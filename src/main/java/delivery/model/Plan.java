package delivery.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import delivery.utils.XMLParser;

public class Plan {
	private Map<String, Intersection> intersections;
	private List<Segment> segments;
	//Graph
	private List<Request> requests;
	
	public Plan() {
		intersections = new HashMap<String, Intersection>();
		segments = new ArrayList<Segment>();
		requests = new ArrayList<Request>();
	}
	
	/**
	 * Loads the XML plan
	 * 
	 * @param f the XML file to load
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws NumberFormatException
	 */
	public void loadPlan(File f) throws ParserConfigurationException, 
										 IOException, SAXException,
										 NumberFormatException {
		Document xmlDocument = XMLParser.parse(f);
		Map<String, Intersection> intersections = 
				loadIntersections(xmlDocument);
		List<Segment> segments = loadSegments(xmlDocument, intersections);
		this.intersections = intersections;
		this.segments = segments;
		this.requests = new ArrayList<Request>();
	}

	/**
	 * Loads the list of all segments
	 * 
	 * @param xmlDocument xml document to load
	 * @param intersections the map of loaded intersections
	 * @return a list of segments
	 * @throws IOException
	 */
	private List<Segment> loadSegments(Document xmlDocument, 
			Map<String, Intersection> intersections) throws IOException {
		List<Segment> segments = new ArrayList<Segment>();
		NodeList seg = xmlDocument.getElementsByTagName("segment");
		for (int i=0; i<seg.getLength(); ++i) {
			Node child = seg.item(i);
			NamedNodeMap attr = child.getAttributes();
			Node orgNode = attr.getNamedItem("origin");
			Node destNode = attr.getNamedItem("destination");
			Node nameNode = attr.getNamedItem("name");
			Node lenNode = attr.getNamedItem("length");
			if (orgNode==null || destNode==null || nameNode==null
					|| lenNode==null) {
				throw new IOException("XML file not correctly formatted: "+
						"Segment attribute not found.");
			}
			String origin = orgNode.getNodeValue();
			String destination = destNode.getNodeValue();
			String name = nameNode.getNodeValue();
			double len = Double.parseDouble(lenNode.getNodeValue());
			
			Intersection interOrigin = intersections.get(origin);
			Intersection interDest = intersections.get(destination);
			if (interOrigin==null || interDest==null) {
				throw new IOException("XML file not correctly formatted: "+
						"Intersection not found.");
			}
			
			segments.add(new Segment(interOrigin, interDest, len, name));
		}
		return segments;
	}
	
	/**
	 * Loads the map of all intersections
	 * 
	 * @param xmlDocument the document to load
	 * @return the map of all intersections
	 * @throws IOException
	 */
	private Map<String, Intersection> loadIntersections(Document xmlDocument) 
			throws IOException {
		Map<String, Intersection> intersections = 
				new HashMap<String, Intersection>();
		NodeList inter = xmlDocument.getElementsByTagName("intersection");
		for (int i=0; i<inter.getLength(); ++i) {
			Node child = inter.item(i);
			NamedNodeMap attr = child.getAttributes();
			Node idNode = attr.getNamedItem("id");
			Node lonNode = attr.getNamedItem("longitude");
			Node latNode = attr.getNamedItem("latitude");
			if (idNode==null || latNode==null || lonNode==null) {
				throw new IOException("XML file not correctly formatted: "+
										"Intersection attribute not found.");
			}
			String id = idNode.getNodeValue();
			double lat = Double.parseDouble(latNode.getNodeValue());
			double lon = Double.parseDouble(lonNode.getNodeValue());
			intersections.put(id, new Intersection(id, lat, lon));
		}
		return intersections;
	}

	public Map<String, Intersection> getIntersections() {
		return intersections;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public List<Request> getRequests() {
		return requests;
	}
}
