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

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class TextDialog extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton cancelButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea textArea = new JTextArea();
  public TextDialog() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setTitle("Text View Dialog");
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(500,500);
    jPanel1.setLayout(flowLayout1);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new TextDialog_cancelButton_actionAdapter(this));
    textArea.setEditable(false);
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(cancelButton, null);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(textArea, null);
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

  public void showSelf(String data) {
    this.textArea.setText(data);
    setVisible(true);
  }
  void cancelButton_actionPerformed(ActionEvent e) {
    setVisible(false);
  }


}

class TextDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  TextDialog adaptee;

  TextDialog_cancelButton_actionAdapter(TextDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}