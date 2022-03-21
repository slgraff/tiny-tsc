/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */

package org.nex.util.cache;
import java.util.*;

/**
 * @author <a href="mailto:jackpark@gmail.com">Jack Park</a>
 * @version $Id: LRUObjectCache.java,v 1.1 2006/10/31 22:31:51 admin Exp $
 *
 */
public class LRUObjectCache {
	private List identities = null;
	private HashMap objects = null;
	private int cacheSize = 0;

	/**
	 * Create a cache of a given size
	 * This cache throws away objects
	 * @param cacheSize
	 */
	public LRUObjectCache(int cacheSize) {
		super();
		this.cacheSize = cacheSize;
		identities = new ArrayList(cacheSize);
		objects = new HashMap();
	}

	/**
	 * If <code>identities</code> contains <code>id</code>
	 * move <code>id</code> to the front of the list
	 * and return the object
	 * @param id
	 * @return can return null
	 */
	public Object get(String id) {
		synchronized(identities) {
			Object result = null;
			boolean contains = identities.remove(id);
			if (contains) {
				// add it to the front of the list
				identities.add(0,id);
				result = objects.get(id);
			}
			return result;
		}
	}

	/**
	 * Add an object to the cache.
	 * If cache is full, remove the least recently used object
	 * which is the <em>last</em> id in <code>identifiers</code>
	 * Add the new object as most recently used.
	 * @param id
	 * @param obj
	 */
	public void put(String id, Object obj) {
		synchronized(identities) {
			// if we are full, lop off the last item
			if (identities.size() > (cacheSize-1)) {
				String key = (String)identities.get(cacheSize-1);
				objects.remove(key);
				identities.remove(cacheSize-1);
			}
			identities.add(0,id);
			objects.put(id,obj);
		}
	}

	/**
	 * Take an object and its identity out of the cache
	 * @param id
	 */
	public void remove(String id) {
		synchronized(identities) {
			if (identities.remove(id))
				objects.remove(id);
		}
	}
}
