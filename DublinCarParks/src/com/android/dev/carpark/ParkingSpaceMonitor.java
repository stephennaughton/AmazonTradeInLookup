package com.android.dev.carpark;

import java.util.List;

import com.android.dev.carpark.data.CarPark;
import com.android.dev.carpark.data.CarParkDataRetriever;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ParkingSpaceMonitor extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);      
		// Use our own list adapter
		setListAdapter(new CarParkAdapter(this));

		setContentView(R.layout.main);     
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{    
		((CarParkAdapter)getListAdapter()).toggle(position);
	}

	private class CarParkAdapter extends BaseAdapter implements OnClickListener {

		/**
		 * Remember our context so we can use it when constructing views.
		 */
		private Context appContext;
		private CarParkDataRetriever dataAccess = new CarParkDataRetriever();
		private List<CarPark> carParkData = null;
		
		private Handler mHandler = new Handler();
		private long mStartTime;
		
		
		
		public CarParkAdapter(Context appContext) {
			this.appContext = appContext;

			// Retrieve the car park data			
			carParkData = dataAccess.getCarParkData();
			
			//mHandler.removeCallbacks(mUpdateTimeTask);            
			//mHandler.postDelayed(mUpdateTimeTask, 1000); 
		}

		public void toggle(int position) {
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return carParkData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return carParkData.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		/**
		 * Make a CarParkView to hold each row.
		 * @see android.widget.ListAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			v = vi.inflate(R.layout.row, null);				

			CarPark availableData = carParkData.get(position);
			
			TextView tt = (TextView) v.findViewById(R.id.spaces);
			if (tt != null) {
				if (availableData.getFreeSpaces() == null
							|| availableData.getFreeSpaces().equals("")) {
					tt.setText("N/A");
				} else {
					tt.setText(availableData.getFreeSpaces());
				}								
			}
			TextView bt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				bt.setText(carParkData.get(position).getName());				
			}
			
			// Add the onclick listener for the row.
			v.setOnClickListener(this);
			
			return v;
		}

		private Runnable mUpdateTimeTask = new Runnable() {   
			public void run() {       
				final long start = mStartTime;       
				long millis = SystemClock.uptimeMillis() - start;       
				int seconds = (int) (millis / 1000);       
				int minutes = seconds / 60;       
				seconds     = seconds % 60;       
				
				Log.d("DEBUG", "Timer called");
				
				carParkData = dataAccess.getCarParkData();
				//carParkData.remove(0);
				
				notifyDataSetChanged();
				
				mHandler.postAtTime(this,               
						start + (((minutes * 60) + seconds + 1) * 1000));   
			}
		};			

		@Override
		public void onClick(View v) {
			Log.d("DEBUG", "I got clicked");
			Intent mapIntent = new Intent(appContext, CarParkMap.class);
			startActivity(mapIntent);
		}

	}

}
