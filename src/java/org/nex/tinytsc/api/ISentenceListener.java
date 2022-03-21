/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.api;

import org.nex.tinytsc.engine.Sentence;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */ 

public interface ISentenceListener {
  /**
   * Callback from <code>SentenceEditorDialog</code>
   * @param sentenceType
   * @param pred
   * @param subj
   * @param obj
   * @param objB
   * @param truth
   * @param weight
   * /
  void acceptSentence(int sentenceType,
                      String pred,
                      String subj,
                      String obj,
                      String objB,
                      boolean truth,
                      String weight);
*/
  /**
   * Used to fire up the <code>SentenceEditorDialog</code>
   * @param type
   */
  void newSentence(int type);
  
  void removeSentence(Sentence sentence);
  
  void displaySentence(Sentence sentence);

  /**
   * Callback from <code>SentenceEditorDialog</code>
   * @param isDirty
   * @param sentence
   */ 
  void acceptSentence(boolean isDirty, Sentence sentence);
}