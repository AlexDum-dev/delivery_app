package algorithm.tsp;

import java.util.Collection;

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
