/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.xml;
import java.io.*;
import java.util.*;

import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.engine.Task;
import org.nex.tinytsc.engine.Episode;
import org.nex.tinytsc.engine.Rule;
import org.nex.tinytsc.engine.Model;
import org.nex.tinytsc.engine.Sentence;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.DatastoreException;
//xpp.jar
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
//log4j.jar
import org.apache.log4j.Logger;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */
public class ConceptPullParser {
	private Logger log = Logger.getLogger(ConceptPullParser.class);
        private Environment environment;
        private Concept theConcept;
        private Model theModel;
        private Episode theEpisode;
        private Rule theRule;
        private Task theTask;
        private Sentence theSentence;

	/**
	 *
	 */
	public ConceptPullParser(Environment e) {
		super();
		environment = e;
	}

	public  void parse(File inFile) {
	    try {
	      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	      factory.setNamespaceAware(false);
	      XmlPullParser xpp = factory.newPullParser();

	      FileInputStream is = new FileInputStream(inFile);
	      BufferedReader in = new BufferedReader(new InputStreamReader(is));
	      xpp.setInput(in);
	      String temp = null;
	      String text = null;
	      String name = null;
              String otherSlotName = null;
	      String value = null;
              String id = null;
              String ruleId = null;
              String nodeId = null;
	      HashMap attributes = null;
	      ArrayList theList = null;
	      boolean isList = false;
              boolean isSlot = false;
              boolean isModel = false;
              boolean isRule = false;
              boolean isEpisode = false;
              boolean isNextEpisode = false;
              boolean isPreviousEpisode = false;
              boolean isTask = false;
              boolean isConcept = false;
 //             boolean isThenSay = false;
              boolean isIfActors = false;
              boolean isIfNotActors = false;
              boolean isIfRelations = false;
              boolean isIfNotRelations = false;
              boolean isIfStates = false;
              boolean isIfNotStates = false;
              boolean isThenActors = false;
              boolean isThenRelates = false;
              boolean isThenStates = false;
              boolean isThenCreate = false;
              boolean isThenConjecture = false;
              boolean isInstanceOf = false; // priviledged slot
              //isOther means just put the slot name/List value pair into the concept
              boolean isOtherSlot = false;
	      int eventType = xpp.getEventType();
	      boolean isStop = false;
	      while (! (isStop || eventType == XmlPullParser.END_DOCUMENT)) {
	        temp = xpp.getName();
	        attributes = getAttributes(xpp);
	        if (attributes != null) {
	          name = (String) attributes.get("name");
	          value = (String) attributes.get("value");
                  id = (String) attributes.get("id");
                  if (name != null) {
                    //clear variables
  //                  isThenSay = false;
                    isIfActors = false;
                    isIfNotActors = false;
                    isIfRelations = false;
                    isIfNotRelations = false;
                    isIfStates = false;
                    isIfNotStates = false;
                    isThenActors = false;
                    isThenRelates = false;
                    isThenStates = false;
                    isThenCreate = false;
                    isThenConjecture = false;
                    isInstanceOf = false;
                    isOtherSlot = false;
                    //set variables if possible
                    if (name.equals("ifActors") || name.equals("actors"))
                      isIfActors = true;
                    else if (name.equals("ifNotActors"))
                      isIfNotActors = true;
                    else if (name.equals("ifRelations") ||
                             name.equals("relations"))
                      isIfRelations = true;
                    else if (name.equals("ifNotRelations"))
                      isIfNotRelations = true;
                    else if (name.equals("ifStates") || name.equals("states"))
                      isIfStates = true;
                    else if (name.equals("ifNotStates"))
                      isIfNotStates = true;
                    else if (name.equals("thenCreates"))
                      isThenCreate = true;
                    else if (name.equals("thenActors"))
                      isThenActors = true;
                    else if (name.equals("thenRelations"))
                      isThenRelates = true;
                    else if (name.equals("thenStates"))
                      isThenStates = true;
  //                  else if (name.equals("thenSay"))
  //                    isThenSay = true;
                    else if (name.equals("thenConjectures"))
                      isThenConjecture = true;
                    else if (name.equals("instanceOf"))
                      isInstanceOf = true;
                    else {
                      isOtherSlot = true;
                      otherSlotName = name;
                    }
                  }
	        }
	        else {
	          name = null;
	          value = null;
	        }
	        if (eventType == XmlPullParser.START_DOCUMENT) {
	          System.out.println("Start document");
	        }
	        else if (eventType == XmlPullParser.END_DOCUMENT) {
	          System.out.println("End document");
	        }
	        else if (eventType == XmlPullParser.START_TAG) {
//	          System.out.println("Start tag " + temp);
	          if (temp.equalsIgnoreCase("concept")) {
                    isConcept = true;
                    theConcept = new Concept(id);
                  } else if (temp.equalsIgnoreCase("rule")) {
                    theRule = new Rule(id);
                    isRule = true;
                  } else if (temp.equalsIgnoreCase("episode")) {
                    theEpisode = new Episode(id);
                    isEpisode = true;
                  } else if (temp.equalsIgnoreCase("model")) {
                    theModel = new Model(id);
                    isModel = true;
                  } else if (temp.equalsIgnoreCase("task")) {
                    theTask = new Task(id);
                    isTask = true;
                  } else if (temp.equalsIgnoreCase("slot")) {
                    isSlot = true;
                    //we already have the slot name
                  } else if (temp.equalsIgnoreCase("sentence")) {
                    theSentence = new Sentence();
                  } else if (temp.equalsIgnoreCase("predicate")) {
                  } else if (temp.equalsIgnoreCase("subject")) {
                  } else if (temp.equalsIgnoreCase("truth")) {
                  } else if (temp.equalsIgnoreCase("nextEpisode")) {
                    isNextEpisode = true;
                  } else if (temp.equalsIgnoreCase("previousEpisode")) {
                    isPreviousEpisode = true;
                  } else if (temp.equalsIgnoreCase("mechanism")) {
                  } else if (temp.equalsIgnoreCase("node")) {
                  } else if (temp.equalsIgnoreCase("type")) {
                  } else if (temp.equalsIgnoreCase("object")) {
                  } else if (temp.equalsIgnoreCase("experiment")) {
                  }
	        }
	        else if (eventType == XmlPullParser.END_TAG) {
//	          System.out.println("End tag " + temp + " // " + text);
                  if (temp.equalsIgnoreCase("concept")) {
                    isConcept = false;
                    try {
                      System.out.println(theConcept.toXML());
                      environment.importConcept(theConcept);
                    } catch (DatastoreException x) {
                      throw new RuntimeException(x);
                    }
                    theConcept = null;
                  } else if (temp.equalsIgnoreCase("rule")) {
                    isRule = false;
                    try {
                      System.out.println(theRule.toXML());
                      environment.importRule(theRule);
                    } catch (DatastoreException x) {
                      throw new RuntimeException(x);
                    }
                    theRule = null;
                  } else if (temp.equalsIgnoreCase("episode")) {
                    isEpisode = false;
                    try {
                      System.out.println(theEpisode.toXML());
                      environment.importEpisode(theEpisode);
                    } catch (DatastoreException x) {
                      throw new RuntimeException(x);
                    }
                    theEpisode = null;
                  } else if (temp.equalsIgnoreCase("model")) {
                    isModel = false;
                    try {
                      System.out.println(theModel.toXML());
                      environment.importModel(theModel);
                    } catch (DatastoreException x) {
                      throw new RuntimeException(x);
                    }
                    theModel = null;
                  } else if (temp.equalsIgnoreCase("task")) {
                    isTask = false;
                    try {
                      System.out.println(theTask.toXML());
                      environment.importTask(theTask);
                    } catch (DatastoreException x) {
                      throw new RuntimeException(x);
                    }
                    theTask = null;
                  } else if (temp.equalsIgnoreCase("slot")) {
                    isSlot = false;
                    if (isOtherSlot) {
                      //these are not qp slots
                      if (isConcept)
                        theConcept.putProperty(otherSlotName,theList);
                    } else if (isInstanceOf) {
                      if (isConcept)
                        theConcept.putProperty("instanceOf", theList);
                      else if (isTask)
                        theTask.setInstanceOf((String)theList.get(0));
                      else if (isRule)
                        theRule.setInstanceOf((String)theList.get(0));
                      else if (isModel)
                        theModel.setInstanceOf((String)theList.get(0));
                      else if (isEpisode)
                        theEpisode.setInstanceOf((String)theList.get(0));
                    } //qp slots were dealt with in their sentences
                  } else if (temp.equalsIgnoreCase("sentence")) {
                    if (isModel) {
                      if (isIfActors)
                        theModel.addActor(theSentence);
                      else if (isIfRelations)
                        theModel.addRelation(theSentence);
                      else if (isIfStates)
                        theModel.addState(theSentence);
                    } else if (isEpisode) {
                      if (isIfActors)
                        theEpisode.addActor(theSentence);
                      else if (isIfRelations)
                        theEpisode.addRelation(theSentence);
                      else if (isIfStates)
                        theEpisode.addState(theSentence);
                    } else if (isRule) {
                      if (isIfActors)
                        theRule.addIfActor(theSentence);
                      else if (isIfRelations)
                        theRule.addIfRelation(theSentence);
                      else if (isIfStates)
                        theRule.addIfState(theSentence);
                      else if (isIfNotActors)
                        theRule.addIfNotActor(theSentence);
                      else if (isIfNotRelations)
                        theRule.addIfNotRelation(theSentence);
                      else if (isIfNotStates)
                        theRule.addIfNotState(theSentence);
                      else if (isThenActors)
                       theRule.addThenActor(theSentence);
                     else if (isThenRelates)
                       theRule.addThenRelation(theSentence);
                     else if (isThenStates)
                       theRule.addThenState(theSentence);
                     else if (isThenConjecture)
                       theRule.addThenConjecture(theSentence);
//                     else if (isThenSay)
//                       theRule.addThenSay(theSentence);
                    } else if (temp.equalsIgnoreCase("database")) {
                      //we're done
                      environment.finishImport();
                    }
                    theSentence = null;
                  } else if (temp.equalsIgnoreCase("predicate")) {
                    theSentence.predicate = text;
                  } else if (temp.equalsIgnoreCase("subject")) {
                    //might be more than one value
                    StringTokenizer tok = new StringTokenizer(text);
                    //opportunity for bugs here
                    theSentence.object = tok.nextToken();
                    if (tok.hasMoreTokens())
                      theSentence.objectB = tok.nextToken();
                  } else if (temp.equalsIgnoreCase("truth")) {
                    boolean truth = text.equals("true");
                    theSentence.truth = truth;
                  } else if (temp.equalsIgnoreCase("nextEpisode")) {
                    isNextEpisode = false;
                    if (isModel)
                      theModel.addNextEpisode(ruleId,nodeId);
                    else if (isEpisode)
                      theEpisode.addNextEpisode(ruleId,nodeId);
                    ruleId = nodeId = null;
                  } else if (temp.equalsIgnoreCase("previousEpisode")) {
                    isPreviousEpisode = false;
                   if (isEpisode)
                      theEpisode.addPreviousEpisode(ruleId,nodeId);
                    ruleId = nodeId = null;
                  } else if (temp.equalsIgnoreCase("mechanism")) {
                    ruleId = text;
                  } else if (temp.equalsIgnoreCase("node")) {
                    nodeId = text;
                  } else if (temp.equalsIgnoreCase("type")) {
                    theTask.setTaskType(text);
                  } else if (temp.equalsIgnoreCase("object")) {
                    //TODO
                  } else if (temp.equalsIgnoreCase("experiment")) {
                    Model m = environment.getModel(text);
                    //could be null!!!!
                    theTask.setModel(m);
                  } else if (temp.equalsIgnoreCase("value")) {

                    //value might have a list of space-delimited symbols
                    StringTokenizer tok = new StringTokenizer(text);
                    theList = new ArrayList();
                    while (tok.hasMoreTokens())
                      theList.add(tok.nextToken());
                  } else if (temp.equalsIgnoreCase("myMechanism")) {
                    theEpisode.setMechanism(text);
                  } else if (temp.equalsIgnoreCase("name")) {
                    if (isConcept)
                      theConcept.setName(text);
                    else if (isEpisode)
                      theEpisode.setName(text);
                    else if (isModel)
                      theModel.setName(text);
                    else if (isRule)
                      theRule.setName(text);
                  } else if (temp.equalsIgnoreCase("comment")) {
                    if (isConcept)
                      theConcept.setComment(text);
                    else if (isEpisode)
                      theEpisode.setComment(text);
                    else if (isModel)
                      theModel.setComment(text);
                    else if (isRule)
                      theRule.setComment(text);
                  }
	        }
	        else if (eventType == XmlPullParser.TEXT) {
//	                System.out.println("Text "+id+" // "+xpp.getText());
	          text = xpp.getText().trim();
	        }
	        else if (eventType == XmlPullParser.CDSECT) {
	          text = xpp.getText().trim();
	        }
	        eventType = xpp.next();
	      }
	    } catch (XmlPullParserException e) {
	      System.out.println("ConceptPullParser failed " + e.getMessage());
	    } catch (IOException x) {
	      System.out.println("ConceptPullParser io failure " + x.getMessage());
	    }
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
	        result.put(name, p.getAttributeValue(i));
	      }
	    }
	    return result;
	  }

}
