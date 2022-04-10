/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.agents;
import java.util.*;

import org.nex.tinytsc.engine.Task;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Episode;
import org.nex.tinytsc.engine.Model;
import org.nex.tinytsc.engine.FindEpisodeUtility;
import org.nex.tinytsc.DatastoreException;
import org.nex.tinytsc.api.IAgent;
import org.nex.tinytsc.api.IConstants;


/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 *
 * <p><code>PublishEpisodeAgent</code> has the task of adding a new
 * <code>Episode</code> to a <code>Model</code>, <em>aka: Envisionment</em></p>
 * <p>The process involves walking the graph looking for a <code>previousEpisode</code>
 * that is <em>in some sense</em> <bold>the same</bold>.</p>
 * <p>When a <code>previousEpisode</code> is found to be the same or a <em>superset</em>
 * of the <code>newEpisode</code>, then the <code>previousEpisode</code> of the
 * <code>newEpisode</code> is linked to the found <code>previousEpisode</code>.<br>
 * This is classified as a loop or a cycle.</p>
 * <p>We need faster ways to perform this task</p>
 */

public class PublishEpisodeAgent extends Thread implements IAgent {
  private Environment environment;
  private FindEpisodeUtility finder;
  private boolean isRunning = true;
  private boolean isWaiting = true;
  private List tasks = new ArrayList();
  private String currentEpId = "";

  public PublishEpisodeAgent() {
  }
  public void initialize(Environment environment) {
    this.environment = environment;
    environment.registerAgent(IConstants.PUBLISH_EPISODE,this);
    finder = new FindEpisodeUtility(environment);
    this.start();
  }
  public void addTask(Task t) {
    synchronized(tasks) {
      tasks.add(t);
      tasks.notifyAll();
      environment.say("PublishEpisode adding task: "+t.getId());
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
      System.out.println("PE 3");
      //grab a Task
      synchronized(tasks) {
        while (tasks.size() == 0) {
          System.out.println("PE 3A");
          try {
            tasks.wait();
            if (!isRunning)
              return;
            System.out.println("PE 3B: " + tasks.size());
            while (isWaiting)
              tasks.wait(100);
          } catch (Exception e) {}
          if (!isRunning)
            return;
          System.out.println("PE 3C: " + tasks.size());
        }
        System.out.println("PE 3D");
        theTask = (Task) tasks.remove(0);
      } // got a Task
      if (theTask != null) {
        System.out.println("PE 3E: "+theTask.getId());
        doTask(theTask);
        System.out.println("PE 3F");
        theTask = null;
      }
    }
  }

  void doTask(Task task) {
    if (task == null) return;
    Episode e = (Episode)task.getObject();
    currentEpId = e.getId();
    environment.say("PublishEpisode on: "+currentEpId);
    System.out.println("PEd 1:");
    environment.say(e.toXML());
    System.out.println("PEd 2:");
    //The Model, itself, is a "root node" in the graph. Start there.
    Episode found = finder.findEpisode(e/*, task.getModel()*/);
    System.out.println("PEd 3: "+found);
    String ruleId = e.getMechanism();
    System.out.println("PEd 4: "+ruleId);
    String prevEpId = e.getPreviousEpisodeId(ruleId);
    System.out.println("PEd 5: "+prevEpId);
    environment.say("PublishEpisode previous: "+prevEpId);
    Episode prev = null;
    // slight chance previous just happens to be the Model itself
    // which is just fine - that happens every time a rule fires on it
    if (prevEpId.equals(task.getModel().getId())) {
        prev = task.getModel();
        prev.addNextEpisode(ruleId, e.getId());
        putModel((Model)prev);
        environment.logDebug("PublishTask prev episode "+prevEpId);   
    } else {
      prev = environment.getEpisode(prevEpId);
	    prevEpId = prev.getId();
	    environment.logDebug("PublishTask prev episode-1 "+prevEpId);   
	    System.out.println("PEd 6: "+prev);
	    if (found != null) {
	      // we have a loop -- we don't need this Episode
	      prev.addNextEpisode(ruleId, found.getId());
	      found.addPreviousEpisode(ruleId,prevEpId);
	      // update database
	      putEpisode(found);
	    } else {
	      // no loop
	      prev.addNextEpisode(ruleId, currentEpId);
	      // update database
	      putEpisode(e);
	      fillinTask(e, task.getModel());
	    }
	    System.out.println("PEd 8:");
	    putEpisode(prev);
    }
  }

  void putEpisode(Episode c) {
    try {
      environment.putEpisode(c);
    } catch (DatastoreException e) {
      e.printStackTrace();
    }
  }
  void putModel(Model c) {
	    try {
	      environment.putModel(c);
	    } catch (DatastoreException e) {
	      e.printStackTrace();
	    }
	  }
  void fillinTask(Episode newEp, Model m) {
    environment.say("PublishEpisode new fillin task on "+newEp.getId());
    Task newTask = environment.newTask();
    newTask.setTaskType(IConstants.FILLIN_NEXT_EPISODE);
    newTask.setModel(m);
    newTask.setObject(newEp);
    environment.say("PublishEpisode newTask "+newTask.getId());
    environment.addTask(newTask);
  }
}
