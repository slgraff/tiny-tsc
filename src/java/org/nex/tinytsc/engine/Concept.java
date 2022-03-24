/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.engine;
import java.util.*;

import org.nex.tinytsc.api.IConstants;
import org.nex.tinytsc.api.Identifiable;
import org.nex.tinytsc.database.JDBMDatabase;

import java.io.*;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class Concept implements Serializable, Identifiable {
  private final int type = IConstants.CONCEPT;
  private String id="";
  private String name="";
  private String comment="";
  private List<Rule> rules = null;
  private List<Episode> episodes=null;
  private String instanceOf = "";
  private List<String> subOf = null;
  private List<String> hasSubs = null;
  private List<String> hasInstances = null;
  private List<String> transitiveClosure = null;
  //we need a database for some of the taxonomy code
  private JDBMDatabase database;

  /**
   * key = String, value = List
   */
  private Map<String, List<Object>> properties = new HashMap<String, List<Object>>();

  public Concept() {}
  
  public Concept (JDBMDatabase db) {
	  database = db;
  }

  public void setDatabase (JDBMDatabase db) {
	  database = db;
  }

  public Concept (String _id) {
    this.id = _id;
  }

  public int getNodeType() {
    return type;
  }
  public void setId(String _id) {
    this.id = _id;
  }
  public String getId() {
    return this.id;
  }
  public void setName(String _name) {
    this.name = _name;
  }
  public String getName() {
    return this.name;
  }
  public void setComment(String _comment) {
    this.comment = _comment;
  }
  public String getComment() {
    return this.comment;
  }

  public void setTransitiveClosure(List<String> tc) {
	  this.transitiveClosure = tc;
  }
  
  /**
   * 
   * @return can return {@code null}
   */
  public List<String> getTransitiveClosure() {
	  return this.transitiveClosure;
  }
  
  public boolean isA(String id) {
	  boolean result = false;
	  if (this.transitiveClosure != null)
		  result = transitiveClosure.contains(id);
	  return result;
  }
  
  public void setInstanceOf(String io) {
	    instanceOf = io;
  }
  public String getInstanceOf() {
    return instanceOf;
  }
  public void addSubOf(String so) {
	  System.out.println("CSubOf "+so);
    if (subOf == null) subOf = new ArrayList<String>();
    subOf.add(so);
    if (transitiveClosure == null) transitiveClosure = new ArrayList<String>();
    if (!transitiveClosure.contains(so))
    	transitiveClosure.add(so);
   /* try {
    	Concept parent = database.getConcept(so);
    	parent.addSubClass(this.getId());
    	database.putConcept(so, parent);
    } catch (Exception e) {
    	throw new RuntimeException(e);
    }*/
  }
  /**
   * 
   * @return can return {@code null}
   */
  public List<String> getSubOf() {
    return subOf;
  }
  public void addSubClass(String so) {
	    if (hasSubs == null) hasSubs = new ArrayList<String>();
	    hasSubs.add(so);
  }
  
  /**
   * 
   * @return can return {@code null}
   */
  public List<String> getSubClasses() {
	  return hasSubs;
  }
  
  /**
   * @return can return {@code null}
   */
  public List<String> getInstance() {
    return hasInstances;
  }
  public void addInstance(String so) {
    if (hasInstances == null) hasInstances = new ArrayList<String>();
    hasInstances.add(so);
  }
  /**
   * 
   * @return can return {@code null}
   */
	public List<String> getInstances() {
	  return hasInstances;
	}

  /**
   * Adding a single value to a slot
   * <p>
   * NOTE:
   * Should check for existence: behave like a Set
   * </p>
   * @param key
   * @param value
   */
  public void addProperty(String key, Object value) {
    System.out.println("Concept "+id+" putProp "+key+" "+value);
    synchronized(properties) {
      List<Object> v = properties.get(key);
      if (v == null)
        v = new ArrayList<Object>();
      if (!v.contains(value))
          v.add(value);
      properties.put(key, v);
    }
  }

  public void putProperty(String key, List<Object> values) {
  	synchronized(properties) {
  		//overwrite everything
  		properties.put(key,values);
  	}
  }

  public List<Object> getProperty(String key) {
    synchronized(properties) {
      return properties.get(key);
    }
  }

  public Iterator listPropertyNames() {
    synchronized(properties) {
      return properties.keySet().iterator();
    }
  }

  public void addRule(Rule rule) {
    if (rules==null) rules = new ArrayList<Rule>();
    rules.add(rule);
  }

  public List<Rule> getRules() {
    return rules;
  }

  public void addEpisode(Episode ep) {
    if (episodes==null) episodes = new ArrayList<Episode>();
    episodes.add(ep);
  }

  public List<Episode> getEpisodes() {
    return episodes;
  }

  public String toXML() {
    StringBuffer buf = new StringBuffer("<concept id=\""+id+"\">\n");
    if (!name.equals(""))
      buf.append("  <name>"+name+"</name>\n");
    if (!comment.equals(""))
      buf.append("  <comment>"+comment+"</comment>\n");
    Iterator itr = listPropertyNames();
    String n;
    List l;
    int len;
    while (itr.hasNext()) {
      n = (String)itr.next();
      l = getProperty(n);
      System.out.println("CONCEPT "+id+" "+l);
      len = l.size();
      buf.append("  <slot name=\""+n+"\"><value>\n  ");
      for (int i=0;i<len;i++)
        buf.append((String)l.get(i)+"  ");
      buf.append("\n  </value></slot>\n");
    }
    buf.append("</concept>\n");
    return buf.toString();
  }
}
