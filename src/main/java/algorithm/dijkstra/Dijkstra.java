package algorithm.dijkstra;

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
	 * Compute all the path from all checkpoints that are in requests 
	 * (plus depot)
	 * 
	 * @param adjList adjacency list
	 * @param requests list of all requests
	 * @param depot
	 * @return A result containing all paths with checkpoints
	 */
	public static DijkstraResult computePaths(List<Intersection> adjList, 
			List<Request> requests,CheckPoint depot){

		List<CheckPoint> listCheckPoint = requestToCheckPoint(requests, 
				depot);
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();

		for(CheckPoint checkpoint : listCheckPoint) {
			List<Integer> nodePredecesor = dijkstra(adjList, 
					checkpoint.getAddress());
			//construct path
			ArrayList<Path> pathsFromCheckPoint = new ArrayList<Path>();
			for(CheckPoint checkpoint2 : listCheckPoint) {
				if(checkpoint != checkpoint2) {
					Path path = createPath(adjList,nodePredecesor,
							checkpoint.getAddress().getIndex(),
							checkpoint2.getAddress().getIndex());
					if(path == null) {
						return null;
					}
					pathsFromCheckPoint.add(path);
				} else {
					pathsFromCheckPoint.add(null);
				}
			}
			listPath.add(pathsFromCheckPoint);	
		}



		return new DijkstraResult(listPath, listCheckPoint);

	}

	/**
	 * Converts all the requests to a list of checkpoints
	 * @param requests list of all requests
	 * @param depot the depot
	 * @return a list containing all pickup and delivery checkpoints
	 */
	private static List<CheckPoint> requestToCheckPoint(List<Request> requests, 
			CheckPoint depot) {
		List<CheckPoint> checkpoints = new ArrayList<CheckPoint>();
		checkpoints.add(depot);

		//Add all the pickups: 
		for(Request req : requests)
			checkpoints.add(req.getPickup());

		//Add all the deliveries: 
		for(Request req : requests)
			checkpoints.add(req.getDelivery());

		return checkpoints;
	}
	/**
	 * Create the path between two intersections using 
	 * Dijkstra's algorithm results
	 * @param adjList adjacency list
	 * @param predList list of predecessors
	 * @param originIndex
	 * @param destinationIndex
	 * @return the path between origin and destination
	 */
	public static Path createPath(List<Intersection> adjList,
			List<Integer> predList, int originIndex, int destinationIndex) {
		if(predList.get(destinationIndex) == null
				&& originIndex!=destinationIndex) { 
			// case where we can't create the path
			// (node not connected to the rest of the graph)
			return null;
		}

		List<Segment> segments = new ArrayList<Segment>();
		int currentIndex = destinationIndex;
		int NextIndex = currentIndex;
		while(currentIndex != originIndex) {
			NextIndex = predList.get(currentIndex);
			Segment s = getSegmentFromList(adjList,NextIndex,currentIndex);

			segments.add(s);
			currentIndex = NextIndex;
		}
		Collections.reverse(segments);
		Path p;
		if(segments.size() == 0) {
			//case where we have two checkpoint on the same intersection
			Segment s = new Segment(adjList.get(originIndex), 
					adjList.get(destinationIndex), 0.0, "same");
			segments.add(s);
		}
		p = new Path(segments);
		return p;
	}

	/**
	 * 
	 * @param adjList adjacency list
	 * @param originIndex
	 * @param destinationIndex
	 * @return the segment for with that originIndex and destinationIndex
	 */
	private static Segment getSegmentFromList(List<Intersection> adjList, 
			int originIndex, int destinationIndex) {
		List<Segment> segForOrigin = adjList.get(originIndex).getSegments();
		for(Segment s : segForOrigin) {
			if(s.getDestination().getIndex() == destinationIndex) {
				return s;
			}
		}

		return null;
	}

	/**
	 * Runs Dijkstra
	 * 
	 * @param adjList adjacency list
	 * @param departure intersection of departure
	 * @return the predecessors list
	 */
	public static List<Integer> dijkstra(List<Intersection> adjList, 
			Intersection departure) {
		//distance between departure and actual node
		List<Double> distance = new ArrayList<Double>();

		List<Color> colors = new ArrayList<Color>();

		//predecessor of actual intersection in djikstra
		List<Integer> predList = new ArrayList<Integer>();

		//Initialize loop:
		// Put 0 to distance to each node 
		// Put the color white to each node because they haven't been visited
		for(Intersection node : adjList) {
			if(departure.equals(node)) { //Initialization of the departure
				distance.add(0.0);
				colors.add(Color.GREY);
			} else {
				distance.add(Double.MAX_VALUE);
				colors.add(Color.WHITE);

			}
			predList.add(null);
		}

		while(existGreyNode(colors)) {
			int indexActualNode = minimalDistanceGreyNode(distance, colors);
			List<Segment> listSegmentFromActualNode = 
					adjList.get(indexActualNode).getSegments();
			for(Segment s : listSegmentFromActualNode) {
				Intersection dest = s.getDestination();
				if(colors.get(dest.getIndex()) == Color.GREY
						|| colors.get(dest.getIndex()) == Color.WHITE) {
					release(s, colors, distance, predList);
					if(colors.get(dest.getIndex()) == Color.WHITE) {
						colors.set(dest.getIndex(), Color.GREY);
					}
				}
			}
			colors.set(indexActualNode, Color.BLACK);
		}

		return predList;
	}

	/**
	 * Releases a segment of the graph
	 * 
	 * @param s a segment of the graph
	 * @param colors node colors
	 * @param distance node distance
	 * @param predList predecessors list
	 */
	private static void release(Segment s,  List<Color> colors, 
			List<Double> distance, List<Integer> predList) {
		int actualNode =  s.getOrigin().getIndex(); //4
		int nextNode =  s.getDestination().getIndex();//0
		if(distance.get(nextNode) > distance.get(actualNode) + s.getLength()) {
			distance.set(nextNode, distance.get(actualNode) + s.getLength());
			predList.set(nextNode, actualNode);
		}
	}

	/**
	 * Tells if there is a grey node is the map
	 * 
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
	 * @return the minimal distance
	 */
	private static int minimalDistanceGreyNode(List<Double> distance, 
			List<Color> nodeColor) {
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
