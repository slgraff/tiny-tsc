/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.agents;
import java.util.*;

import org.nex.tinytsc.api.IActorCarrier;
import org.nex.tinytsc.api.IAgent;
import org.nex.tinytsc.api.IConstants;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Task;
import org.nex.tinytsc.engine.Episode;
import org.nex.tinytsc.engine.Rule;
import org.nex.tinytsc.engine.Sentence;
import org.nex.tinytsc.engine.SimpleBindingEngine;
import org.nex.tinytsc.engine.Model;
import org.nex.tinytsc.engine.InferenceEngine;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 *
 * <p>A Core QP functionality is to build an <code>Envisionment</code><br>
 * To build an <code>Envisionment</code>:
 * <li>Start with initial conditions, a <code>Model</code></li>
 * <li>FillinNextEpisode on <code>Model</code></li>
 * <li>PublishEpisode for each <code>Episode</code> made</li>
 * <li>For each <code>Episode</code> made, FillinNextEpisode</li>
 * <li>until <em>stopping conditions<em> are detected</li></p>
 * <p><em>Stopping conditions</em> exist when:
 * <li>no more <code>Rule</code>s to fire</li>
 * <li><em>stopping</em> <code>Rule</code>s fire</li></p>
 * <p>Note: this can be made faster by building a <code>Lucene</code> index
 * of all <code>Concept</code>s, <code>Rule</code>s, and <code>Episode</code>s
 * in order to speed up finding <code>Rule</code>s to fire.</p>
 * <p>Right now, the algorithm must cycle through every <code>Rule</code>. That
 * won't scale when we have thousands of <code>Rule</code>s.</p>
 * <p>An alternative way to collect rules, which earlier TSC implementations used,
 * is to bring in the <code>Concept</code> associated with each <code>predicate</code>
 * and collect it's <code>rules</code> list.<br>
 * That approach relies on an import agent or compiler to
 * aggressively add a ruleId to each predict <code>Concept</code></p>
 *
 * TODO:
 *   ifNotActors
 *   ifNotRelates
 *   ifNotStates
 *   ripple up isA
 *   check synonyms
 *   create a <code>Concept</code> for any new existential actor created
 */

public class FillinNextEpisodeAgent extends Thread implements IAgent {
  private Environment environment;
  private SimpleBindingEngine bindings;
  private InferenceEngine inferenceEngine;

  private boolean isRunning = true;
  private boolean isWaiting = true;
  private List tasks = new ArrayList();
  private String currentEpId = "";

  public FillinNextEpisodeAgent() {
  }

  /**
   * Bring a new <code>agent</code> in contact with the
   * <code>Environment</code>
   * @param environment
   */
  public void initialize(Environment environment) {
    this.environment = environment;
    this.inferenceEngine = new InferenceEngine(environment);
    bindings = new SimpleBindingEngine(environment);
    environment.registerAgent(IConstants.FILLIN_NEXT_EPISODE,this);
    this.start();
  }

  public void addTask(Task t) {
    synchronized(tasks) {
      tasks.add(t);
      tasks.notifyAll();
      environment.say("FillinNextEpisode adding task: "+t.getId());
    }
  }

  public int getQueueSize() {
    synchronized(tasks) {
      return tasks.size();
    }
  }

  public String getCurrentEpId() {
    return currentEpId;
  }
  /**
   * Wait until go()
   */
  public void idle() {
    isWaiting = true;
  }
  /**
   * go back to work
   */
  public void go() {
	  environment.logDebug("FillinNextEpisode Go");
    isWaiting = false;
  }

  /**
   * kill the thread
   */
  public void halt() {
    isRunning = false;
  }

