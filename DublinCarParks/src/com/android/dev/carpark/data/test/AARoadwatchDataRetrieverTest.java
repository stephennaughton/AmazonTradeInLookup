package com.android.dev.carpark.data.test;

import java.util.List;

import org.junit.Test;

import com.android.dev.carpark.data.AARoadwatchDataRetriever;

public class AARoadwatchDataRetrieverTest {

	@Test
	public void testRetrieveData() {
		AARoadwatchDataRetriever data = new AARoadwatchDataRetriever();	
		List<String> trafficData = data.getTrafficData();
		
		System.out.println(trafficData);
	}
	
}
