/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.engine;
import java.util.*;



/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */
public class FindEpisodeUtility {
	  private Environment environment;
	  private Episode result = null;
	  
	/**
	 * 
	 */
	public FindEpisodeUtility(Environment e) {
		super();
		environment = e;
	}
	
	public Episode findEpisode(Episode ep) {
		try {
			new Finder(ep);
			result.wait();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	class Finder extends Thread {
		Model root;
		Episode target;
		Episode current;
		public Finder(Episode ep) {
			root = ep.getModel();
			target = ep;
			start();
		}
		
		public void run() {
			result = findEpisode(target,root);
			result.notifyAll();
		}
		  /**
		   * Recursive depthFirst walk around graph
		   * @param newEp
		   * @param prevEp
		   * @return can return <code>null</code>
		   */
		  Episode findEpisode(Episode newEp, Episode prevEp) {
		    Episode result = null;
		    Episode temp = prevEp;
		    if (newEp.compareEpisode(prevEp) > -1)
		        result = prevEp; // found it!
		    else {
		      // here we go
		      Iterator nextEps = prevEp.listNextEpisodeIds();
		      String epId;
		      Episode x;
		      while(nextEps.hasNext()) {
		      	yield();
		        epId = (String)nextEps.next();
		        x = environment.getEpisode(epId);
		        if (x == null)
		          throw new RuntimeException("PublishEpisodeAgent missing Episode: "+epId);
		        x = findEpisode(newEp,x);
		        if (x != null) {
		          result = x;
		          break;
		        }
		      }
		    }
		    return result;
		  }
	}
	

}