  public void run() {
    Task theTask=null;
    while (isRunning) {
      //grab a Task
      synchronized(tasks) {
        while (tasks.size() == 0) {
          System.out.println("FNE 3A: " + tasks.size());
          try {
            tasks.wait();
            if (!isRunning)
              return;
            System.out.println("FNE 3B: " + tasks.size());
            while (isWaiting)
              tasks.wait(100);
          }catch (Exception e) {}
          if (!isRunning)
            return;
          System.out.println("FNE 3C: " + tasks.size());
        }
        System.out.println("FNE 3D");
        theTask = (Task)tasks.remove(0);
        if (theTask != null) {
          environment.logDebug("FNE 3E: " + theTask.getId());
          doTask(theTask);
          System.out.println("FNE 3F");
          theTask = null;
        }
      }
   }
  }

  void doTask(Task task) {
    System.out.println("DT 0");
    Episode e = (Episode)task.getObject();
    System.out.println("DT 1");
    environment.setCurrentModel(task.getModel());
    System.out.println("DT 2");
    currentEpId = e.getId();
    System.out.println("DT 3");
    environment.say("FillinNextEpisode on: "+currentEpId);
    System.out.println("DT 4");
    List rules = collectRules(e);
    System.out.println("DT 5: "+rules.size());
    int len = rules.size();
    Rule rul;
    Episode ep;
    environment.say("FillinNextEpisode testing "+len+" rules");
    for (int i=0;i<len;i++) {
      environment.say("FillinNextEpisode pushing bindings");
      environment.logDebug("FillinNextEpisode pushing bindings");
      // fresh binding for each rule
      bindings.freshBindings();
      rul = (Rule) rules.get(i);
      System.out.println("DT 2: "+rul.getId());
      environment.say("FillinNextEpisode testing rule "+rul.getId());
      if (testRule(e, rul)) {
        ep = fireRule(e, rul);
        publishTask(ep, task.getModel());
      }
    }
  }

  /**
   * <code>Episode</code> knows its <code>Rule</code> and its <code>previousEpisode</code>
   * @param e
   */
  void publishTask(Episode e, Model m) {
    environment.say("FillinNextEpisode new publish task on "+e.getId());
    Task newTask = environment.newTask();
    newTask.setTaskType(IConstants.PUBLISH_EPISODE);
    newTask.setModel(m);
    newTask.setObject(e);
    environment.say("FillinNextEpisode newTask "+newTask.getId());
    environment.addTask(newTask);
  }
  /**
   * <p>See if a <code>Rule</code> <em>can fire</em> on the given <code>Episode</code></p>
   * <p>If a <code>Rule</code> has already fired on this <code>Episode</code>,
   * then, don't fire it again.
   * @param e
   * @param r
   * @return
   */
  boolean testRule(Episode e, Rule r) {
    environment.say("FillinNextEpisode testing rule "+r.getId()+" on episode "+e.getId());
    System.out.println("TR 1: "+e.ruleHasFired(r.getId()));
    if (e.ruleHasFired(r.getId())) return false;
    boolean result = true;
    // Lists of Sentences
    List eActors = e.getActors();
    List rActors = r.getActors();
    System.out.println("TR 2: "+eActors);
    System.out.println("TR 3: "+rActors);
//    TR 2: [[stem.cell(stem.cell1 | )], [helper.t-cell(helper.t-cell1 | )], [macrophage(Macrophage1 | )], [b-cell(B-cell1 | )], [activated.b-cell(activated.b-cell1 | )], [act.h.t-cell(act.h.t-cell1 | )], [gm-csf(csf1 | )], [il(il1 | )], [il-2(il2 | )], [virus(virus1 | )], [antigen(antigen1 | )], [cytokine.receptor(cytokine.receptor1 | )], [plasma.cell(plasma.cell1 | )], [memory.cell(memory.cell1 | )], [blast.cell(blast.cell1 | )], [antibody(antibody1 | )]]
//    TR 3: [[antigen(*antigen | )], [macrophage(*macrophage | )]]
    // quick check for predicates matching
    boolean tru = false;
    if (rActors.size() == eActors.size()) {
      int len = rActors.size(), len2 = eActors.size();
      String p;
      tru = false;
      for (int i=0;i<len;i++) {
        p = ((Sentence)rActors.get(i)).predicate;
        for (int j=0;j<len2;j++)
          if ((tru = ((Sentence)eActors.get(j)).samePredicate(p)))
            break;
      }
    }
    if (!tru) return false; // move along
    // might want to check for null values
    if (bindings.bind(rActors,eActors)) {
      System.out.println("TR 4:");
      List eVals = e.getRelations();
      List rVals = null;
      if (eVals != null) {
        // relations are a bit more tricky than actors.
        // an Episode can have relations that Rules don't care about
        // but if a Rule cares and the Episode doesn't have any, this binding is hosed
        eVals = cloneSentence(eVals);
        rVals = r.getIfNotRelations();
        //match variables on NOT relations
        if (rVals != null)
          result = !bindings.match(rVals,eVals);
        if (!result) return false;
        rVals = r.getIfRelations();
        //match variables on relations
        if (rVals != null)
          result = bindings.match(rVals,eVals);
      }
      System.out.println("TR 5: "+result);
      if (result) {
        //still running!
        eVals = e.getStates();
        System.out.println("TR 6 : "+eVals);
        if (eVals != null) {
          eVals = cloneSentence(eVals);
          rVals = r.getIfNotStates();
          //match variables on NOT states
          System.out.println("NOT STATES "+rVals);
          if (rVals != null)
            result = !bindings.match(rVals,eVals);
          if (!result) return false;
          rVals = r.getIfRelations();
          rVals = r.getIfStates();
          System.out.println("TR 7: "+rVals);
          //match variables on states
          if (rVals != null)
            result = bindings.match(rVals,eVals);
        }
      }
    }
    System.out.println("TR 8: "+result);
    environment.say("FillinNextEpisode tested rule "+r.getId()+" on episode "+e.getId()+" : "+result);
    return result;
  }

