/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import javax.swing.tree.DefaultTreeCellRenderer;

import org.nex.tinytsc.api.IConstants;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class ConceptTreeCellRenderer extends DefaultTreeCellRenderer {
  private ImageIcon conceptImage;
  private ImageIcon ruleImage;
  private ImageIcon episodeImage;
  private ImageIcon modelImage;
  private ImageIcon taskImage;

  public ConceptTreeCellRenderer() {
    conceptImage = new ImageIcon(org.nex.tinytsc.MainFrame.class.getResource("concept.PNG"));
    ruleImage = new ImageIcon(org.nex.tinytsc.MainFrame.class.getResource("rule.PNG"));
    episodeImage = new ImageIcon(org.nex.tinytsc.MainFrame.class.getResource("model.PNG" /*"episode.PNG"*/));
    modelImage = new ImageIcon(org.nex.tinytsc.MainFrame.class.getResource("model.PNG"));
    taskImage = new ImageIcon(org.nex.tinytsc.MainFrame.class.getResource("task.PNG"));
  }

  /**
   * Code adapted from the Java tutorial
   * @param tree
   * @param value
   * @param sel
   * @param expanded
   * @param leaf
   * @param row
   * @param hasFocus
   * @return
   */
  public Component getTreeCellRendererComponent(
                      JTree tree,
                      Object value,
                      boolean sel,
                      boolean expanded,
                      boolean leaf,
                      int row,
                      boolean hasFocus) {

      super.getTreeCellRendererComponent(
                      tree, value, sel,
                      expanded, leaf, row,
                      hasFocus);
      ConceptTreeNode cell = (ConceptTreeNode)value;
      switch (cell.getNodeType()) {
        case IConstants.CONCEPT: setIcon(conceptImage); break;
        case IConstants.RULE: setIcon(ruleImage); break;
        case IConstants.EPISODE: setIcon(episodeImage); break;
        case IConstants.MODEL: setIcon(modelImage); break;
        case IConstants.TASK: setIcon(taskImage); break;
      }

      return this;
  }


}