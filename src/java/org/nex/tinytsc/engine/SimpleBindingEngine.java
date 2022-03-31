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
 */

public class SimpleBindingEngine {
	  private Environment environment;
  /**
   * Effectively, a <code>Map</code> of bindings, e.g.
   * variable:  *foo
   * binding:   foo39
   */
  private BindingsClass curBinding = new BindingsClass();

  /**
   * Constructed in <code>FillinNextEpisodeAgent</code>
   * @param env
   */
  public SimpleBindingEngine(Environment env) {
	  environment = env;
  }

  /**
   * Clear out the bindings
   * Bindings are cleared at the beginning of <code>testRule</code>
   * where a <code>Rule</code> is being tested to see if it can fire
   * against an <code>Episode</code>
   */
  public void freshBindings() {
    curBinding.freshBindings();
  }

  /**
   * <p>Add an externally-created binding.
   * This is necessary for <code>thenCreate</code>
   * which creates a new <em>existential binding</em> for
   * a new <code>actor</code> in an <code>Episode</code>.</p>
   * <p>Called from <code>Environment</code></p>
   * @param name
   * @param value
   */
  public void addBinding(String name, String value) {
    curBinding.bindVar(name,value);
    environment.logDebug("BINDING-1 "+curBinding);
  }

  /**
   * @param unbound sentence from a Rule
   * @return bound sentence or null
   */
  public Sentence bindSentence(Sentence aSent) {
    Sentence result = aSent.copy();
    String subj = aSent.subject;
    String obja = aSent.object;
//    String objb = aSent.objectB;
    result.subject = curBinding.getBinding(subj);
    if (!obja.equals(""))
        result.object = curBinding.getBinding(obja);
    System.out.println("Bound Sentence: "+result);
    return result;
  }

  /**
   * <p>Presently requires that both lists be the same length.
   * This could be changed such that the <code>Rule</code> could have
   * <em>less</em> or the <em>same</em> length, but <b>not</b> <em>greater</em>
   * length.</p>
   * <p>It might be worth reversing this code:
   * for each <code>Sentence</code> in the <code>Rule</code> <code>actors</code></p>
   *
   * <p>This operation is performed on <code>actors</code> for a
   * <code>Rule</code> and an <code>Episode</code></p>
   *
   * @param ruleSentences (list of <code>Sentence</code>s)
   * @param epSentences
   * @return
   */
  public boolean bind(List ruleSentences, List<Sentence> epSentences) {
//    Binding rules: [[foo(*f1 | )], [bar(*b1 | )], [foo(*f2 | )]]
	  environment.logDebug("Binding rules: "+ruleSentences);
//    Binding eps: [[foo(foo1 | )], [foo(foo2 | )], [bar(bar1 | )]]
	  environment.logDebug("Binding eps: "+epSentences);
    //TODO
    // generalize such that ruleSentences.size() can be <= epSentences.size()
    // then only bind up what you can.
    // this means that fireRule can only build a new episode on that which is bound
    // meaning fireRule needs a boolean isBound() test
    if (ruleSentences.size() == epSentences.size()) {
      Map<String, List<Sentence>> r = collectPreds(ruleSentences);
      int len = epSentences.size(), len2;
      Sentence t, t2;
      List<Sentence> l;
      String svar, ovar, sbnd, obnd;
      for (int i = 0; i < len; i++) {
        // for each Sentence in the Episode actors
        // remember, there may be more than one
        // Sentence in both the Rule and in the Episode
        // for any given predicate -- multiple actors of the same class
        t = epSentences.get(i);
        //
        l = r.get(t.predicate);
        //NOTE: if we are going to relax the "equal length" requirement,
        //then this test will not return false;
        if (l == null)
          return false; // don't have this predicate
        len2 = l.size();
        // for each Sentence associated with this predicate
        for (int j = 0; j < len2; j++) {
          t2 = l.get(j);
          svar = t2.subject;
          ovar = t2.object;
          if (curBinding.bindVar(svar, t.subject)) {
            if (!ovar.equals(""))
              curBinding.bindVar(ovar, t2.object);
            break; // bound for this epSentence
          }
        }
      }
      return true;
    }

    return false;
  }

