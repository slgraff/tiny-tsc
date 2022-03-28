/*
 *  Copyright (C) 2006  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.ui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExportDialog
    extends JDialog {

  MainFrame host;

  public ExportDialog() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  public void setHost(MainFrame h) {
    host = h;
  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    okButton.addActionListener(new ExportDialog_okButton_actionAdapter(this));
    cancelButton.addActionListener(new ExportDialog_cancelButton_actionAdapter(this));
    this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    this.setSize(300,300);
    jPanel1.setLayout(borderLayout2);
    jPanel2.setLayout(flowLayout1);
    jPanel4.setLayout(flowLayout2);
    jLabel1.setText("Click OK to Export Entire Database");
    jLabel2.setText("Future expansion");
    okButton.setText("    OK    ");
    cancelButton.setText("Cancel");
    jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
    jPanel2.add(jLabel2);
    jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);
    jPanel3.add(cancelButton);
    jPanel3.add(okButton);
    jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);
    jPanel4.add(jLabel1);
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

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();

  public void showSelf() {
    this.setVisible(true);
  }
  public void okButton_actionPerformed(ActionEvent e) {
    host.processExport();
    this.setVisible(false);
  }

  public void cancelButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}

class ExportDialog_cancelButton_actionAdapter
    implements ActionListener {
  private ExportDialog adaptee;
  ExportDialog_cancelButton_actionAdapter(ExportDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class ExportDialog_okButton_actionAdapter
    implements ActionListener {
  private ExportDialog adaptee;
  ExportDialog_okButton_actionAdapter(ExportDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}
