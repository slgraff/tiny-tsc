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
  /**
   * key = String, value = List
   */
  private Map<String, List<Object>> properties = new HashMap<String, List<Object>>();

  public Concept() {}

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
