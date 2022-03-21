/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.api;

import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Task;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public interface IAgent {
  /**
   * Bring a new <code>agent</code> in contact with the
   * <code>Environment</code>
   * @param environment
   */
  void initialize(Environment environment);

  /**
   * A <code>Task</code> for the <code>agent</code> to perform.
   * @param t
   */
  void addTask(Task t);
  /**
   * Wait until go()
   */
  void idle();
  /**
   * go back to work
   */
  void go();

  /**
   * kill the thread
   */
  void halt();
}
