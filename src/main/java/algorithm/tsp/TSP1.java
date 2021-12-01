package algorithm.tsp;

import java.util.Collection;
import java.util.Iterator;

/**
 * Level 1 of TSP implementation
 * 
 * @author Christine Solnon (from tsp package)
 * @version 1.0
 */
public class TSP1 extends TemplateTSP {
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