  /**
   * 1- Build an Episode
   * 2- Manipulate the slots in the new Episode according to
   *    changes made by the Rule's consequent clauses
   * @param parentEpisode
   * @param rule that fired
   * @return newEpisode
   */
  Episode fireRule(Episode parent, Rule r) {
    environment.say("FillinNextEpisode firing rule "+r.getId()+" on episode "+parent.getId());
    Episode result = environment.newEpisode(parent.getModel());
    result.setMechanism(r.getId());
    result.addPreviousEpisode(r.getId(),parent.getId());
    Sentence temp = null;
    int len = 0;
    // deal with thenCreates for new actors
    List rThenVals = r.getThenCreates();
    List eThenVals;
    if (rThenVals != null) {
      len = rThenVals.size();
      String n;
      for (int i=0;i<len;i++) {
        n = (String)rThenVals.get(i);
        bindings.addBinding(n, environment.newExistentialId());
      }
    }
    // deal with actors
    rThenVals = r.getThenActors();
    List eVals = cloneSentence(parent.getActors());
    if (rThenVals == null)
      result.setActors(eVals);
    else {
      // might be a new actor, might be a "false" actor -- got killed
      len = rThenVals.size();
      for (int i=0;i<len;i++) {
        temp = (Sentence)rThenVals.get(i);
        // this binding required the existential bindings from thenCreate
        temp = bindings.bindSentence(temp);
        if (temp != null && temp.truth) {
          // a new actor
          result.addActor(temp);
          //TODO create a new concept for this actor
        } else {
          // an actor got killed -- remove it from eVals
          eVals.remove(temp);
        }
      }
      // move the rest of the actors, if any, over
      len = eVals.size();
      for (int i=0;i<len;i++)
        result.addActor((Sentence)eVals.get(i));
    }
    // deal with relations
    eThenVals = parent.getRelations();
    if (eThenVals != null)
      eThenVals = cloneSentence(eThenVals);
    rThenVals = r.getThenRelations();
    System.out.println("RLNS: "+rThenVals);
    if (rThenVals != null) {
      // we have relations!
      len = rThenVals.size();
      for (int i=0;i<len;i++) {
        temp = (Sentence)rThenVals.get(i);
        if (temp.truth)
          result.addRelation(bindings.bindSentence(temp));
        else {
          if (eThenVals != null)
            System.out.println("Removing sentence: "+temp.toString()+" "+eThenVals.size());
            eThenVals.remove(temp);
            System.out.println("Removed sentence "+eThenVals.size());
        }
      }
    }
    //move the rest of the relations
    if (eThenVals != null && eThenVals.size() > 0) {
      result.addRelations(eThenVals);
    }
    // deal with states
    eThenVals = parent.getStates();
    if (eThenVals != null)
      eThenVals = cloneSentence(eThenVals);
    rThenVals = r.getThenStates();
    System.out.println("STAS: "+rThenVals);
    if (rThenVals != null) {
      // we have states!
      len = rThenVals.size();
      for (int i=0;i<len;i++) {
        temp = (Sentence)rThenVals.get(i);
        if (temp.truth)
          result.addState(bindings.bindSentence(temp));
        else {
          if (eThenVals != null)
            System.out.println("Removing sentence: "+temp.toString()+" "+eThenVals.size());
            eThenVals.remove(temp);
            System.out.println("Removed sentence "+eThenVals.size());
        }
      }
    }
    rThenVals = r.getThenSays();
    if (rThenVals != null)
      environment.say((String)rThenVals.get(0));
      //move the rest of the states
      if (eThenVals != null && eThenVals.size() > 0) {
        result.addStates(eThenVals);
      }
    //TODO
    //  thenConjectures
    //  thenTerminate
    return result;
  }

