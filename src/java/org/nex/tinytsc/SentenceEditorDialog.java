/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import org.nex.tinytsc.api.IConstants;
import org.nex.tinytsc.api.ISentenceListener;
import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Sentence;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class SentenceEditorDialog extends JDialog {
	/**
	 * The two hosts are:
	 * <li><code>RuleEditorPanel</code></li>
	 * <li><code>EpisodeEditorPanel</code></li>
	 */
  ISentenceListener host;
  BorderLayout borderLayout1 = new BorderLayout();
  SentenceEditorPanel sentenceEditorPanel1 ;
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton cancelButton = new JButton();
  JButton okButton = new JButton();
  int sentenceType = 0;

  public SentenceEditorDialog(Environment env) throws HeadlessException {
    try {
    	sentenceEditorPanel1 = new SentenceEditorPanel(env);
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Called by
   * <li><code>RuleEditorPanel</code></li>
   * <li><code>EpisodeEditorPanel</code></li>
   * @param h
   * @param isRuleSentence
   */
  public void setHost(ISentenceListener h, boolean isRuleSentence) {
    host = h;
    if (isRuleSentence)
    	sentenceEditorPanel1.setIsRule();
  }

  public void setListener(TreeListener e) {
    sentenceEditorPanel1.setListener(e);
  }

  public void setRootConcept(Concept c) {
    sentenceEditorPanel1.setRootConcept(c);
  }

  private void jbInit() throws Exception {
    this.setTitle("Sentence Editor");
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(500,500);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setHgap(20);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new SentenceEditorDialog_cancelButton_actionAdapter(this));
    okButton.setText("OK");
    okButton.addActionListener(new SentenceEditorDialog_okButton_actionAdapter(this));
    this.getContentPane().add(sentenceEditorPanel1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(okButton, null);
    jPanel1.add(cancelButton, null);
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

  void clearDisplay() {
    sentenceEditorPanel1.setPredicate("");
    sentenceEditorPanel1.setSubject("");
    sentenceEditorPanel1.setObject("");
    sentenceEditorPanel1.setObjectB("");
    sentenceEditorPanel1.setTruth(true);
    sentenceEditorPanel1.setWeight("");
  }

  public void newSentence(int sentenceType) {
    setTitle(sentenceType);
    clearDisplay();
    sentenceEditorPanel1.newSentence(sentenceType);
    setVisible(true);
  }

  public void showSelf(Sentence sentence) {
    setTitle(sentence.type);
    sentenceEditorPanel1.setPredicate(sentence.predicate);
    sentenceEditorPanel1.setSubject(sentence.subject);
    sentenceEditorPanel1.setObject(sentence.object);
    sentenceEditorPanel1.setObjectB(sentence.objectB);
    sentenceEditorPanel1.setTruth(sentence.truth);
    sentenceEditorPanel1.setWeight(Double.toString(sentence.probability));
    setVisible(true);
  }

  void setTitle(int sentenceType) {
      switch(sentenceType) {
        case IConstants.ACTORS: setTitle("Actor Sentence"); break;
        case IConstants.IF_ACTORS: setTitle("IFActor Sentence"); break;
        case IConstants.IF_NOT_ACTORS: setTitle("IfNotActor Sentence"); break;
        case IConstants.THEN_ACTORS: setTitle("ThenActor Sentence"); break;
        case IConstants.RELATIONS: setTitle("Relations Sentence"); break;
        case IConstants.IF_RELATIONS: setTitle("IF_Relations Sentence"); break;
        case IConstants.IF_NOT_RELATIONS: setTitle("IfNotRelations Sentence"); break;
        case IConstants.THEN_RELATIONS: setTitle("ThenRelations Sentence"); break;
        case IConstants.STATES: setTitle("States Sentence"); break;
        case IConstants.IF_STATES: setTitle("IfStates Sentence"); break;
        case IConstants.IF_NOT_STATES: setTitle("IfNotStates Sentence"); break;
        case IConstants.THEN_STATES: setTitle("ThenStates Sentence"); break;
        case IConstants.IF_RUN: setTitle("IfRun Sentence"); break;
        case IConstants.IF_NOT_RUN: setTitle("IfNotRun Sentence"); break;
        case IConstants.THEN_RUN: setTitle("ThenRun Sentence"); break;
        case IConstants.THEN_CONJECTURE: setTitle("ThenConjecture Sentence"); break;
        case IConstants.THEN_CREATE: setTitle("ThenCreate Sentence"); break;
        case IConstants.THEN_SAY: setTitle("ThenSay Sentence"); break;
    }
  }
  public void showSelf(int sentenceType,
                       String pred,
                       String subj,
                       String obj,
                       String objB,
                       boolean truth,
                       String weight) {
    setTitle(sentenceType);
    sentenceEditorPanel1.setPredicate(pred);
    sentenceEditorPanel1.setSubject(subj);
    sentenceEditorPanel1.setObject(obj);
    sentenceEditorPanel1.setObjectB(objB);
    sentenceEditorPanel1.setTruth(truth);
    sentenceEditorPanel1.setWeight(weight);
    setVisible(true);
  }


  void okButton_actionPerformed(ActionEvent e) {
    host.acceptSentence(sentenceEditorPanel1.getIsDirty(), 
    		sentenceEditorPanel1.getSentence());
    setVisible(false);
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    setVisible(false);
  }


}

class SentenceEditorDialog_okButton_actionAdapter implements java.awt.event.ActionListener {
  SentenceEditorDialog adaptee;

  SentenceEditorDialog_okButton_actionAdapter(SentenceEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class SentenceEditorDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  SentenceEditorDialog adaptee;

  SentenceEditorDialog_cancelButton_actionAdapter(SentenceEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}