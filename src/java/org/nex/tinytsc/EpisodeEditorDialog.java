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

import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Episode;
import org.nex.tinytsc.engine.Model;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class EpisodeEditorDialog extends JDialog {
	Environment environment;
	boolean isEditable = false;
	String instanceParentId;
  EpisodeEditorPanel episodeEditorPanel1 = new EpisodeEditorPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel jLabel1 = new JLabel();
  JTextField idField = new JTextField();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JButton cancelButton = new JButton();
  JButton okButton = new JButton();

  public EpisodeEditorDialog() throws HeadlessException {
    try {
      jbInit();
      episodeEditorPanel1.setHost(this);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setListener(TreeListener e) {
    episodeEditorPanel1.setListener(e);
    environment = e.getEnvironment();
  }

  public void setRootConcept(Concept c) {
    episodeEditorPanel1.setRootConcept(c);
  }

  private void jbInit() throws Exception {
    this.setTitle("Episode Editor");
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(500,500);
    jPanel1.setLayout(flowLayout1);
    jLabel1.setText("Episode Identity: ");
    idField.setPreferredSize(new Dimension(200, 21));
    idField.setRequestFocusEnabled(true);
    idField.setText("");
    jPanel2.setLayout(flowLayout2);
    flowLayout2.setHgap(20);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new EpisodeEditorDialog_cancelButton_actionAdapter(this));
    okButton.setText("OK");
    okButton.addActionListener(new EpisodeEditorDialog_okButton_actionAdapter(this));
    this.getContentPane().add(episodeEditorPanel1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1, null);
    jPanel1.add(idField, null);
    this.getContentPane().add(jPanel2,  BorderLayout.SOUTH);
    jPanel2.add(okButton, null);
    jPanel2.add(cancelButton, null);
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

  /**
   * Callback from <code>EpisodeEditorPanel</code>
   * @param id
   */
  public void setEpisodeId(String id) {
  	this.idField.setText(id);
  }

  /**
   * Called from <code>TreeListener</code>
   *
   */
  public void showSelf() {
    episodeEditorPanel1.clearDisplay();
    this.idField.setText("");
    setVisible(true);
  }

  /**
   * Called from <code>TreeListener</code> to display a
   * selected <code>Episode</code>
   * @param ruleId
   */
  public void showSelf(String episodeId) {
    episodeEditorPanel1.setEpisodeId(episodeId);
    this.idField.setText(episodeId);
    isEditable = false;
    setVisible(true);
  }

  /**
   * Called from <code>TreeListener</code> to create a new
   * <code>Model</code>
   * Note: <code>Episodes</code> are <em>never</em> created here.
   * @param parentId
   */
  public void showNewInstance(String parentId) {
  	if (parentId == null || parentId.equals("")) return;
  	episodeEditorPanel1.newInstance(parentId);
  	idField.setText("");
  	isEditable = true;
  	instanceParentId = parentId;
  	setVisible(true);
  }

  void okButton_actionPerformed(ActionEvent e) {
    if (idField.getText().equals("")) {
    	JOptionPane.showMessageDialog(null, "Model not identified yet");
    	return;
    }
    if (isEditable) {
	    if (episodeEditorPanel1.getIsDirty()) {
	    	Episode ep = episodeEditorPanel1.getModel();
	    	ep.setId(idField.getText());
                try {
                  environment.putModel( (Model) ep);
                } catch (DatastoreException x) {
                  x.printStackTrace();
                }
	    	Concept parent = environment.getConcept(instanceParentId);
	    	if (parent == null)
	    		throw new RuntimeException("Model Missing instanceParent: "+instanceParentId);
	    	parent.addProperty("hasInstances", idField.getText());
	    }
    }
    setVisible(false);
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    setVisible(false);
  }


}

class EpisodeEditorDialog_okButton_actionAdapter implements java.awt.event.ActionListener {
  EpisodeEditorDialog adaptee;

  EpisodeEditorDialog_okButton_actionAdapter(EpisodeEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class EpisodeEditorDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  EpisodeEditorDialog adaptee;

  EpisodeEditorDialog_cancelButton_actionAdapter(EpisodeEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}
