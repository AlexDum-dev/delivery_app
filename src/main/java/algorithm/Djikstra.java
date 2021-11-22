package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delivery.model.CheckPoint;
import delivery.model.Intersection;
import delivery.model.Path;
import delivery.model.Request;
import delivery.model.Segment;

public class Djikstra {
	
	public static List<ArrayList<Path>> computePaths(List<Intersection> adjacencyList, List<Request> listRequest,CheckPoint depot){
		
		List<CheckPoint> listCheckPoint = RequestToCheckPoint(listRequest,depot);
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();
		
		for(CheckPoint checkpoint : listCheckPoint) {
			List<Integer> nodePredecesor = djikstra(adjacencyList, checkpoint.getAddress());
			//construct path
			ArrayList<Path> listPathConnectedtoCheckpointNode = new ArrayList<Path>();
			for(CheckPoint checkpoint2 : listCheckPoint) {
				if(checkpoint != checkpoint2) {
					Path path = createPath(adjacencyList,nodePredecesor,checkpoint.getAddress().getIndex(),checkpoint2.getAddress().getIndex());
					listPathConnectedtoCheckpointNode.add(path);
				} else {
					//null dans la diag
					listPathConnectedtoCheckpointNode.add(null);
				}
			}
			listPath.add(listPathConnectedtoCheckpointNode);	
		}
		
		
		
		return listPath;
		
	}
	
	private static List<CheckPoint> RequestToCheckPoint(List<Request> listRequest,CheckPoint depot) {
		// TODO Auto-generated method stub
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

	private static Path createPath(List<Intersection> adjacencyList,List<Integer> nodePredecesor, int OriginIndex, int DestinationIndex) {
		// TODO Auto-generated method stub
		//System.out.println("OriginIndex = "+OriginIndex);
		//System.out.println("DestinationIndex = "+DestinationIndex);
		System.out.println("==================================  createPath ===========================================");
		System.out.println("=OriginIndex="+OriginIndex);
		System.out.println("=DestinationIndex="+DestinationIndex);
		List<Segment> segments = new ArrayList<Segment>();
		int currentIndex = DestinationIndex;
		int NextIndex = currentIndex;
		while(currentIndex != OriginIndex) {
			System.out.println("======== WHILE =========");
			//System.out.println("NextIndex : "+NextIndex);
			//System.out.println("currentIndex : "+currentIndex);
			NextIndex = nodePredecesor.get(currentIndex);
			//System.out.println("2/NextIndex : "+NextIndex);
			Segment s = getSegmentFromList(adjacencyList,NextIndex,currentIndex);//we need to get the segment from the list of segments
			System.out.println("=currentIndex="+currentIndex);
			System.out.println("=NextIndex="+NextIndex);
			System.out.println("=getOrigin="+s.getOrigin().getIndex());
			System.out.println("=getDestination="+s.getDestination().getIndex());
			
			segments.add(s);
			currentIndex = NextIndex;
		}
		//System.out.println("segments.size() = "+segments.size() );
		for (Segment s : segments) {
			System.out.println(s.getOrigin().getIndex());
			System.out.println(s.getDestination().getIndex());
		}
		Collections.reverse(segments);
		Path p = new Path(segments);
		System.out.println("==================================  FIN : createPath ===========================================");
		return p;
	}

	private static Segment getSegmentFromList(List<Intersection> adjacencyList, int originIndex, int destinationIndex) {
		// TODO Auto-generated method stub		
		List<Segment> listSegmentToNode = adjacencyList.get(originIndex).getSegments();
		//System.out.println("[getSegmentFromList] size "+listSegmentToNode.size());
		for(Segment s : listSegmentToNode) {
			//System.out.println("[getSegmentFromList] "+s.getDestination().getIndex());
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
	public static List<Integer> djikstra(List<Intersection> adjacensyList, Intersection departure) {
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
		for(Intersection node : adjacensyList) {
			if(departure.equals(node)) { //Initialization of the departure
				distance.add(0.0);
				nodeColor.add(Color.GREY);
			} else {
				distance.add(Double.MAX_VALUE);
				nodeColor.add(Color.WHITE);
				
			}
			nodePredecesor.add(null);
			
			
		}
		
		//System.out.println("Couleur du 5 "+nodeColor.get("5")+ "and distance "+);
		
		while(existGreyNode(nodeColor)) {
			int indexActualNode = minimalDistanceGreyNode(distance, nodeColor);
			System.out.println("Actual Node : "+indexActualNode);
			List<Segment> listSegmentFromActualNode = adjacensyList.get(indexActualNode).getSegments();
			for(Segment s : listSegmentFromActualNode) {
				System.out.println("Voisin : "+s.getDestination().getIndex()+" et couleur : "+nodeColor.get(s.getDestination().getIndex()));
				if(nodeColor.get(s.getDestination().getIndex()) == Color.GREY || nodeColor.get(s.getDestination().getIndex()) == Color.WHITE) {
					relacher(s, nodeColor, distance, nodePredecesor);
					if(nodeColor.get(s.getDestination().getIndex()) == Color.WHITE) {
						nodeColor.set(s.getDestination().getIndex(), Color.GREY);
					}
					//System.out.println("id noeud actuel : "+e.getOrigin().getId()+ " noeud voisin : "+e.getDestination().getId());
					//System.out.println("Couleur noeud actuelle : "+nodeColor.get(e.getOrigin().getId())+ " noeud voisin : "+nodeColor.get(e.getDestination().getId()));
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
	public static void relacher(Segment s,  List<Color> nodeColor, List<Double> distance, List<Integer> nodePredecesor) {
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
	
	public static boolean existGreyNode(List<Color> nodeColor) {
		
		for(int i = 0; i<nodeColor.size(); i++)
			if(nodeColor.get(i) == Color.GREY) return true;
		
		return false;
	}
	
	/**
	 * Return the index of the node which has the minimal distance
	 * @param distance
	 * @return
	 */
	public static int minimalDistanceGreyNode(List<Double> distance, List<Color> nodeColor) {
		double min = Double.MAX_VALUE;
		int indexNodeMin = -1;
				
		for(int i = 0; i<distance.size(); i++) {
			if(min > distance.get(i) && nodeColor.get(i) == Color.GREY) indexNodeMin = i;
		}
		
		
		return indexNodeMin;
		
	}
	
	

}
