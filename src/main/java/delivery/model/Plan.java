package delivery.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import delivery.utils.XMLParser;

public class Plan {
	private Map<String, Intersection> intersections;
	private List<Segment> segments;
	//Graph
	private List<Request> requests;
	
	public Plan() {
		resetPlan();
	}
	
	/**
	 * Resets the plan by removing all intersections, segments and requests
	 */
	private void resetPlan() {
		intersections = new HashMap<String, Intersection>();
		segments = new ArrayList<Segment>();
		resetRequests();
	}
	
	/**
	 * Deletes all requests
	 */
	private void resetRequests() {
		requests = new ArrayList<Request>();
	}
	
	private void loadPlan(File f) throws ParserConfigurationException, 
										 IOException, SAXException {
		Document xmlDocument = XMLParser.parse(f);
		resetPlan();
		
	}
}
