package com.android.dev.carpark;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.android.dev.carpark.R;
import com.android.dev.carpark.R.id;
import com.android.dev.carpark.R.layout;
import com.android.dev.carpark.data.CarPark;
import com.android.dev.carpark.data.CarParkDataRetriever;
import com.android.dev.carpark.maps.CarParkItemOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class CarParkMap extends MapActivity {

	private MapView mapView;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.maplayout); // bind the layout to the activity

		// create a map view.
		mapView = (MapView) findViewById(R.id.mapview);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.androidpointer);
	
		// Retrieve the car park data
		CarParkDataRetriever dataAccess = new CarParkDataRetriever();
		List<CarPark> carParkData = dataAccess.getCarParkData();
		
		CarParkItemOverlay itemizedoverlay = new CarParkItemOverlay(drawable, mapView.getContext());
	
		for (CarPark c:carParkData) {
			OverlayItem overlayItem = new OverlayItem(c.getLocation(), c.getName(), c.getFreeSpaces());			
			itemizedoverlay.addOverlay(overlayItem);
		}

		mapOverlays.add(itemizedoverlay);
		
		mapView.setBuiltInZoomControls(true);
		
//		mapView.setBuiltInZoomControls(true);
//		mapView.setStreetView(true);
//		mapController = mapView.getController();
//		mapController.setZoom(14); // Zoom 1 is world view
//		
//    String coordinates[] = {"53.350462", "-6.268837"};
//    double lat = Double.parseDouble(coordinates[0]);
//    double lng = Double.parseDouble(coordinates[1]);
//
//		mapController.setCenter(new GeoPoint(
//        (int) (lat * 1E6), 
//        (int) (lng * 1E6)));
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
