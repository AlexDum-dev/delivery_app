package delivery.model;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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
	
	private static Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
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
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws NumberFormatException
	 * @throws XMLParserException 
	 */
	public static void loadPlan(File f, Plan p) throws ParserConfigurationException, 
	IOException, SAXException,
	NumberFormatException, XMLParserException {
		Document xmlDocument = XMLParser.parse(f);
		p.clearPlan();
		loadIntersections(xmlDocument, p);
		loadSegments(xmlDocument, p);
		p.notifyObservers();
	}

	/**
	 * Loads the list of all segments
	 * 
	 * @param xmlDocument xml document to load
	 * @param p the plan to feed
	 * @throws IOException
	 * @throws XMLParserException 
	 */
	private static void loadSegments(Document xmlDocument, 
			Plan p) throws IOException, XMLParserException {
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
			double len = Double.parseDouble(lenNode.getNodeValue());

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
	 * @throws IOException
	 * @throws XMLParserException 
	 */
	private static void loadIntersections(Document xmlDocument, Plan p)
			throws IOException, XMLParserException {
		NodeList inter = xmlDocument.getElementsByTagName("intersection");
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
			double lat = Double.parseDouble(latNode.getNodeValue());
			double lon = Double.parseDouble(lonNode.getNodeValue());
			p.addIntersection(new Intersection(id, lat, lon));
		}
	}
	
	/**
	 * Loads the XML requests
	 * 
	 * @param f the XML file to load
	 * @param p the plan to feed
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws NumberFormatException
	 * @throws XMLParserException 
	 */
	public static void loadRequests(File f, Plan p) throws ParserConfigurationException, 
	IOException, SAXException,
	NumberFormatException, XMLParserException,
	DateTimeParseException{
		Document xmlDocument = XMLParser.parse(f);
		p.clearRequests();
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
	 * @throws NumberFormatException
	 */
	private static void loadCheckPoints(Document xmlDocument, Plan p)
			throws XMLParserException, NumberFormatException {
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
			int pkDur = Integer.parseInt(pkDurNode.getNodeValue());
			int dlDur = Integer.parseInt(dlDurNode.getNodeValue());

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
	 * @throws NumberFormatException
	 */
	private static void loadDepot(Document xmlDocument, Plan p)
			throws XMLParserException, NumberFormatException{
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
		int h = Integer.parseInt(timestamp[0]);
		int m = Integer.parseInt(timestamp[1]);
		int s = Integer.parseInt(timestamp[2]);
		LocalTime time = LocalTime.of(h, m, s);
		p.setDepot(new CheckPoint(CheckPointType.DEPOT, inter, time));
	}
}
