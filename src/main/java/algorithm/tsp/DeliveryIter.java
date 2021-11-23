package algorithm.tsp;

import java.util.Collection;
import java.util.Iterator;

public class DeliveryIter implements Iterator<Integer> {
	private Integer[] candidates;
	private Integer[] unavailable;
	private Integer[] pickups;
	private Integer[] deliveries;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code> 
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param unavailable
	 * @param currentVertex
	 * @param g
	 */
	public DeliveryIter(Collection<Integer> unvisited, Collection<Integer> unavailable, int currentVertex, Graph g){
		/*this.candidates = new Integer[unvisited.size()-unavailable.size()];
		this.unavailable = new Integer[unavailable.size()];
		for (Integer i : unavailable) {
			this.unavailable[this.candidates++] = i;
		}
		for (Integer s : unvisited){
			if (g.isArc(currentVertex, s) && !unavailable.contains(s))
				candidates[nbCandidates++] = s;
		}*/
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {}

}
