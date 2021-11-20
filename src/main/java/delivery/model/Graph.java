package delivery.model;

import java.util.List;
import java.util.Map;

public class Graph {
	
	private Map<String,List<Edge>> AdjacencyList;

	public Graph(Map<String, List<Edge>> adjacencyList) {
		super();
		AdjacencyList = adjacencyList;
	}

	public Map<String, List<Edge>> getAdjacencyList() {
		return AdjacencyList;
	}

	public void setAdjacencyList(Map<String, List<Edge>> adjacencyList) {
		AdjacencyList = adjacencyList;
	}
	
	
	
}
