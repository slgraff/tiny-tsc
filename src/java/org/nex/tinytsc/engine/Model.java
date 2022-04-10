/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.engine;
import java.util.*;

import org.nex.tinytsc.api.IConstants;
import org.nex.tinytsc.api.Identifiable;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 *
 * A <code>Model</code> is the <code>initialEpisode</code>
 * in an <code>Envisionment</code>. In essence, it contains
 * the entire <code>Envisionment</code> through its
 * <code>nextEpisodes</code>
 */

public class Model extends Episode implements Identifiable {
  private final int type = IConstants.EPISODE;
  private int numNodes = 1;
  private Environment environment;

  public Model() {
    setIsModel();
  }

  public Model(String _id) {
    super(_id);
    setIsModel();
  }

  public int getNodeType() {
    return type;
  }

  public void incrementNodeCount() {
//    synchronized(environment) {
      numNodes++;
//    }
  }

  public int getNodeCount() {
//    synchronized(environment) {
      return numNodes;
//    }
  }
  public void setEnvironment(Environment e) {
    environment = e;
  }
  /**
   * Probably will not use this code
   * @param e
   * @return
   */
  public int countNodes() {

    numNodes = 0; // start
    countNodes(this);
    return numNodes;
  }

  void countNodes(Episode ep) {
    if (ep == null) return;
    numNodes++;
    Iterator<String> itr = ep.listNextEpisodeIds();
    while (itr.hasNext())
      countNodes(environment.getEpisode(itr.next()));
  }
  
  @Override
  public String toString() {
	  String foo = "Model: "+ getId();
	  return foo;
  }
}