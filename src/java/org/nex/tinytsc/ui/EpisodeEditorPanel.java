/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import org.nex.tinytsc.api.IConstants;
import org.nex.tinytsc.api.ISentenceListener;
import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.engine.Episode;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Model;
import org.nex.tinytsc.engine.Sentence;

import java.awt.event.*;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class EpisodeEditorPanel extends JPanel implements ISentenceListener {
  EpisodeEditorDialog host;
  Environment environment;
  SentenceEditorDialog sentenceEditor;
  Episode myEpisode = null;
  boolean isDirty = false;
  /**
   * When we display <code>Episode</code>s, then are not editable
   */
  boolean isEditable = false;
  GridLayout gridLayout1 = new GridLayout();
  SentencePanel actorPanel = new SentencePanel();
  SentencePanel statePanel = new SentencePanel();
  SentencePanel relationPanel = new SentencePanel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  TitledBorder titledBorder2;
  Border border3;
  TitledBorder titledBorder3;
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  Border border4;
  TitledBorder titledBorder4;
  Border border5;
  TitledBorder titledBorder5;
  JList nextEpisodeList = new JList();
  DefaultListModel nextEpisodeModel = new DefaultListModel();
  JList previousEpisodeList = new JList();
  DefaultListModel previousEpisodeModel = new DefaultListModel();

  public EpisodeEditorPanel(Environment env) {
	  environment = env;
    try {
    	sentenceEditor = new SentenceEditorDialog(environment);
      jbInit();
      sentenceEditor.setHost(this, false);
      nextEpisodeList.setModel(nextEpisodeModel);
      previousEpisodeList.setModel(previousEpisodeModel);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setHost(EpisodeEditorDialog h) {
  	host = h;
  }
  public void setListener(TreeListener e) {
    sentenceEditor.setListener(e);
  }

  public void setRootConcept(Concept c) {
    sentenceEditor.setRootConcept(c);
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,"Actors");
    border2 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border2,"Relations");
    border3 = BorderFactory.createEmptyBorder();
    titledBorder3 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"States");
    border4 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder4 = new TitledBorder(BorderFactory.createLineBorder(Color.white,1),"Next Episodes");
    border5 = BorderFactory.createLineBorder(Color.white,1);
    titledBorder5 = new TitledBorder(border5,"previousEpisodes");
    gridLayout1.setColumns(1);
    gridLayout1.setRows(5);
    gridLayout1.setVgap(3);
    this.setLayout(gridLayout1);
    actorPanel.setBorder(titledBorder1);
    actorPanel.setSentenceType(IConstants.ACTORS);
    actorPanel.setHost(this);
    relationPanel.setBorder(titledBorder2);
    relationPanel.setSentenceType(IConstants.RELATIONS);
    relationPanel.setHost(this);
    statePanel.setBorder(titledBorder3);
    statePanel.setSentenceType(IConstants.STATES);
    statePanel.setHost(this);
    jScrollPane1.setBorder(titledBorder4);
    jScrollPane2.setBorder(titledBorder5);
    nextEpisodeList.addMouseListener(new EpisodeEditorPanel_nextEpisodeList_mouseAdapter(this));
    previousEpisodeList.addMouseListener(new EpisodeEditorPanel_previousEpisodeList_mouseAdapter(this));
    this.add(actorPanel, null);
    this.add(relationPanel, null);
    this.add(statePanel, null);
    this.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(nextEpisodeList, null);
    this.add(jScrollPane2, null);
    jScrollPane2.getViewport().add(previousEpisodeList, null);
  }

  public void newSentence(int type) {
    sentenceEditor.newSentence(type);
  }
  public void removeSentence(Sentence sentence) {
  	if (sentence.type == IConstants.ACTORS)
  		myEpisode.removeActor(sentence);
  	else if (sentence.type == IConstants.RELATIONS)
  		myEpisode.removeRelation(sentence);
  	else if (sentence.type == IConstants.STATES)
  		myEpisode.removeState(sentence);
  	else {
  		System.out.println("Cannot remove sentence "+sentence.toString());
  		return;
  	}
  	isDirty = true;
  }

  public void displaySentence(Sentence sentence) {
  	sentenceEditor.showSelf(sentence);
  }

  public void setEpisodeId(String id) {
  	clearDisplay();
    myEpisode = environment.getEpisode(id);
    if (myEpisode == null)
    	myEpisode = environment.getModel(id);
    if (myEpisode == null)
    	throw new RuntimeException("Cannot find Episode/Model: "+id);
    host.setEpisodeId(id);
    isDirty = false;
    setIsEditable(false);
    //fill the display
    refreshDisplay();
  }

  void refreshDisplay() {
  	java.util.List l = myEpisode.getActors();
  	int len = 0;
  	if (l != null) {
  		len = l.size();
  		for (int i=0;i<len;i++)
  			actorPanel.addSentence((Sentence)l.get(i));
  	}
  	l = myEpisode.getRelations();
  	if (l != null) {
  		len = l.size();
  		for (int i=0;i<len;i++)
  			relationPanel.addSentence((Sentence)l.get(i));
  	}
  	l = myEpisode.getStates();
  	if (l != null) {
  		len = l.size();
  		for (int i=0;i<len;i++)
  			statePanel.addSentence((Sentence)l.get(i));
  	}
  	Iterator itr = myEpisode.listNextEpisodeRuleIds();
  	String rul;
  	while (itr.hasNext()) {
  		rul = (String)itr.next();
  		nextEpisodeModel.addElement(rul+" : "+(String)myEpisode.getNextEpisodes().get(rul));
  	}
  	itr = myEpisode.listPreviousEpisodeRuleIds();
  	while (itr.hasNext()) {
  		rul = (String)itr.next();
  		previousEpisodeModel.addElement(rul+" : "+(String)myEpisode.getPreviousEpisodes().get(rul));
  	}
  }
  public void setIsEditable(boolean tf) {
    isEditable = tf;
  	actorPanel.setIsEditable(tf);
  	relationPanel.setIsEditable(tf);
  	statePanel.setIsEditable(tf);
  }

  public void newInstance(String parentId) {
  	clearDisplay();
  	myEpisode = new Model();
  	isDirty = true;
  	myEpisode.setInstanceOf(parentId);
  	setIsEditable(true);
  }

  public void clearDisplay() {
    this.actorPanel.clearDisplay();
    this.relationPanel.clearDisplay();
    this.statePanel.clearDisplay();
    nextEpisodeModel.clear();
    previousEpisodeModel.clear();
  }


  /**
   * Callback from <code>SentenceEditorDialog</code>
   * @param isDirty
   * @param	sentence
   */
  public void acceptSentence(boolean isDirty, Sentence sentence) {
    if (isDirty) {
    	switch(sentence.type) {
    		case IConstants.ACTORS:
    			actorPanel.addSentence(sentence);
    			break;
    		case IConstants.RELATIONS:
    			relationPanel.addSentence(sentence);
    			break;
    		case IConstants.STATES:
    			statePanel.addSentence(sentence);
    	}
    }
  }
  public boolean getIsDirty() {
  	return isDirty;
  }

  void populateEpisode() {
  	myEpisode.setActors(actorPanel.getSentences());
  	myEpisode.setRelations(relationPanel.getSentences());
  	myEpisode.setStates(statePanel.getSentences());
  }
  public Episode getModel() {
  	populateEpisode();
  	return myEpisode;
  }

  void nextEpisodeList_mouseClicked(MouseEvent e) {
    // can't recurse to a new editor here
    // need an external way to view Episodes from within Episodes
    JOptionPane.showMessageDialog(null,"Can't display Episodes from within Episodes");
  }

  void previousEpisodeList_mouseClicked(MouseEvent e) {
    // can't recurse to a new editor here
    // need an external way to view Episodes from within Episodes
    JOptionPane.showMessageDialog(null,"Can't display Episodes from within Episodes");
  }

}

class EpisodeEditorPanel_nextEpisodeList_mouseAdapter extends java.awt.event.MouseAdapter {
  EpisodeEditorPanel adaptee;

  EpisodeEditorPanel_nextEpisodeList_mouseAdapter(EpisodeEditorPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.nextEpisodeList_mouseClicked(e);
  }
}

class EpisodeEditorPanel_previousEpisodeList_mouseAdapter extends java.awt.event.MouseAdapter {
  EpisodeEditorPanel adaptee;

  EpisodeEditorPanel_previousEpisodeList_mouseAdapter(EpisodeEditorPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.previousEpisodeList_mouseClicked(e);
  }
}