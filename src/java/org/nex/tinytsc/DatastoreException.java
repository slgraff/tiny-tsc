/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 *
 * <code>DatastoreException</code> exists to allow different kinds of
 * implementations of <code>IDatastore</code> to toss a uniform <code>Exception</code>
 */

public class DatastoreException extends Exception {

  public DatastoreException() {
  }

  public DatastoreException(String message) {
    super(message);
  }

  public DatastoreException(String message, Throwable cause) {
    super(message, cause);
  }

  public DatastoreException(Throwable cause) {
    super(cause);
  }
}