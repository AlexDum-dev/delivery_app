package algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delivery.model.Edge;
import delivery.model.Graph;

public class Djikstra {
	
	/**
	 * Implement the djikstra algorithm
	 * @param g
	 * @param idDeparture
	 * @return
	 */
	public static Map<String,String> djikstra(Graph g, String idDeparture) {
		Map<String,Double> distance = new HashMap<String,Double>();
		Map<String,Color> nodeColor = new HashMap<String,Color>();
		Map<String,String> nodePredecesor = new HashMap<String, String>();
		//Initialize loop :
		//Put 0 to distance to each node 
		// Put the color white to each node because they haven't been visited yet
		Set<String> allNodes = g.getAdjacencyList().keySet();
		for(String node : allNodes) {
			distance.put(node,  Double.MAX_VALUE);
			nodeColor.put(node, Color.WHITE);
			nodePredecesor.put(node,  null);
		}
		
		//System.out.println("Coleur du 5 "+nodeColor.get("5")+ "and distance "+);
		
		distance.put(idDeparture, 0.0);
		nodeColor.put(idDeparture, Color.GREY);
		while(existGreyNode(nodeColor)) {
			String idActualNode = minimalDistanceGreyNode(distance, nodeColor);
			System.out.println("Actual Node : "+idActualNode);
			List<Edge> edgesConnectedtoActualNode = g.getAdjacencyList().get(idActualNode);
			for(Edge e : edgesConnectedtoActualNode) {
				System.out.println("Voisin : "+e.getDestination().getId()+" et couleur : "+nodeColor.get(e.getDestination().getId()));
				if(nodeColor.get(e.getDestination().getId()) == Color.GREY || nodeColor.get(e.getDestination().getId()) == Color.WHITE) {
					relacher(e, nodeColor, distance, nodePredecesor);
					if(nodeColor.get(e.getDestination().getId()) == Color.WHITE) {
						nodeColor.put(e.getDestination().getId(), Color.GREY);
					}
					//System.out.println("id noeud actuel : "+e.getOrigin().getId()+ " noeud voisin : "+e.getDestination().getId());
					//System.out.println("Couleur noeud actuelle : "+nodeColor.get(e.getOrigin().getId())+ " noeud voisin : "+nodeColor.get(e.getDestination().getId()));
				}
			}
			nodeColor.put(idActualNode, Color.BLACK);
		}
		
		return nodePredecesor;
	}
	/**
	 * 
	 * @param e
	 * @param precedor
	 * @param distance
	 */
	public static void relacher(Edge e,  Map<String,Color> nodeColor, Map<String,Double> distance, Map<String,String> nodePredecesor) {
		String actualNode =  e.getOrigin().getId();
		String nextNode =  e.getDestination().getId();
		if(distance.get(nextNode) > distance.get(actualNode) + e.getPath().get(0).getLength()) {
			distance.put(nextNode, distance.get(actualNode) + e.getPath().get(0).getLength());
			nodePredecesor.put(nextNode, actualNode);
		}
	}
	
	/**
	 * Tell if there is a grey node is the map
	 * @param nodeColor
	 * @return boolean
	 */
	
	public static boolean existGreyNode(Map<String,Color> nodeColor) {
		
		Iterator<Map.Entry<String, Color>> itr = nodeColor.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry<String, Color> entry = itr.next();
			if(entry.getValue() == Color.GREY) return true;
		}
		
		return false;
	}
	/**
	 * Return the id of the node which has the minimal distance
	 * @param distance
	 * @return
	 */
	public static String minimalDistanceGreyNode(Map<String,Double> distance, Map<String,Color> nodeColor) {
		double min = Double.MAX_VALUE;
		String nodeMin = "";
		Iterator<Map.Entry<String, Double>> itr = distance.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry<String, Double> entry = itr.next();
			if(min > entry.getValue() && nodeColor.get(entry.getKey()) == Color.GREY) nodeMin = entry.getKey();
		}
		
		return nodeMin;
		
	}

}