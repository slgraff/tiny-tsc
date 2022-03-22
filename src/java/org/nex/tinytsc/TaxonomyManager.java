/*
 *  Copyright (C) 2022  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import org.nex.tinytsc.database.JDBMDatabase;
import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.engine.Environment;

/**
 * @author jackpark
 * A class which manages transitive closure
 */
public class TaxonomyManager {
	private Environment environment;
	private JDBMDatabase database;

	/**
	 * 
	 */
	public TaxonomyManager(Environment env) {
		environment = env;
		database = environment.getDatabase();
	}
	
	
	public void addSub(String parentId, String childId) throws Exception {
		Concept p = database.getConcept(parentId);
		
	}

	public void addInstance(String parentId, String childId) throws Exception {
		
	}

}
