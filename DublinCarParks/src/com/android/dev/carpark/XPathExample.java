package com.android.dev.carpark;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XPathExample {

	/*
  public static void main(String[] args) 
   throws ParserConfigurationException, SAXException, 
          IOException, XPathExpressionException {

  	String xmlSource = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><carparkData><Northwest><carpark name=\"PARNELL\" spaces=\"286\"></carpark><carpark name=\"ILAC\" spaces=\" \"> </carpark><carpark name=\"JERVIS\" spaces=\"166\"></carpark><carpark name=\"ARNOTTS\" spaces=\"FULL\"></carpark></Northwest><Northeast><carpark name=\"MARLBORO\" spaces=\"209\"></carpark><carpark name=\"ABBEY\" spaces=\"99\"></carpark></Northeast><Southwest><carpark name=\"THOMASST\" spaces=\"215\"></carpark><carpark name=\"C/CHURCH\" spaces=\"77\"></carpark></Southwest><Southeast><carpark name=\"SETANTA\" spaces=\"18\"></carpark><carpark name=\"DAWSON\" spaces=\"138\"></carpark><carpark name=\"TRINITY\" spaces=\"188\"></carpark><carpark name=\"GREENRCS\" spaces=\"599\"></carpark><carpark name=\"DRURY\" spaces=\"136\"></carpark><carpark name=\"B/THOMAS\" spaces=\"153\"></carpark></Southeast><Timestamp>10:44:19 on Friday 17/12/2010</Timestamp></carparkData>";  	
  	StringReader strReader = new StringReader(xmlSource);
  	InputSource inputString = new InputSource(strReader); 

    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true); // never forget this!
    DocumentBuilder builder = domFactory.newDocumentBuilder();
    Document doc = builder.parse(inputString);    

    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();
    XPathExpression expr 
     = xpath.compile("//carpark/@name");

    Object result = expr.evaluate(doc, XPathConstants.NODESET);
    NodeList nodes = (NodeList) result;
    for (int i = 0; i < nodes.getLength(); i++) {
        System.out.println(nodes.item(i).getNodeValue()); 
    }

  }  
	 */

	public static void main (String args[]) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();         
		factory.setNamespaceAware(true);         
		XmlPullParser xpp = factory.newPullParser();         

		String xmlSource = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><carparkData><Northwest><carpark name=\"PARNELL\" spaces=\"286\"></carpark><carpark name=\"ILAC\" spaces=\" \"> </carpark><carpark name=\"JERVIS\" spaces=\"166\"></carpark><carpark name=\"ARNOTTS\" spaces=\"FULL\"></carpark></Northwest><Northeast><carpark name=\"MARLBORO\" spaces=\"209\"></carpark><carpark name=\"ABBEY\" spaces=\"99\"></carpark></Northeast><Southwest><carpark name=\"THOMASST\" spaces=\"215\"></carpark><carpark name=\"C/CHURCH\" spaces=\"77\"></carpark></Southwest><Southeast><carpark name=\"SETANTA\" spaces=\"18\"></carpark><carpark name=\"DAWSON\" spaces=\"138\"></carpark><carpark name=\"TRINITY\" spaces=\"188\"></carpark><carpark name=\"GREENRCS\" spaces=\"599\"></carpark><carpark name=\"DRURY\" spaces=\"136\"></carpark><carpark name=\"B/THOMAS\" spaces=\"153\"></carpark></Southeast><Timestamp>10:44:19 on Friday 17/12/2010</Timestamp></carparkData>";  	

		xpp.setInput( new StringReader ( xmlSource ) );         
		int eventType = xpp.getEventType();         
		while (eventType != XmlPullParser.END_DOCUMENT) {          
			if(eventType == XmlPullParser.START_DOCUMENT) {              
				System.out.println("Start document");          
			} else if(eventType == XmlPullParser.END_DOCUMENT) {              
				System.out.println("End document");          
			} else if(eventType == XmlPullParser.START_TAG) {              
				System.out.println("Start tag "+xpp.getName());          
			} else if(eventType == XmlPullParser.END_TAG) {              
				System.out.println("End tag "+xpp.getName());          
			} else if(eventType == XmlPullParser.TEXT) {              
				System.out.println("Text "+xpp.getText());          
			}          
			eventType = xpp.next();         
		}     
	}
	
}