  /**
   * Return a Map:
   *   key = predicate
   *   value = List of Tuples with that predicate
   * @param sentences (list of <code>Sentence</code>s)
   * @return Map
   */
  Map<String, List<Sentence>> collectPreds(List<Sentence> sentences) {
    Map<String, List<Sentence>> result = new HashMap<String, List<Sentence>>();
    int len = sentences.size();
    Sentence t;
    String pred;
    List<Sentence> l;
    for (int i=0;i<len;i++) {
      t = sentences.get(i);
      System.out.println("CRP 1: "+t.toString());
      pred = t.predicate;
      System.out.println("CRP 2: "+pred);
      l = (List)result.get(pred);
      if (l==null) l = new ArrayList<Sentence>();
      l.add(t);
      // fill up the map
      result.put(pred,l);
    }
    return result;
  }

  /**
   * Return <code>true if we can bind up each variable in the rules
   * and match an episode sentence
   * @param ruleSentences
   * @param epSentences
   * @return
   */
  public boolean match(List<Sentence> ruleSentences, List<Sentence> epSentences) {
//    Matching rules: [[abuts(*f2 | *b1)]]
    environment.logDebug("Matching rules: " + ruleSentences);
//    Matching eps: [[abuts(foo1 | foo2)], [abuts(foo2 | bar1)]]
    environment.logDebug("Matching eps: "+epSentences);
    //key = predicate
    Map<String, List<Sentence>> r = collectPreds(epSentences);
    int len = ruleSentences.size(), len2;
    Sentence t, t2;
    List<Sentence> l;
    String svar, ovar, sbnd, obnd, xsbnd, xobnd ="", xvar,xsvar, xovar;
    for (int i = 0; i < len; i++) {
      // for each Sentence in the rule list
      // remember, there may be more than one
      // Sentence in both the Rule and in the Episode
      // for any given predicate -- multiple actors of the same class
      t = ruleSentences.get(i);
      svar = t.subject; // variable
      ovar = t.object;  // variable
      xsbnd = curBinding.getBinding(svar);
      if (!ovar.equals(""))
        xobnd = curBinding.getBinding(ovar);
      //
      l = r.get(t.predicate);
      System.out.println("Match 1: "+t.toString()+" | "+xsbnd+" | "+xobnd+" | "+l);
      if (l == null)
        return false; // don't have this predicate
      len2 = l.size();
      // for each Sentence associated with this predicate found in the Episode
      boolean temp = false;
      for (int j = 0; j < len2; j++) {
        t2 = l.get(j);
        System.out.println("Match 2: "+t2.toString());
        sbnd = t2.subject;
        obnd = t2.object;
        if (sbnd.equals(xsbnd)) {
          if (!ovar.equals("")) {
            if (obnd.equals(xobnd)) {
              temp = true;
              break;
            }
          } else {
            temp = true;
            break;
          }
        }
      }
      System.out.println("Match 3: "+temp);
      if (!temp) return false; // never found a match for that sentence
    }
    return true;
  }


  /////////////////////////////////
  // Bindings Class
  /////////////////////////////////
  class BindingsClass {
    /**
     * key: variable name
     * value: some string value
     * e.g. *foo foo49 after binding
     */
    private Map<String, Object> structures = new HashMap<String, Object>();

    public BindingsClass() {}

    public void addVariable(String var, Object nullBinding) {
      structures.put(var, nullBinding);
    }

    public void freshBindings() {
      structures.clear();
    }
    /**
     * Return the binding for var
     * @param var
     * @return can return <code>null</code>
     */
    public String getBinding(String var) {
      System.out.println("GetBinding on: "+var);
      Object o = structures.get(var);
      if (o == null) return null;
      return (String)o;
    }

    /**
     * Return <code>false</code> if this var is already bound.
     * @param var
     * @param val
     * @return
     */
    public boolean bindVar(String var, String val) {
      System.out.println("Binding 1: "+var+" "+val);
      Object o = structures.get(var);
      if (o != null) return false;
      System.out.println("Binding: 2: "+var+" "+val);
      structures.put(var,val);
      return true;
    }
    @Override
    public String toString() {
  	  return ((HashMap)structures).toString();
    }
  }
  
  /////////////////////////////////
  // Utilities
  /////////////////////////////////
  /**
   * @param ArrayList
   * @param ArrayList
   * @return ArrayList which is the Union of the two inputs
   * /
  public ArrayList unionLists(ArrayList listA, ArrayList listB) {
    ArrayList longest = listA;
    ArrayList shortest = listB;
    if (listA.size() < listB.size()) {
      longest = listB;
      shortest = listA;
    }
    ArrayList result = (ArrayList)longest.clone();
    // Union shortest into longest
    for (int i=0;i<shortest.size();i++) {
      if (!longest.contains(shortest.get(i)))
        result.add(shortest.get(i));
    }
    return result;
  }
*/
}