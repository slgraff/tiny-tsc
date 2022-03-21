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

package com.touchgraph.graphlayout;

import  com.touchgraph.graphlayout.interaction.*;

import  java.awt.Point;
import  java.awt.event.MouseEvent;
import  java.awt.event.KeyEvent;
import  javax.swing.JPopupMenu;

/** TGScrollPane is a Java interface for a user interface using scrollbars
  * to set TouchGraph navigation and editing properties such as zoom, rotate
  * and locality. If a particular UI doesn't use a specific scrollbar, the 
  * corresponding method should return a null.
  *
  * @author   Murray Altheim  
  * @author   Alex Shapiro
  * @version  1.21  $Id: TGScrollPane.java,v 1.1 2006/10/31 22:31:47 admin Exp $
  */
public interface TGScrollPane {

    /** Return the TGPanel used with this TGScrollPane. */
    public TGPanel getTGPanel();

    /** Return the JPopupMenu used to toggle display of the controls. */
    public JPopupMenu getPopupMenu();

  // user interaction ...........

    // altheim: 2003-11-11: MouseEvent added below to track modifier keys

    /** An action method indicating Node <tt>node</tt> has been clicked.
      * The MouseEvent <tt>event</tt> may <b>optionally</b> be used to provide
      * information about modifier keys. The behaviour if null should be
      * identical to a node clicked with no modifier keys pressed.
      */
    public void nodeClicked( Node node, MouseEvent event );

    /** An action method indicating Node <tt>node</tt> has been double-clicked.
      * The MouseEvent <tt>event</tt> may <b>optionally</b> be used to provide
      * information about modifier keys. The behaviour if null should be
      * identical to a node clicked with no modifier keys pressed.
      */
    public void nodeDoubleClicked( Node node, MouseEvent event );

    /** An action method indicating a key event has occurred.
      * The KeyEvent <tt>event</tt> may include information about 
      * modifier keys. 
      */
    public void keyPressed( KeyEvent event );

  // navigation .................

    /** Return the HVScroll used with this TGScrollPane. */
    public HVScroll getHVScroll();

    /** Sets the horizontal offset to p.x, and the vertical offset to p.y
      * given a Point <tt>p<tt>. 
      */
    public void setOffset( Point p );

    /** Return the horizontal and vertical offset position as a Point. */
    public Point getOffset();

  // zoom .......................

    /** Return the ZoomScroll used with this TGScrollPane. */
    public ZoomScroll getZoomScroll();

    /** Set the zoom value of this TGScrollPane (allowable values between -100 to 100). */
    public void setZoomValue( int zoomValue );

    /** Return the zoom value of this TGScrollPane. */
    public int getZoomValue();

  // rotation ...................

    /** Return the RotateScroll used with this TGScrollPane. */
    public RotateScroll getRotateScroll();

    /** Set the rotation angle of this TGScrollPane (allowable values between 0 to 359). */
     public void setRotationAngle( int angle );

    /** Return the rotation angle of this TGScrollPane. */
    public int getRotationAngle();

  // locality ...................

    /** Return the LocalityScroll used with this TGScrollPane. */
    public LocalityScroll getLocalityScroll();

    /** Set the locality radius of this TGScrollPane  
      * (allowable values between 0 to 4, or LocalityUtils.INFINITE_LOCALITY_RADIUS). 
      */
    public void setLocalityRadius( int radius );

    /** Return the locality radius of this TGScrollPane. */
    public int getLocalityRadius();

  // hyperbolic .................

    /** Return the HyperScroll used with this TGScrollPane. */
    public HyperScroll getHyperScroll();

    /** Set the hyperbolic (aka "fisheye") distortion of this TGScrollPane  
      * (allowable values between 0 to 108). 
      */
    public void setHyperbolicDistortion( int value );

    /** Return the hyperbolic (aka "fisheye") distortion of this TGScrollPane. */
    public int getHyperbolicDistortion();

} // end com.touchgraph.graphlayout.TGScrollPane
