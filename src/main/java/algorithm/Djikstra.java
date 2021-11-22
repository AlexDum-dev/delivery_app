package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delivery.model.Edge;
import delivery.model.Graph;
import delivery.model.Intersection;
import delivery.model.Segment;

public class Djikstra {
	
	/**
	 * Implement the djikstra algorithm
	 * @param g
	 * @param idDeparture
	 * @return
	 */
	public static List<Intersection> djikstra(List<Intersection> adjacensyList, Intersection departure) {
		List<Double> distance = new ArrayList<Double>(); //distance between departure and actual node
		/*
		 * Color of the node :
		 * White : has not been visited
		 * Grey : Is being visited
		 * Black : has been visited
		 */
		List<Color> nodeColor = new ArrayList<Color>();  //couleur 
		
		List<Intersection> nodePredecesor = new ArrayList<Intersection>(); //predecessor of actual intersection in djikstra
		
		//Initialize loop :
		//Put 0 to distance to each node 
		// Put the color white to each node because they haven't been visited yet
		for(Intersection node : adjacensyList) {
			distance.add(Double.MAX_VALUE);
			nodeColor.add(Color.WHITE);
			nodePredecesor.add(null);
			
			if(departure.equals(node)) { //Initialization of the departure
				distance.add(0.0);
				nodeColor.add(Color.GREY);
			}
		}
		
		//System.out.println("Couleur du 5 "+nodeColor.get("5")+ "and distance "+);
		
		while(existGreyNode(nodeColor)) {
			int indexActualNode = minimalDistanceGreyNode(distance, nodeColor);
			System.out.println("Actual Node : "+indexActualNode);
			List<Segment> listSegmentFromActualNode = adjacensyList.get(indexActualNode).getListSegment();
			for(Segment s : listSegmentFromActualNode) {
				System.out.println("Voisin : "+e.getDestination().getId()+" et couleur : "+nodeColor.get(e.getDestination().getId()));
				if(nodeColor.get(s.getDestination().getIndex()) == Color.GREY || nodeColor.get(s.getDestination().getIndex()) == Color.WHITE) {
					relacher(s, nodeColor, distance, nodePredecesor);
					if(nodeColor.get(s.getDestination().getIndex()) == Color.WHITE) {
						nodeColor.set(s.getDestination().getIndex(), Color.GREY);
					}
					//System.out.println("id noeud actuel : "+e.getOrigin().getId()+ " noeud voisin : "+e.getDestination().getId());
					//System.out.println("Couleur noeud actuelle : "+nodeColor.get(e.getOrigin().getId())+ " noeud voisin : "+nodeColor.get(e.getDestination().getId()));
				}
			}
			nodeColor.set(idActualNode, Color.BLACK);
		}
		
		return nodePredecesor;
	}
	/**
	 * 
	 * @param e
	 * @param precedor
	 * @param distance
	 */
	public static void relacher(Segment s,  List<Color> nodeColor, List<Double> distance, List<int> nodePredecesor) {
		int actualNode =  s.getOrigin().getIndex();
		int nextNode =  s.getDestination().getIndex();
		if(distance.get(nextNode) > distance.get(actualNode) + s.getLength()) {
			distance.put(nextNode, distance.get(actualNode) + s.getLength());
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
