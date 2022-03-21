/*
 * TouchGraph LLC. Apache-Style Software License
 *
 *
 * Copyright (c) 2001-2002 Alexander Shapiro. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by 
 *        TouchGraph LLC (http://www.touchgraph.com/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "TouchGraph" or "TouchGraph LLC" must not be used to endorse 
 *    or promote products derived from this software without prior written 
 *    permission.  For written permission, please contact 
 *    alex@touchgraph.com
 *
 * 5. Products derived from this software may not be called "TouchGraph",
 *    nor may "TouchGraph" appear in their name, without prior written
 *    permission of alex@touchgraph.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL TOUCHGRAPH OR ITS CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 *
 */

package com.touchgraph.graphlayout.interaction;

import  com.touchgraph.graphlayout.*;

import  java.io.*;
import  java.util.*;
import  java.awt.*;
import  java.applet.*;

import  java.awt.event.*;

/**  DragAddUI contains code for adding nodes + edges by dragging.
  *   
  * @author   Alexander Shapiro                                        
  * @version  1.21  $Id: DragAddUI.java,v 1.1 2006/10/31 22:31:48 admin Exp $
  */
public class DragAddUI extends TGAbstractDragUI implements TGPaintListener {

    Point mousePos = null;
    Node dragAddNode = null;

  // ............

   /** Constructor with provided TGPanel <tt>tgp</tt>.
     */ 
    public DragAddUI( TGPanel tgp ) {
        super(tgp); 
    }

    public void preActivate() {
        mousePos=null;
        tgPanel.addPaintListener(this);
    }

    public void preDeactivate() {
        tgPanel.removePaintListener(this);
    };

    public void mousePressed( MouseEvent e ) {
        dragAddNode = tgPanel.getMouseOverN();
    }    

    public void mouseReleased( MouseEvent e ) {
        Node mouseOverN = tgPanel.getMouseOverN();

        if (mouseOverN!=null && dragAddNode!=null && mouseOverN!=dragAddNode) {
            Edge ed=tgPanel.findEdge(dragAddNode,mouseOverN);
            if (ed==null) tgPanel.addEdge(dragAddNode,mouseOverN, Edge.DEFAULT_LENGTH); 
            else tgPanel.deleteEdge(ed);

        } else if ( mouseOverN == null && dragAddNode != null ) {
            try {
                Node n =tgPanel.addNode(); 
                tgPanel.addEdge(dragAddNode,n, Edge.DEFAULT_LENGTH); 
                n.drawx = tgPanel.getMousePos().x; 
                n.drawy = tgPanel.getMousePos().y;
                tgPanel.updatePosFromDraw(n); 
            } catch ( TGException tge ) {
                System.err.println(tge.getMessage());
                tge.printStackTrace(System.err);
            }
        }

        if (mouseWasDragged) { //Don't reset the damper on a mouseClicked
            tgPanel.resetDamper();
            tgPanel.startDamper();
        }   

        dragAddNode=null;
    }

    public void mouseDragged(MouseEvent e) {    
        mousePos=e.getPoint();
        tgPanel.repaint();
    }

    public void paintFirst(Graphics g) {};
    public void paintLast(Graphics g) {};

    public void paintAfterEdges(Graphics g) {

        if(mousePos==null) return;

        Node mouseOverN = tgPanel.getMouseOverN();

        if (mouseOverN==null) {
            g.setColor(Node.BACK_DEFAULT_COLOR);
            g.drawRect(mousePos.x-7, mousePos.y-7, 14, 14);
        }
        Color c;
        if (mouseOverN==dragAddNode) {
            c = Color.darkGray;
        } else {
            c = Color.blue;
        }
        paintArrow(g,(int)dragAddNode.drawx,(int)dragAddNode.drawy,
               mousePos.x,mousePos.y,c);
    }

    public static void paintArrow( Graphics g, int x1, int y1, int x2, int y2, Color c ) 
    {
        g.setColor(c);

        int x3=x1;
        int y3=y1;

        double dist=Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        if (dist>10) {
            double adjustDistRatio = (dist-10)/dist;
            x3=(int) (x1+(x2-x1)*adjustDistRatio);
            y3=(int) (y1+(y2-y1)*adjustDistRatio);
        }

        x3=(int) ((x3*4+x1)/5.0);
        y3=(int) ((y3*4+y1)/5.0);

        g.drawLine(x3,   y3,   x2, y2);
        g.drawLine(x1,   y1,   x3, y3);
        g.drawLine(x1+1, y1,   x3, y3);
        g.drawLine(x1+2, y1,   x3, y3);
        g.drawLine(x1+3, y1,   x3, y3);
        g.drawLine(x1+4, y1,   x3, y3);
        g.drawLine(x1-1, y1,   x3, y3);
        g.drawLine(x1-2, y1,   x3, y3);
        g.drawLine(x1-3, y1,   x3, y3);
        g.drawLine(x1-4, y1,   x3, y3);
        g.drawLine(x1,   y1+1, x3, y3);
        g.drawLine(x1,   y1+2, x3, y3);
        g.drawLine(x1,   y1+3, x3, y3);
        g.drawLine(x1,   y1+4, x3, y3);
        g.drawLine(x1,   y1-1, x3, y3);
        g.drawLine(x1,   y1-2, x3, y3);
        g.drawLine(x1,   y1-3, x3, y3);
        g.drawLine(x1,   y1-4, x3, y3);
    }


} // end com.touchgraph.graphlayout.interaction.DragAddUI
