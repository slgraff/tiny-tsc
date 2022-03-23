/*
 *  Copyright (C) 2022  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.util.Iterator;
import java.util.List;

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
		Concept c = database.getConcept(childId);
		if (p != null && c != null) {
			List<String> tcp = p.getTransitiveClosure();
			List<String> tcc = c.getTransitiveClosure();
			//deal with transitive closure
			if (tcc == null)
				c.setTransitiveClosure(tcp);
			c.addSubOf(parentId); // this adds subClass to parent
			//now store 
			database.putConcept(childId, c);
		}
		
	}

	/**
	 * Extend transitive closure on a new instance
	 * @param parentId
	 * @param childId
	 * @throws Exception
	 */
	public void addInstance(String parentId, String childId) throws Exception {
		Concept p = database.getConcept(parentId);
		if (p != null) { 
			p.addInstance(childId);
			//now store 
			database.putConcept(parentId, p);
			//We assume Child already knows what it's an instanceOf
		}
		
	}
	
	/**
	 * Populate a taxonomy of concepts - recursive
	 * @param rootConcept
	 * @param treeRoot
	 * @throws Exception
	 */
	public void populateConceptTreeRoot(Concept rootConcept, ConceptTreeNode treeRoot) throws Exception {
		System.out.println("PopulatingConceptTree " + rootConcept + " "+ treeRoot);
		List<String> subKids = rootConcept.getSubClasses();
		List<String> instanceKids = rootConcept.getInstance();
		List<String> snappers = null;
		if (subKids != null && !subKids.isEmpty()) {
			snappers = subKids;
			if (instanceKids != null && !instanceKids.isEmpty())
				snappers.addAll(instanceKids);
		}
		if (snappers != null) {
			//let's roll
			Iterator<String> itr = snappers.iterator();
			String cid;
			Concept c;
			ConceptTreeNode tn;
			while (itr.hasNext())  {
				cid = itr.next();
				c = database.getConcept(cid);
				if (c != null) {
					tn = createNode(cid, c.getNodeType());
					treeRoot.add(tn);
					// now recurse
					this.populateConceptTreeRoot(c, tn);;
				}
			}
		}
	}
	
	ConceptTreeNode createNode(String id, int type) {
	    return new ConceptTreeNode(id, type);
	}

	/**
	 * Populate an envisionment of episodes - recursive
	 * @param rootConcept
	 * @param treeRoot
	 * @throws Exception
	 */
	public void populateModelTreeRoot(Model rootConcept, ConceptTreeNode treeRoot) throws Exception {
		
	}

}
