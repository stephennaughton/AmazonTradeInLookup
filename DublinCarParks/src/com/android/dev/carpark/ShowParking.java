package com.android.dev.carpark;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShowParking extends ListActivity {

	private static HashMap<String, String> data;
	private ParkingLocationsAdapter adapter;
	private Runnable viewCarParks;	
	private List<String> carParks;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {    
		MenuInflater inflater = getMenuInflater();    
		inflater.inflate(R.menu.main_menu, menu);    
		return true;
	}

	/*
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			this.createListDialog();
			break;
		case R.id.quit:
			break;
		}
		return true;
	}
	*/
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		carParks = new ArrayList<String>();
		this.adapter = new ParkingLocationsAdapter(this, R.layout.row, carParks);		
		setListAdapter(this.adapter);
		
		viewCarParks = new Runnable(){
			@Override
			public void run() {
				getParkingData();			
				System.out.println("Carparks:" + carParks.size());
			}
		};
		Thread thread =  new Thread(null, viewCarParks, "MagentoBackground");
		thread.start();		
	}
	
	private Runnable returnRes = new Runnable() {		
		@Override
		public void run() {
			System.out.println("Running returnRes on main thread" + carParks.size());
			if(carParks != null && carParks.size() > 0){
				adapter.notifyDataSetChanged();
				/*
				for(int i=0;i<10;i++) {
					System.out.println("Adding: " + carParks.get(i));
					adapter.add(carParks.get(i));
					System.out.println("Size: " + adapter.items.size());
				}
				*/
			}
			adapter.notifyDataSetChanged();
		}
	};	

	private class ParkingLocationsAdapter extends ArrayAdapter<String> {

		private List<String> items;

		public ParkingLocationsAdapter(Context context, int textViewResourceId, List<String> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			final String value = items.get(position);
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);				
			}

			/*
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			if (tt != null) {
				tt.setText(value);				
			}
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			if (tt != null) {
				bt.setText(data.get(value));				
			}			
			*/
			return v;
		}
	}	

	private void getParkingData() {

		data =  new HashMap<String, String>();

		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//carpark/@name";
		String strSource = retrieveData();
		try {
			NodeList nodes = (NodeList) xpath.evaluate(expression, new InputSource(new StringReader(strSource)), XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				String value = getXMLValue(new InputSource(new StringReader(strSource)), "//carpark[@name='" + nodes.item(i).getNodeValue() + "']/@spaces");
				data.put(nodes.item(i).getNodeValue(), value);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	

		System.out.println("HashMap: " + data);
		
		for (String key:data.keySet()) { 
			if (!carParks.contains(key)) {
				carParks.add(key);
			}
		}		
		runOnUiThread(returnRes);
	}   

	private String getXMLValue(InputSource xmlSource, String xpathQuery) {
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			NodeList nodes = (NodeList) xpath.evaluate(xpathQuery, xmlSource, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				return nodes.item(i).getNodeValue(); 
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	private String retrieveData() {
		URL feedUrl = null;
		try {
			feedUrl = new URL("http://www.dublincity.ie/dublintraffic/cpdata.xml");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		

		try {			
			return slurp(feedUrl.openConnection().getInputStream());			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	public  String slurp (InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

}
