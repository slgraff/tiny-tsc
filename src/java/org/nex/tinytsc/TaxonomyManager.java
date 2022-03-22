/*
 *  Copyright (C) 2022  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import org.nex.tinytsc.engine.Environment;

/**
 * @author jackpark
 * A class which manages transitive closure
 */
public class TaxonomyManager {
	private Environment environment;
	/**
	 * 
	 */
	public TaxonomyManager(Environment env) {
		environment = env;
	}
	
	
	public void addSub(String parentId, String childId) throws Exception {
		
	}

	public void addInstance(String parentId, String childId) throws Exception {
		
	}

}
