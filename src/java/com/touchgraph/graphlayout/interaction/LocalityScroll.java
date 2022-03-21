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
import  com.touchgraph.graphlayout.graphelements.*;

import  java.awt.event.*;
import  javax.swing.*;

/** LocalityScroll.
  *
  * @author   Alexander Shapiro
  * @author   Murray Altheim (2002-08-08; added setable default and maximum values)
  * @version  1.21  $Id: LocalityScroll.java,v 1.1 2006/10/31 22:31:49 admin Exp $
  */
public class LocalityScroll implements GraphListener 
{
    private JScrollBar localitySB;

    private TGPanel tgPanel;

    /** The default scroll value. */
    public static int scrollDef = 2;

    /** The maximum scroll value. */
    public static int scrollMax = 7;

  // ............

    public LocalityScroll( TGPanel tgp )
    {
        tgPanel=tgp;
        localitySB = new JScrollBar(JScrollBar.HORIZONTAL, scrollDef, 1, 0, scrollMax);
        localitySB.setBlockIncrement(1);
        localitySB.setUnitIncrement(1);
        localitySB.addAdjustmentListener(new LocalityAdjustmentListener());
        tgPanel.addGraphListener(this);
    }


    /** Sets the maximum scroll limit (prior to INFINITE_LOCALITY_RADIUS) to <tt>n</tt>. 
      * Allowed values are between 4 and 50.
      */
    public void setMaximum( int n )
    {
        if ( n > 4 && n < 50 ) scrollMax = n; 
        localitySB.setMaximum(scrollMax);
    }


    /** Returns the maximum scroll limit (prior to INFINITE_LOCALITY_RADIUS) as an int.
      */
    public int getMaximum()
    {
        return localitySB.getMaximum();
    }


    /** Sets the default scroll limit to <tt>n</tt>. Allowed values are
      * between 0 and the <b>current</b> value of <tt>scrollMax</tt>.
      */
    public void setDefault( int n )
    {
        if ( n > 4 && n < scrollDef ) scrollDef = n; 
    }


    public JScrollBar getLocalitySB() {
        return localitySB;
    }


    public int getLocalityRadius() {
        int locVal = localitySB.getValue();
        if ( locVal >= (scrollMax-1) ) return LocalityUtils.INFINITE_LOCALITY_RADIUS;
        else return locVal;
    }

    public void setLocalityRadius( int radius )
    {
        if ( radius <= 0 ) 
            localitySB.setValue(0);
        else if ( radius <= (scrollMax-2) ) // and > 0
            localitySB.setValue(radius);
        else // radius > 5
            localitySB.setValue(scrollMax-1);        
    }

    public void graphMoved() {} //From GraphListener interface
    public void graphReset() { localitySB.setValue(scrollDef); } //From GraphListener interface

    /** Included to permit overriding. Used by the private class LocalityAdjustmentListener. */
    protected Node getSelect()
    {
        return tgPanel.getSelect();                        
    } // altheim: 2003-10-31

    // altheim: 2003-10-31 capitalized class name
    private class LocalityAdjustmentListener implements AdjustmentListener 
    {
        public void adjustmentValueChanged( AdjustmentEvent e )
        {
            Node select = getSelect();                        
            if ( select!=null || getLocalityRadius() == LocalityUtils.INFINITE_LOCALITY_RADIUS) {
                try {
                    tgPanel.setLocale(select, getLocalityRadius());
                } catch ( TGException ex ) {
                    System.out.println("Error setting locale");
                    ex.printStackTrace();
                }
            }
        }
    }

} // end com.touchgraph.graphlayout.interaction.LocalityScroll
