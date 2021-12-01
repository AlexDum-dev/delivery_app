package delivery.model;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class to parse XML plan files
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class XMLParser {
	private XMLParser() {
		
	}
	
	private static Document parse(File file) 
			throws ParserConfigurationException, IOException, SAXException {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setIgnoringElementContentWhitespace(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(file);
	    // Do something with the document here.
	    return doc;
	}
	
	/**
	 * Loads the XML plan
	 * 
	 * @param f the XML file to load
	 * @param p the plan to feed
	 * @throws XMLParserException 
	 * @throws IOException
	 */
	public static void loadPlan(File f, Plan p) 
			throws XMLParserException, IOException {
		Document xmlDocument;
		try {
			xmlDocument = XMLParser.parse(f);
		} catch (ParserConfigurationException | SAXException e) {
			throw new XMLParserException("Not a valid XML file.");
		}
		p.clearPlan();
		NodeList root = xmlDocument.getChildNodes();
		if (root.getLength()!=1 
				|| !"map".equals(root.item(0).getNodeName())) {
			throw new XMLParserException("Invalid root node.");
		}
		loadIntersections(xmlDocument, p);
		loadSegments(xmlDocument, p);
		p.notifyObservers();
	}

	/**
	 * Loads the list of all segments
	 * 
	 * @param xmlDocument xml document to load
	 * @param p the plan to feed
	 * @throws XMLParserException 
	 */
	private static void loadSegments(Document xmlDocument, 
			Plan p) throws XMLParserException {
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
				throw new XMLParserException("XML file not correctly formatted: "+
						"Segment attribute not found.");
			}
			String origin = orgNode.getNodeValue();
			String destination = destNode.getNodeValue();
			String name = nameNode.getNodeValue();
			double len = -1;
			try {
				len = Double.parseDouble(lenNode.getNodeValue());
			} catch (NumberFormatException e) {
				len = -1;
			}
			if (len<0) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Invalid segment length.");
			}

			Intersection interOrigin = p.getIntersection(origin);
			Intersection interDest = p.getIntersection(destination);
			if (interOrigin==null || interDest==null) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Intersection not found.");
			}
			
			interOrigin.addSegment(new Segment(interOrigin, interDest, len, name));
		}
	}

	/**
	 * Loads the map of all intersections
	 * 
	 * @param xmlDocument the document to load
	 * @param p the plan to feed
	 * @throws XMLParserException 
	 */
	private static void loadIntersections(Document xmlDocument, Plan p)
			throws XMLParserException {
		NodeList inter = xmlDocument.getElementsByTagName("intersection");
		Set<String> inter_set = new TreeSet<String>();
		for (int i=0; i<inter.getLength(); ++i) {
			Node child = inter.item(i);
			NamedNodeMap attr = child.getAttributes();
			Node idNode = attr.getNamedItem("id");
			Node lonNode = attr.getNamedItem("longitude");
			Node latNode = attr.getNamedItem("latitude");
			if (idNode==null || latNode==null || lonNode==null) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Intersection attribute not found.");
			}
			String id = idNode.getNodeValue();
			double lat = 0;
			try {
				lat = Double.parseDouble(latNode.getNodeValue());
			} catch (NumberFormatException e) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Invalid Latitude.");
			}
			double lon = 0;
			try {
				lon = Double.parseDouble(lonNode.getNodeValue());
			} catch (NumberFormatException e) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Invalid Longitude.");
			}
			if (inter_set.contains(id)) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Duplicate Intersection.");
			}
			inter_set.add(id);
			p.addIntersection(new Intersection(id, lat, lon));
		}
		p.notifyObservers("doneWithIntersections");
	}
	
	/**
	 * Loads the XML requests
	 * 
	 * @param f the XML file to load
	 * @param p the plan to feed
	 * @throws XMLParserException 
	 * @throws IOException 
	 */
	public static void loadRequests(File f, Plan p) 
			throws XMLParserException, IOException{
		Document xmlDocument;
		try {
			xmlDocument = XMLParser.parse(f);
		} catch (ParserConfigurationException | SAXException e) {
			throw new XMLParserException("Not a valid XML file.");
		}
		p.clearRequests();
		NodeList root = xmlDocument.getChildNodes();
		if (root.getLength()!=1 
				|| !"planningRequest".equals(root.item(0).getNodeName())) {
			throw new XMLParserException("Invalid root node.");
		}
		loadDepot(xmlDocument, p);
		loadCheckPoints(xmlDocument, p);
		p.notifyObservers();
	}

	/**
	 * Loads the list of all requests
	 * 
	 * @param xmlDocument xml document to load
	 * @param p the plan to feed
	 * @throws XMLParserException
	 */
	private static void loadCheckPoints(Document xmlDocument, Plan p)
			throws XMLParserException {
		NodeList req = xmlDocument.getElementsByTagName("request");
		for (int i=0; i<req.getLength(); ++i) {
			Node child = req.item(i);
			NamedNodeMap attr = child.getAttributes();
			Node pkAddrNode = attr.getNamedItem("pickupAddress");
			Node dlAddrNode = attr.getNamedItem("deliveryAddress");
			Node pkDurNode = attr.getNamedItem("pickupDuration");
			Node dlDurNode = attr.getNamedItem("deliveryDuration");
			if (pkAddrNode==null || dlAddrNode==null
					|| pkDurNode==null || dlDurNode==null) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Request attribute not found.");
			}
			String pkAddr = pkAddrNode.getNodeValue();
			String dlAddr = dlAddrNode.getNodeValue();
			int pkDur = -1;
			try {
				pkDur = Integer.parseInt(pkDurNode.getNodeValue());
			} catch (NumberFormatException e) {
				pkDur = -1;
			}
			if (pkDur<0) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Invalid pickup duration.");
			}
			int dlDur = -1;
			try {
				dlDur = Integer.parseInt(dlDurNode.getNodeValue());
			} catch (NumberFormatException e) {
				dlDur = -1;
			}
			if (dlDur<0) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Invalid delivery duration.");
			}

			Intersection interPk = p.getIntersection(pkAddr);
			Intersection interDl = p.getIntersection(dlAddr);
			if (interPk==null || interDl==null) {
				throw new XMLParserException("XML file not correctly formatted: "+
						"Intersection not found.");
			}
			
			CheckPoint pickup = new CheckPoint(CheckPointType.PICKUP, interPk, 
					pkDur);
			CheckPoint delivery = new CheckPoint(CheckPointType.DELIVERY, 
					interDl, dlDur);
			p.addRequest(new Request(pickup, delivery));
		}
	}

	/**
	 * Loads the depot
	 * 
	 * @param xmlDocument xml document to load
	 * @param p the plan to feed
	 * @throws XMLParserException
	 */
	private static void loadDepot(Document xmlDocument, Plan p)
			throws XMLParserException{
		NodeList dep = xmlDocument.getElementsByTagName("depot");
		if (dep.getLength()!=1) {
			throw new XMLParserException("XML file not correctly formatted: "+
					"non-existent depot.");
		}
		Node child = dep.item(0);
		NamedNodeMap attr = child.getAttributes();
		Node addrNode = attr.getNamedItem("address");
		Node timeNode = attr.getNamedItem("departureTime");
		if (addrNode==null || timeNode==null) {
			throw new XMLParserException("XML file not correctly formatted: "+
					"Depot attribute not found.");
		}
		String addr = addrNode.getNodeValue();
		Intersection inter = p.getIntersection(addr);
		if (inter==null) {
			throw new XMLParserException("XML file not correctly formatted: "+
					"Intersection not found.");
		}
		String[] timestamp = timeNode.getNodeValue().split(":");
		if (timestamp.length!=3) {
			throw new XMLParserException("XML file not correctly formatted: "+
					"Invalid timestamp.");
		}

		try {
			int h = Integer.parseInt(timestamp[0]);
			int m = Integer.parseInt(timestamp[1]);
			int s = Integer.parseInt(timestamp[2]);
			LocalTime time = LocalTime.of(h, m, s);
			p.setDepot(new CheckPoint(CheckPointType.DEPOT, inter, time));
		} catch (NumberFormatException e) {
			throw new XMLParserException("XML file not correctly formatted: "+
					"Invalid Departure Time.");
		}
	}
}
