package algorithm.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Level 2 of TSP implementation
 * iterates using the MinFirstIter
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class TSP3 extends TSP2 {

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new MinFirstIter(unvisited, currentVertex, g);
	}

}
