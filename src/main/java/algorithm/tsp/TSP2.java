package algorithm.tsp;

import java.util.Collection;

/**
 * Level 2 of TSP implementation
 * search a minimal bound by taking the minimal edge cost for each vertex
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class TSP2 extends TSP1 {

	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		int sumCosts = 0;
		for (Integer i : unvisited) {
			double minCost = Double.MAX_VALUE;
			for (Integer j : unvisited) {
				if (g.isArc(i, j)) {
					if (minCost > g.getCost(i, j)) {
						minCost = g.getCost(i, j);
					}
				}
			}
			if (g.isArc(i, 0)) {
				if (minCost > g.getCost(i, 0)) {
					minCost = g.getCost(i, 0);
				}
			}
			sumCosts += minCost;
		}
		return sumCosts;
	}
}
