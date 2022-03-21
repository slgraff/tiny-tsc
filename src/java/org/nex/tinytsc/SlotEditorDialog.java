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

//import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Concept;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class SlotEditorDialog extends JDialog {
  ConceptEditor host;
  BorderLayout borderLayout1 = new BorderLayout();
  SlotEditorPanel slotEditorPanel1 = new SlotEditorPanel();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton cancelButton = new JButton();
  JButton okButton = new JButton();

  public SlotEditorDialog() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setHost(ConceptEditor h) {
    host = h;
  }

  public void setListener(TreeListener e) {
    slotEditorPanel1.setListener(e);
  }

  public void setRootConcept(Concept c) {
    slotEditorPanel1.setRootConcept(c);
  }

  public void setIsQP() {
  	slotEditorPanel1.setIsQP();
  }
  
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setTitle("Slot Editor");
    this.setSize(800,400);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setHgap(25);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new SlotEditorDialog_cancelButton_actionAdapter(this));
    okButton.setText("OK");
    okButton.addActionListener(new SlotEditorDialog_okButton_actionAdapter(this));
    this.getContentPane().add(slotEditorPanel1, BorderLayout.CENTER);
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

  void cancelButton_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  public void showSelf() {
    setVisible(true);
    this.slotEditorPanel1.enableSlotTypeButton();
  }

  public void showSelf(String id, java.util.List values) {
    this.slotEditorPanel1.setSlotType(id);
    if (values != null) {
      int len = values.size();
      for (int i=0;i<len;i++)
        this.slotEditorPanel1.addValue((String)values.get(i));
    }
    setVisible(true);
  }
  void okButton_actionPerformed(ActionEvent e) {
    String slotType = slotEditorPanel1.getSlotType();
    if (slotType.equals("")) {
      JOptionPane.showMessageDialog(null,"SlotType Missing");
      return;
    }
    host.acceptSlot(slotType,slotEditorPanel1.getValues());
    setVisible(false);
  }


}

class SlotEditorDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  SlotEditorDialog adaptee;

  SlotEditorDialog_cancelButton_actionAdapter(SlotEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class SlotEditorDialog_okButton_actionAdapter implements java.awt.event.ActionListener {
  SlotEditorDialog adaptee;

  SlotEditorDialog_okButton_actionAdapter(SlotEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}