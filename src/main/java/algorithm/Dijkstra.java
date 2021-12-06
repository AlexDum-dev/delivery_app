package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.CheckPoint;
import model.Intersection;
import model.Path;
import model.Plan;
import model.Request;
import model.Segment;

/**
 * Class for Dijkstra algorithm
 * 
 * @author 4IF Group H4144
 * @version 1.0 1 Dec 2021
 */
public class Dijkstra {
	
	/**
	 * Compute all the path from all checkpoints that are in requests (plus dépot)
	 * 
	 * @param adjacencyList
	 * @param listRequest
	 * @param depot
	 * @return
	 */
	public static DijkstraResult computePaths(List<Intersection> adjacencyList, List<Request> listRequest,CheckPoint depot){
		
		List<CheckPoint> listCheckPoint = RequestToCheckPoint(listRequest,depot);
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();
		
		for(CheckPoint checkpoint : listCheckPoint) {
			List<Integer> nodePredecesor = dijkstra(adjacencyList, 
					checkpoint.getAddress());
			//construct path
			ArrayList<Path> pathsFromCheckPoint = new ArrayList<Path>();
			for(CheckPoint checkpoint2 : listCheckPoint) {
				if(checkpoint != checkpoint2) {
					Path path = createPath(adjacencyList,nodePredecesor,
							checkpoint.getAddress().getIndex(),
							checkpoint2.getAddress().getIndex());
					pathsFromCheckPoint.add(path);
				} else {
					//null dans la diag
					pathsFromCheckPoint.add(null);
				}
			}
			listPath.add(pathsFromCheckPoint);	
		}
		
		
		
		return new DijkstraResult(listPath, listCheckPoint);
		
	}
	
	private static List<CheckPoint> RequestToCheckPoint(List<Request> listRequest,CheckPoint depot) {
		List<CheckPoint> checkpoints = new ArrayList<CheckPoint>();
		checkpoints.add(depot);
		
		//Add all the pickup : 
		for(Request req : listRequest)
			checkpoints.add(req.getPickup());
		
		//Add all the delivery : 
		for(Request req : listRequest)
			checkpoints.add(req.getDelivery());
		
		return checkpoints;
	}
	/**
	 * Create the path between two intersection using Dijkstra's algorithm results
	 * @param adjacencyList
	 * @param nodePredecesor
	 * @param OriginIndex
	 * @param DestinationIndex
	 * @return
	 */
	public static Path createPath(List<Intersection> adjacencyList,List<Integer> nodePredecesor, int OriginIndex, int DestinationIndex) {
		List<Segment> segments = new ArrayList<Segment>();
		int currentIndex = DestinationIndex;
		int NextIndex = currentIndex;
		while(currentIndex != OriginIndex) {
			System.out.println("[createPath]"+currentIndex);
			NextIndex = nodePredecesor.get(currentIndex);
			Segment s = getSegmentFromList(adjacencyList,NextIndex,currentIndex);

			segments.add(s);
			currentIndex = NextIndex;
		}
		Collections.reverse(segments);
		Path p;
		if(segments.size() == 0) {
			//case where we have two checkpoint on the same intersection
			Segment s = new Segment(adjacencyList.get(OriginIndex), adjacencyList.get(DestinationIndex), 0.0, "same");
			segments.add(s);
		}
		p = new Path(segments);
		return p;
	}

	private static Segment getSegmentFromList(List<Intersection> adjacencyList, int originIndex, int destinationIndex) {
		List<Segment> listSegmentToNode = adjacencyList.get(originIndex).getSegments();
		for(Segment s : listSegmentToNode) {
			if(s.getDestination().getIndex() == destinationIndex) {
				return s;
			}
		}
		
		return null;
	}

	/**
	 * Implement the djikstra algorithm
	 * @param g
	 * @param idDeparture
	 * @return
	 */
	public static List<Integer> dijkstra(List<Intersection> adjacencyList, Intersection departure) {
		List<Double> distance = new ArrayList<Double>(); //distance between departure and actual node
		/*
		 * Color of the node :
		 * White : has not been visited
		 * Grey : Is being visited
		 * Black : has been visited
		 */
		List<Color> nodeColor = new ArrayList<Color>();  //couleur 
		
		List<Integer> nodePredecesor = new ArrayList<Integer>(); //predecessor of actual intersection in djikstra
		
		//Initialize loop :
		//Put 0 to distance to each node 
		// Put the color white to each node because they haven't been visited yet
		for(Intersection node : adjacencyList) {
			if(departure.equals(node)) { //Initialization of the departure
				distance.add(0.0);
				nodeColor.add(Color.GREY);
			} else {
				distance.add(Double.MAX_VALUE);
				nodeColor.add(Color.WHITE);
				
			}
			nodePredecesor.add(null);
		}
		
		while(existGreyNode(nodeColor)) {
			int indexActualNode = minimalDistanceGreyNode(distance, nodeColor);
			List<Segment> listSegmentFromActualNode = adjacencyList.get(indexActualNode).getSegments();
			for(Segment s : listSegmentFromActualNode) {
				if(nodeColor.get(s.getDestination().getIndex()) == Color.GREY || nodeColor.get(s.getDestination().getIndex()) == Color.WHITE) {
					relacher(s, nodeColor, distance, nodePredecesor);
					if(nodeColor.get(s.getDestination().getIndex()) == Color.WHITE) {
						nodeColor.set(s.getDestination().getIndex(), Color.GREY);
					}
				}
			}
			nodeColor.set(indexActualNode, Color.BLACK);
		}
		
		return nodePredecesor;
	}
	/**
	 * 
	 * @param e
	 * @param precedor
	 * @param distance
	 */
	private static void relacher(Segment s,  List<Color> nodeColor, List<Double> distance, List<Integer> nodePredecesor) {
		int actualNode =  s.getOrigin().getIndex(); //4
		int nextNode =  s.getDestination().getIndex();//0
		if(distance.get(nextNode) > distance.get(actualNode) + s.getLength()) {
			distance.set(nextNode, distance.get(actualNode) + s.getLength());
			nodePredecesor.set(nextNode, actualNode);
		}
	}
	
	/**
	 * Tell if there is a grey node is the map
	 * @param nodeColor
	 * @return boolean
	 */
	
	private static boolean existGreyNode(List<Color> nodeColor) {
		
		for(int i = 0; i<nodeColor.size(); i++)
			if(nodeColor.get(i) == Color.GREY) return true;
		
		return false;
	}
	
	/**
	 * Return the index of the node which has the minimal distance
	 * @param distance
	 * @return
	 */
	private static int minimalDistanceGreyNode(List<Double> distance, List<Color> nodeColor) {
		double min = Double.MAX_VALUE;
		int indexNodeMin = -1;
				
		for(int i = 0; i<distance.size(); i++) {
			if(min > distance.get(i) && nodeColor.get(i) == Color.GREY) {
				indexNodeMin = i;
				min = distance.get(i);
			}
		}
		
		
		return indexNodeMin;
		
	}
	
	

}
