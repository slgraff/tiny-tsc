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
import org.nex.tinytsc.engine.Model;

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
	
	/**
	 * Extend transitive closure on a new subclass
	 * @param parentId
	 * @param childId
	 * @throws Exception
	 */
	public void addSub(String parentId, String childId) throws Exception {
		Concept p = database.getConcept(parentId);
		
	}

	/**
	 * Extend transitive closure on a new instance
	 * @param parentId
	 * @param childId
	 * @throws Exception
	 */
	public void addInstance(String parentId, String childId) throws Exception {
		
	}
	
	/**
	 * Populate a taxonomy of concepts
	 * @param rootConcept
	 * @param treeRoot
	 * @throws Exception
	 */
	public void populateConceptTreeRoot(Concept rootConcept, ConceptTreeNode treeRoot) throws Exception {
		
	}

	/**
	 * Populate an envisionment of episodes
	 * @param rootConcept
	 * @param treeRoot
	 * @throws Exception
	 */
	public void populateModelTreeRoot(Model rootConcept, ConceptTreeNode treeRoot) throws Exception {
		
	}

}
