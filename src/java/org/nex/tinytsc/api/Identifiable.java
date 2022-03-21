/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.api;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public interface Identifiable {

  /**
   * Returns the <code>id</code> of this object
   * @return
   */
  String getId();

  /**
   * Output the object as an XML string
   * @return
   */
  String toXML();

  /**
   * Returns the node type found in IConstansts
   * @return
   */
  int getNodeType();

}
