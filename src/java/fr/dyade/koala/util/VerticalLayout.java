/*
 * The contents of this file are subject to the Dyade Public License, 
 * as defined by the file DYADE_PUBLIC_LICENSE.TXT
 *
 * You may not use this file except in compliance with the License. You may
 * obtain a copy of the License on the Dyade web site (www.dyade.fr).
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific terms governing rights and limitations under the License.
 *
 * The Original Code is Koala Graphics, including the java package 
 * fr.dyade.koala, released July 10, 2000.
 *
 * The Initial Developer of the Original Code is Dyade. The Original Code and
 * portions created by Dyade are Copyright Bull and Copyright INRIA. 
 * All Rights Reserved.
 */
/* Copyright (c) 1997 by Groupe Bull.  All Rights Reserved */
/* $Id: VerticalLayout.java,v 1.1 2006/10/31 22:31:49 admin Exp $ */
/* Author: Jean-Michel.Leon@sophia.inria.fr */

package fr.dyade.koala.util;

import java.awt.*;

/**
 * A simple layout that arranges its components vertically
 * all the components are given the same width and keep their own height.
 * 
 */

public class VerticalLayout implements LayoutManager {

    final static private int VGAP = 0;
    final static private int HMARGIN = 0;
    final static private int VMARGIN = 0;

    protected int vgap;
    protected int hmargin;
    protected int vmargin;
    
    /**
     * Constructs a new VerticalLayout.
     */
    public VerticalLayout() {
	this(VGAP, HMARGIN, VMARGIN);
    }
    
    /**
     * Constructs a new VerticalLayout with specific gap and margin.
     */
    public VerticalLayout(int gap, int h, int v) {
	vgap = gap;
	hmargin = h;
	vmargin = v;
    }

    /**
     * Adds the specified named component to the layout.
     * @param name the String name
     * @param comp the component to be added
     */
    public void addLayoutComponent(String name, Component comp) {}

    /**
     * Removes the specified component from the layout.
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp) {}

    
    public Dimension minimumLayoutSize(Container target) {
	return preferredLayoutSize(target);
    }
     
    public Dimension preferredLayoutSize(Container target) {
	Dimension dim = new Dimension(0, 0);
	int width = 0;
	int height = 0;
	Component children[] = target.getComponents();
	
	for(int i=0; i < children.length; i++) {
	    Dimension d = children[i].getPreferredSize();
	    width = Math.max(width, d.width);
	    height += d.height;
	}
	height += vgap * (children.length + 1) + vmargin*2*children.length;
	Insets insets = target.getInsets();
	dim.width = width + insets.left + insets.right + hmargin*2;
	dim.height = height + insets.top + insets.bottom;

	return dim;
    }
    
    
    /**
     * Lays out the specified container.
     * @param target the component being laid out
     * @see Container
     */
    public void layoutContainer(Container target) {
	Insets insets = target.getInsets();
	int top = insets.top, left = insets.left + hmargin;
	int width = target.getSize().width - left - insets.right - hmargin;
	Component children[] = target.getComponents();
	// available vertical space
	int vroom =  target.getSize().height - top - insets.bottom - vmargin*2;
	
	top += vgap;
	for(int i=0; i < children.length-1; i++) {
	    int h = children[i].getPreferredSize().height + vmargin * 2;
	    children[i].setBounds(left, top, width, h);
	    top += h+vgap;
	    vroom -= (h+vgap);
	}
	int last = children.length - 1;
	int h = children[last].getPreferredSize().height + vmargin * 2;
	children[last].setBounds(left, top, width, Math.min(h, vroom));
    }
     
    /**
     * Returns the String representation of this BorderLayout's values.
     */
    public String toString() {
	return getClass().getName() + ",vgap=" + vgap + "]";
    }

}
