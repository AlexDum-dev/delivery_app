package algorithm.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator that returns the vertices in order of the nearest first
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class MinFirstIter implements Iterator<Integer> {
	List<Integer> candidates;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code> 
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public MinFirstIter(Collection<Integer> unvisited, int currentVertex, 
			Graph g){
		candidates = new ArrayList<Integer>();
		for (Integer s : unvisited){
			if (g.isArc(currentVertex, s)) {
				if (g.canBeVisited(currentVertex, unvisited)) {
					candidates.add(s);
				}
			}
		}
		Collections.sort(candidates, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				double x = g.getCost(currentVertex, o1)
						- g.getCost(currentVertex, o2);
				if (x<0) {
					return -1;
				} else if (x>0) {
					return 1;
				}
				return 0;
			}

		});
		this.nbCandidates = 0; 
	}

	@Override
	public boolean hasNext() {
		return nbCandidates < candidates.size();
	}

	@Override
	public Integer next() {
		return candidates.get(nbCandidates++);
	}

	@Override
	public void remove() {}

}
