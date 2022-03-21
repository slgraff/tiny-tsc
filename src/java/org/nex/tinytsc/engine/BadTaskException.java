/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.engine;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class BadTaskException extends Exception {

  public BadTaskException() {
  }

  public BadTaskException(String message) {
    super(message);
  }

  public BadTaskException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadTaskException(Throwable cause) {
    super(cause);
  }
}