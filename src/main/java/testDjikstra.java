import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import algorithm.Color;
import algorithm.Djikstra;
import delivery.model.Edge;
import delivery.model.Graph;
import delivery.model.Intersection;
import delivery.model.Segment;

public class testDjikstra {

	public static void main(String[] args) {
		Intersection i1 = new Intersection("1", 1.1, 1.2);
		Intersection i2 = new Intersection("2", 2.1, 2.2);
		Segment si12 = new Segment(i1, i2, 10.0, "first");
		List<Segment> seg = new ArrayList<Segment>();
		seg.add(si12);
		
		Intersection i3 = new Intersection("3", 2.1, 2.2);
		Segment si13 = new Segment(i1, i3, 2.0, "first");
		List<Segment> seg2 = new ArrayList<Segment>();
		seg2.add(si13);
		
		Intersection i4 = new Intersection("4", 2.1, 2.2);
		Segment si34 = new Segment(i3, i4, 2.0, "first");
		List<Segment> seg3 = new ArrayList<Segment>();
		seg3.add(si34);
		
		Segment si24 = new Segment(i2, i4, 3.0, "first");
		List<Segment> seg4 = new ArrayList<Segment>();
		seg4.add(si24);
		
		Intersection i5 = new Intersection("5", 2.1, 2.2);
		Segment si45 = new Segment(i4, i5, 3.0, "first");
		List<Segment> seg5 = new ArrayList<Segment>();
		seg5.add(si45);
		
		Segment si51 = new Segment(i5, i1, 5.0, "first");
		List<Segment> seg51 = new ArrayList<Segment>();
		seg51.add(si51);
		
		Edge e1 = new Edge(seg, i1, i2);
		Edge e1_3 = new Edge(seg2, i1, i3);
		Edge e2 = new Edge(seg4, i2, i4);
		Edge e3 = new Edge(seg3, i3, i4);
		Edge e4 = new Edge(seg5, i4, i5);
		Edge e5 = new Edge(seg51, i5, i1);
		
		List<Edge> connectedI1 = new ArrayList<Edge>();
		connectedI1.add(e1); connectedI1.add(e1_3);
		
		List<Edge> connectedI2 = new ArrayList<Edge>();
		connectedI2.add(e2);
		
		List<Edge> connectedI3 = new ArrayList<Edge>();
		connectedI3.add(e3);
		
		List<Edge> connectedI4 = new ArrayList<Edge>();
		connectedI4.add(e4);
		
		List<Edge> connectedI5 = new ArrayList<Edge>();
		connectedI4.add(e5);
		
		Map<String, List<Edge>> adjacencyList = new HashMap<String, List<Edge>>();
		adjacencyList.put("1", connectedI1);
		adjacencyList.put("2", connectedI2);
		adjacencyList.put("3", connectedI3);
		adjacencyList.put("4", connectedI4);
		adjacencyList.put("5", connectedI5);
		
		Iterator<Map.Entry<String, List<Edge>>> itr = adjacencyList.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry<String, List<Edge>> entry = itr.next();
			System.out.print(entry.getKey()+" : ");
			for(Edge e : entry.getValue()) {
				System.out.print(" "+e.getDestination().getId()+" ");
			}
			System.out.println();
		}
		
		Graph g = new Graph(adjacencyList);
		
		Map<String,String> predecesor = Djikstra.djikstra(g, "1");
		System.out.println(predecesor.get("4"));

	}

}
