package algorithm.dijkstra;

import java.util.List;

import model.CheckPoint;
import model.Path;

/**
 * Container class for Dijkstra result
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class DijkstraResult {
	List<? extends List<Path>> paths;
	List<CheckPoint> checkpoints;

	public DijkstraResult(List<? extends List<Path>> paths, List<CheckPoint> checkpoints) {
		this.paths = paths;
		this.checkpoints = checkpoints;
	}

	public List<? extends List<Path>> getPaths() {
		return paths;
	}

	public List<CheckPoint> getCheckpoints() {
		return checkpoints;
	}

	public boolean equals(Object d) {
		// If the object is compared with itself then return true 
		if (d == this) {
			return true;
		}

		if (!(d instanceof DijkstraResult)) {
			return false;
		}

		DijkstraResult tmp = (DijkstraResult) d;

		if (tmp.getPaths().equals(this.getPaths())
				&& tmp.getCheckpoints().equals(this.getCheckpoints())) {
			return true;        	
		}
		return false;
	}
}
