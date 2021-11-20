package delivery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to create a graph for algorithms
 * 
 * @author 4IF Group H4144
 * @version 1.0 20 Nov 2021
 */
public class Graph {
	
	private Map<String,List<Edge>> adjacencyList;
	
	public Graph() {
		super();
		adjacencyList = new HashMap<String, List<Edge>>();
	}
	
	public Graph(Map<String, List<Edge>> adjacencyList) {
		super();
		this.adjacencyList = adjacencyList;
	}

	public Map<String, List<Edge>> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(Map<String, List<Edge>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	/**
	 * Adds an edge to the graph
	 * @param adjacent an edge
	 */
	public void addEdge(Edge adjacent) {
		String originId = adjacent.getOrigin().getId();
		List<Edge> l = adjacencyList.get(originId);
		if (l==null) {
			l = new ArrayList<Edge>();
			adjacencyList.put(originId, l);
		}
		l.add(adjacent);
	}
	
	public boolean equals(Graph g) {
		if (this.getAdjacencyList().size() != g.getAdjacencyList().size()) {
			return false;
		}
		
		for (String key : this.getAdjacencyList().keySet()) {
			List<Edge> edges = g.getAdjacencyList().get(key);
			if (edges == null || edges.size() != this.getAdjacencyList().get(key).size()) {
				return false;
			}
			
			for (int i = 0; i<edges.size(); i++) {
				if (!edges.get(i).equals(this.getAdjacencyList().get(key).get(i))) {
					return false;
				}
			}			
		}
				
		return true;
	}
	
}
