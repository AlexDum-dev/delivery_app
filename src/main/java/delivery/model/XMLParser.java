package delivery.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	public static Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
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
	 * @param p the plan to be loaded
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws NumberFormatException
	 */
	public static void loadPlan(File f, Plan p) throws ParserConfigurationException, 
	IOException, SAXException,
	NumberFormatException {
		Document xmlDocument = XMLParser.parse(f);
		p.clearPlan();
		loadIntersections(xmlDocument, p);
		loadSegments(xmlDocument, p);
	}

	/**
	 * Loads the list of all segments
	 * 
	 * @param xmlDocument xml document to load
	 * @param intersections the map of loaded intersections
	 * @return a list of segments
	 * @throws IOException
	 */
	private static void loadSegments(Document xmlDocument, 
			Plan p) throws IOException {
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

			Intersection interOrigin = p.getIntersection(origin);
			Intersection interDest = p.getIntersection(destination);
			if (interOrigin==null || interDest==null) {
				throw new IOException("XML file not correctly formatted: "+
						"Intersection not found.");
			}
			
			p.addSegment(new Segment(interOrigin, interDest, len, name));
		}
	}

	/**
	 * Loads the map of all intersections
	 * 
	 * @param xmlDocument the document to load
	 * @return the map of all intersections
	 * @throws IOException
	 */
	private static void loadIntersections(Document xmlDocument, Plan p)
			throws IOException {
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
			p.addIntersection(new Intersection(id, lat, lon));
		}
	}
}
