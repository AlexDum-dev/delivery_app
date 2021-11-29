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
	
	public boolean equals(Object d) {
		// If the object is compared with itself then return true 
        if (d == this) {
            return true;
        }
        
        if (!(d instanceof DijkstraResult)) {
            return false;
        }
         
        DijkstraResult tmpDijkstraResult = (DijkstraResult) d;
        
        if (tmpDijkstraResult.getPaths().equals(this.getPaths())
        		&& tmpDijkstraResult.getCheckpoints().equals(this.getCheckpoints())) {
        	return true;        	
        }
        return false;
	}
}
