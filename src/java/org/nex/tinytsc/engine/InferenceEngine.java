/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.engine;
import java.util.*;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 *
 * <p>
 * A class which contains various <code>inference engines</code>
 * First one up does an isA check on <code>Concept</code>s
 * </p>
 */

public class InferenceEngine {
  private Environment environment;

  public InferenceEngine(Environment e) {
    environment = e;
  }

  /**
   * <p>Ripple up the <code>instanceOf</code> or <code>subOf</code> links
   * from the <code>instance</code>. Return <code>true</code> if classId is found
   * </p>
   * <p>
   * Works only for the <code>Concept</code> taxonomy </p>
   * @param instanceId
   * @param classId
   * @return
   */
  public boolean checkIsAConcept(String instanceId, String classId) {
    if (instanceId.equals(classId)) return true;
    Concept inst = environment.getConcept(instanceId);
    Concept x;
    List<Object> l;
    String id = null;
    boolean t = false;
    if (inst != null) {
      l = (List<Object>)inst.getProperty("instanceOf");
      if (l != null)
        t = _checkIsAConcept(l,classId);
      if (t)
        return true;
      l = (List<Object>) inst.getProperty("subOf");
      if (l != null)
          return _checkIsAConcept(l,classId);
    }
    return false;
  }

  boolean _checkIsAConcept(List l, String classId) {
    int len = l.size();
    String id;
    for (int i=0;i<len;i++) {
      id = (String)l.get(i);
      if (id != null) {
        if (id.contentEquals(classId)) //bug fix
          return true;
      }
    }
    return false;
  }
}