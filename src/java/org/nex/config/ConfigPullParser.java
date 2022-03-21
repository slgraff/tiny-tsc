/*
 *  Copyright (C) 2004,2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.config;
import java.io.*;
import java.util.*;
/** in xpp.jar */
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * <p>Configuration PullParser</p>
 * <p>Description: For parsing config files</p>
 * <p>Copyright: Copyright (c) 2003,2004 Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */
/**
 *  DTD handled by this class:
  <!ELEMENT PROPERTIES  (PARAMETER | LIST )* >
  <!ELEMENT PARAMETER EMPTY >
  <!ATTLIST PARAMETER
	type	( String | Integer | Double | Boolean | URL ) "String"
	value	CDATA "Null"
	name	CDATA "Name"
	date	CDATA "00/00/00" >
  <!ELEMENT LIST (PARAMETER)* >
  <!ATTLIST LIST
      name    CDATA "Name" >
 */
public class ConfigPullParser {
	private Hashtable properties = new Hashtable();

	/**
	 * Constructor for ConfigPullParser.
	 * @param file path to XML config file
	 */
	public ConfigPullParser(String configFilePath) {
		super();
		try {
			// open a file
			File f = new File(configFilePath);
			// grab an inputstream
			FileInputStream fis = new FileInputStream(f);
			// parse this puppy
			parse(fis);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * @return properties table formed by parsing config file
	 * called by TsServlet
	 */
	public Hashtable getProperties() {
		return properties;
	}

	void parse(InputStream is) {
      try {
         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
         factory.setNamespaceAware(false);
         XmlPullParser xpp = factory.newPullParser();

         BufferedReader in = new BufferedReader(new InputStreamReader(is));
         xpp.setInput(in);
         String temp = null;
         String text = null;
         String name = null;
         String listName = null;
         String value = null;
         HashMap attributes = null;
         ArrayList theList = null;
         boolean isList = false;
         int eventType = xpp.getEventType();
         boolean isStop = false;
         while (!(isStop || eventType == XmlPullParser.END_DOCUMENT)) {
            temp = xpp.getName();
            attributes = getAttributes(xpp);
            if (attributes != null) {
            	name = (String)attributes.get("name");
            	value = (String)attributes.get("value");
            } else {
            	name = null;
            	value = null;
            }
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End document");
            } else if(eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag "+temp);
                if(temp.equalsIgnoreCase("parameter")) {
                	if (isList) {
                		ArrayList list = new ArrayList();
                		list.add(name);
                		list.add(value);
                		theList.add(list);
                	} else
                		properties.put(name,value);

                } else if(temp.equalsIgnoreCase("list")) {
                	listName = name;
                	theList = new ArrayList();
                	isList = true;
                }
            } else if(eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag "+temp+" // "+text);
                if(temp.equalsIgnoreCase("parameter")) {
                	//taken care by start tag
                } else if(temp.equalsIgnoreCase("list")) {
                	properties.put(listName,theList);
                	listName = null;
                	theList = null;
                	isList = false;
                }
            } else if(eventType == XmlPullParser.TEXT) {
//                System.out.println("Text "+id+" // "+xpp.getText());
                text = xpp.getText().trim();
             } else if(eventType == XmlPullParser.CDSECT) {
     //         System.out.println("Cdata "+id+" // "+xpp.getText());
                text = xpp.getText().trim();
            }
            eventType = xpp.next();
      //System.out.println("PARSER NEXT "+id+" // "+result.getCommand()+ " // "+eventType);
          }
        } catch (XmlPullParserException e) {
      System.out.println("ServerPullParser parser failed "+e.getMessage());
        } catch (IOException x) {
      System.out.println("ServerPullParser parser io failure "+x.getMessage());
        }
//System.out.println("PARSER RETURNING "+result.toString());
	}
    /**
     * Return null if no attributes
     */
    HashMap getAttributes(XmlPullParser p) {
      HashMap result = null;
      int count = p.getAttributeCount();
      if (count > 0) {
        result = new HashMap();
        String name = null;
        for (int i = 0; i < count; i++) {
          name = p.getAttributeName(i);
          result.put(name,p.getAttributeValue(i));
        }
      }
      return result;
    }
}
