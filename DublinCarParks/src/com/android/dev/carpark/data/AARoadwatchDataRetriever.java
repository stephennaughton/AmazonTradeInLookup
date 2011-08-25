package com.android.dev.carpark.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AARoadwatchDataRetriever {

	public static void main(String[] args) {
		AARoadwatchDataRetriever data = new AARoadwatchDataRetriever();	
		List<String> trafficData = data.getTrafficData();
		
		System.out.println(trafficData);
	}
	
	public List<String> getTrafficData() {
		List<String> trafficData = new ArrayList<String>();
		
		// Get the http data from the server
		String strSource = retrieveData();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//div[@class=\"mainTrafficItem\"]";
		try {			
			// Get the car park names
			NodeList nodes = (NodeList) xpath.evaluate(expression, new InputSource(new StringReader(strSource)), XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				trafficData.add(nodes.item(i).getNodeValue());	
			}			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}		
		return trafficData;
	}

	/**
	 * Get the xml data returned from the car park url
	 * @return
	 */
	private String retrieveData() {
		URL feedUrl = null;
		try {
			feedUrl = new URL("http://www.aaireland.ie/AA/AA-Roadwatch/Dublin.aspx");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}		

		try {			
			return slurp(feedUrl.openConnection().getInputStream());			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	/**
	 * Pull in all the information contained in the feed.
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private String slurp (InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}	
	
}
