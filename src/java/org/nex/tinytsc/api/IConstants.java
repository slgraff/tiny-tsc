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

public interface IConstants {
  /**
   * Sentence types
   */
  public static final int ACTORS = 0;
  public static final int IF_ACTORS = 1;
  public static final int IF_NOT_ACTORS = 2;
  public static final int THEN_ACTORS = 3;
  public static final int RELATIONS = 4;
  public static final int IF_RELATIONS = 5;
  public static final int IF_NOT_RELATIONS = 6;
  public static final int THEN_RELATIONS = 7;
  public static final int STATES = 8;
  public static final int IF_STATES = 9;
  public static final int IF_NOT_STATES  = 10;
  public static final int THEN_STATES = 11;
  public static final int THEN_CREATE = 12;
  public static final int THEN_CONJECTURE = 13;
  public static final int THEN_SAY = 14;
  public static final int IF_RUN = 15;
  public static final int IF_NOT_RUN = 16;
  public static final int THEN_RUN = 17;

  /**
   * Tree node types
   */
  public static final int CONCEPT = 0;
  public static final int RULE = 1;
  public static final int EPISODE = 2;
  public static final int MODEL = 3;
  public static final int TASK = 4;
  public static final int UNUSED = 5;
  

}