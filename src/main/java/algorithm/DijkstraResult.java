package algorithm;

import java.util.List;

import delivery.model.CheckPoint;
import delivery.model.Path;

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
}
