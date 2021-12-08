package algorithm.tsp;

import java.util.Collection;
import java.util.List;

import model.*;

/**
 * Graph of checkpoints with their min paths
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class DeliveryGraph implements Graph {
	int nbVertices;
	List<? extends List<Path>> paths;
	List<CheckPoint> checkPoints;

	/**
	 * Create a graph using 
	 * 
	 * @param paths
	 */
	public DeliveryGraph(List<? extends List<Path>> paths, 
			List<CheckPoint> checkPoints){
		this.nbVertices = checkPoints.size();
		this.paths = paths;
		this.checkPoints = checkPoints;
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public double getCost(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return -1;

		Path p = paths.get(i).get(j);
		double cost;
		if (p==null) {
			cost = -1;
		} else {
			cost = p.getLength();
		}
		return cost;
	}

	@Override
	public boolean isArc(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return false;
		return paths.get(i).get(j)!=null;
	}

	@Override
	public boolean canBeVisited(int i, Collection<Integer> unvisited) {
		CheckPoint chk_i = checkPoints.get(i);
		CheckPoint chk_j;
		if (chk_i.getType()==CheckPointType.DELIVERY) {
			for (Integer j : unvisited) {
				chk_j = checkPoints.get(j);
				if (chk_j.getType()==CheckPointType.PICKUP 
						&& chk_i.getIndex()==chk_j.getIndex()) {
					return false;
				}
			}
		}
		return true;
	}
}
