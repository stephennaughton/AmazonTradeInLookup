package com.amazon.prodadvertising.api;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ProductLookup {
	/*
	 * Your AWS Access Key ID, as taken from the AWS Your Account page.
	 */
	private static final String AWS_ACCESS_KEY_ID = "AKIAJ4TRBPEMPHH4R65Q";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "XD5NtnRomszG4or6386fgMt111szxqbxGOPRQgZq";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 *      US: ecs.amazonaws.com 
	 *      CA: ecs.amazonaws.ca 
	 *      UK: ecs.amazonaws.co.uk 
	 *      DE: ecs.amazonaws.de 
	 *      FR: ecs.amazonaws.fr 
	 *      JP: ecs.amazonaws.jp
	 * 
	 */
	private static final String ENDPOINT = "ecs.amazonaws.com";
	
	public Map<String, String> lookupAmazonProductInfo(String isbn) {
		/*
		 * Set up the signed requests helper 
		 */
		SignedRequestsHelper helper = null;
		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();            
		}

		String requestUrl = null;

		/* The helper can sign requests in two forms - map form and string form */

		/*
		 * Here is an example in map form, where the request parameters are stored in a map.
		 */
		System.out.println("Map form example:");
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", "2011-08-01");
		params.put("Operation", "ItemLookup");
		params.put("ItemId", isbn);
		params.put("SearchIndex", "Books");
		params.put("SubscriptionId", "Your_AWS_ID");
		params.put("AssociateTag", "assoc-id-20");
		params.put("IdType", "EAN");
		params.put("ResponseGroup", "ItemAttributes");

		requestUrl = helper.sign(params);	
	
		System.out.println("Signed Request is \"" + requestUrl + "\"");

		Map<String, String> data = fetchData(requestUrl);
		System.out.println("Title is \"" + data + "\"");

		return data;
	}

	/*
	 * Utility function to fetch the response from the service and extract the
	 * title from the XML.
	 */
	private static Map<String, String> fetchData(String requestUrl) {
		Map<String, String> data = new HashMap<String, String>();
		try {
			System.out.println("****" + requestUrl);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(requestUrl);
			
			String[] sArr = {"Title", "IsEligibleForTradeIn", "TradeInValue"};
			for (String s:sArr) {
				Node aNode = doc.getElementsByTagName(s).item(0);			
				String sVal = aNode.getTextContent();				
				data.put(s, sVal);
			}			

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return data;
	}

}