  List collectRules(Episode e) {
    List result = new ArrayList();
    System.out.println("CollectRules 1");
    Set preds = getPredicates(e);
    System.out.println("CollectRules 2");
    environment.say("FillinNextEpisode got "+preds.size()+" predicates.");
    List rules = environment.getAllRules();
    System.out.println("CollectRules 3");
    environment.say("FillinNextEpisode got "+rules.size()+" rules.");
    result = filterRules(rules, preds);
    System.out.println("CollectRules 4");
    environment.say("FillinNextEpisode filtered "+rules.size()+" rules.");
    return result;
  }

  /**
   * Return just those <code>Rule</code>s which contain,
   * <em>at least</em> the given <code>predicates</code>
   * @param allRules
   * @param predicates
   * @return
   */
  List filterRules(List allRules, Set predicates) {
    System.out.println("FilterRules "+allRules.size()+" "+predicates.size());
    List result = new ArrayList();
    Set rulePreds;
    int len = allRules.size();
    Rule rul;
    environment.say("FillinNextEpisode filtering on predicates: "+predicates);
    for (int i=0;i<len;i++) {
      rul = (Rule)allRules.get(i);
      System.out.println("FilterRules 2: "+rul.getId());
      rulePreds = getPredicates(rul);
      environment.say("FillinNextEpisode checking rule preds: "+rulePreds);
      if (predicates.containsAll(rulePreds))
        result.add(rul);
    }
    System.out.println("FilterRules 3: "+result.size());
    return result;
  }

  /**
   * Return all the <code>predicate</code>s found
   * in the <code>actors</code> of this object.
   * @param e
   * @return
   */
  Set getPredicates(IActorCarrier e) {
    Set result = new HashSet();
    List actors = e.getActors();
    int len = actors.size();
    Sentence t;
    for (int i=0;i<len;i++) {
      t = (Sentence)actors.get(i);
      result.add(t.predicate);
    }
    return result;
  }

  /**
   * Utility method to clone a list of <code>Sentence</code>s
   * @param l
   * @return
   */
  List cloneSentence(List l) {
    if (l == null) return null;
    List result = new ArrayList();
    int len = l.size();
    for (int i=0;i<len;i++)
      result.add(((Sentence)l.get(i)).copy());
    return result;
  }
}
