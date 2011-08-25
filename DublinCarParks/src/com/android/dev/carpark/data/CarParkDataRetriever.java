package com.android.dev.carpark.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.android.maps.GeoPoint;

/**
 * Utility class to retrieve and package the CarPark Data into a usable format
 * @author snaughton
 *
 */
public class CarParkDataRetriever {
	
	private static Map<String, GeoPoint> carParkLocations =
										new HashMap<String, GeoPoint>();
	
	static {
		carParkLocations.put("PARNELL", new GeoPoint(53350462,-6268837));
		carParkLocations.put("ILAC", new GeoPoint(53350959,-6264707));
		carParkLocations.put("JERVIS", new GeoPoint(5334863,-6266413));
		carParkLocations.put("ARNOTTS", new GeoPoint(53348841,-6261767));
		carParkLocations.put("MARLBORO", new GeoPoint(53352469,-6258768));
		carParkLocations.put("ABBEY", new GeoPoint(53348111,-6262116));
		carParkLocations.put("THOMASST", new GeoPoint(53342737,-6280661));
		carParkLocations.put("C/CHURCH", new GeoPoint(53342744,-6272056));
		carParkLocations.put("SETANTA", new GeoPoint(53341732,-6256027));
		carParkLocations.put("DAWSON", new GeoPoint(53340294,-6256354));
		carParkLocations.put("TRINITY", new GeoPoint(53343961,-6262282));
		carParkLocations.put("GREENRCS", new GeoPoint(53339775,-6263956));
		carParkLocations.put("DRURY", new GeoPoint(53341492,-626394));
		carParkLocations.put("B/THOMAS", new GeoPoint(53342564,-6260989));
	}
	
	public List<CarPark> getCarParkData() {
		List<CarPark> carParks = new ArrayList<CarPark>();
		
		// Get the http data from the server
		String strSource = retrieveData();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//carpark/@name";
		try {
			// Get the car park names
			NodeList nodes = (NodeList) xpath.evaluate(expression, new InputSource(new StringReader(strSource)), XPathConstants.NODESET);
			
			for (int i = 0; i < nodes.getLength(); i++) {
				String value = getXMLValue(new InputSource(new StringReader(strSource)), 
																		"//carpark[@name='" + nodes.item(i).getNodeValue() + "']/@spaces");
				// Create our car park data object
				CarPark thisCarPark = new CarPark();
				thisCarPark.setName(nodes.item(i).getNodeValue());
				thisCarPark.setFreeSpaces(value);
				thisCarPark.setLocation(getCarParkLocation(nodes.item(i).getNodeValue()));
				carParks.add(thisCarPark);
			}			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		return carParks;
	}   
	
	/**
	 * Using XPath get the individual car park information
	 * @param xmlSource
	 * @param xpathQuery
	 * @return
	 */
	private String getXMLValue(InputSource xmlSource, String xpathQuery) {
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			NodeList nodes = (NodeList) xpath.evaluate(xpathQuery, xmlSource, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				return nodes.item(i).getNodeValue(); 
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}		
		return null;
	}

	/**
	 * Get the xml data returned from the car park url
	 * @return
	 */
	private String retrieveData() {
		URL feedUrl = null;
		try {
			feedUrl = new URL("http://www.dublincity.ie/dublintraffic/cpdata.xml");
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
	
	private GeoPoint getCarParkLocation(String carParkName) {		
		return carParkLocations.get(carParkName);
	}

}
