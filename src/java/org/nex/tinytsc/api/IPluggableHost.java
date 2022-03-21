/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.api;

import org.nex.tinytsc.engine.Environment;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */
public interface IPluggableHost {
	
	/**
	 * An <code>IPluggable</code> is created with
	 * <code>Class.forName(...)</code> and installed.
	 * If the <code>IPluggable</code> needs GUI support,
	 * this interface applies.
	 * @param tabTitle 
	 * @param agent (must extend <code>Component</code>, e.g. <code>JPanel</code>)
	 */
	void addPluggable(String tabTitle, IPluggable agent);
	
	/**
	 * Returns a copy of <code>Environment</code> for the agent to use.
	 * @return
	 */
	Environment getEnvironment();

}
