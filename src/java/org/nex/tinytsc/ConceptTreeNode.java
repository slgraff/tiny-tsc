/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class ConceptTreeNode extends DefaultMutableTreeNode {
  int nodeType = 0;

  public ConceptTreeNode(String name, int type) {
    super(name);
    nodeType = type;
  }

  public void setNodeType(int type) {
    nodeType = type;
  }

  public int getNodeType() {
    return nodeType;
  }
}