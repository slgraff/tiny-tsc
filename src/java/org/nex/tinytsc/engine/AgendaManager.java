/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.engine;
import java.util.*;
//log4j.jar
import org.apache.log4j.Logger;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 *
 * Agents are crafted to handle each <code>taskType</code>
 */

public class AgendaManager {
  private Logger log = Logger.getLogger(AgendaManager.class);
  /**
   * Each taskType is the key, e.g. fillin, study, etc<br>
   * Each map value is a List of <code>Task</code>s<br>
   * <code>Task</code>s are sorted on priority, highest first
   */
  private Map agenda = new HashMap();

  public AgendaManager() {
  }

  public void addTask(Task t) throws BadTaskException {
    synchronized(agenda) {
      String type = t.getTaskType();
      if (t.equals(Task.UNKNOWN))
        throw new BadTaskException("Task without a taskType");
      //TODO sort
      List tasks = (List)agenda.get(type);
      if (tasks==null) tasks = new ArrayList();
      tasks.add(t);
      agenda.put(type,tasks);
    }
  }

  public Task getNextTask(String taskType) {
    synchronized(agenda) {
      List tasks = (List)agenda.get(taskType);
      if (tasks==null) return null;
      return (Task)tasks.remove(0);
    }
  }

  /**
   * <p>The semantics of a Task are:<br>
   * <code>perform taskType onConcept c</code></p>
   * <p>It is possible to come into <code>newTask</code>
   * with a <code>taskType</code> for which no <code>agent</code>
   * exists to execute.
   * @param taskType
   * @param priority
   * @param c
   */
  public void newTask(String taskType, int priority, Concept c) {
    Task newTask = new Task(taskType,priority);
    newTask.setObject(c);
    try {
      addTask(newTask);
    } catch (BadTaskException e) {
      e.printStackTrace(); // should never happen
    }
  }
}