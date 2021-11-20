package delivery;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

import delivery.model.*;

public class XMLParserTest {
	
	
	@Test
	public void loadPlanTestOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
		//Arrange
		ArrayList<Segment> segments = new ArrayList<>();
		Map<String, Intersection> intersections = new HashMap<>();
		
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		
		intersections.put("208769457",i1);
		intersections.put("208769499",i2);
		intersections.put("55474978",i3);
		intersections.put("55475018",i4);
		intersections.put("26033277",i5);
		intersections.put("975886496",i6);
		
		//We need to be careful about writing a double like this
		segments.add(new Segment(i2, i1, 106.73056, "Rue Frédéric Passy"));
		segments.add(new Segment(i2, i3, 79.801414, "Rue Édouard Aynard"));
		segments.add(new Segment(i2, i4, 96.57731, "Rue Édouard Aynard"));
		segments.add(new Segment(i6, i5, 78.72686, "Rue Danton"));
		
		Plan p = new Plan();
		//Act
		
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanGoodDataTest.xml"),p);
		
		
		//Asset
		assertEquals(intersections.size(), p.getIntersections().size());
		assertEquals(segments.size(), p.getSegments().size());
		
		boolean intersectionsCorrect=true;
		boolean segmentsCorrect=true;
		
		for (String key : intersections.keySet()) {
			if (!p.getIntersections().get(key).equals(intersections.get(key))) {
				intersectionsCorrect=false;
			}	
		}
		
		if (segments.size()!=p.getSegments().size()) {
			segmentsCorrect=false;
		}
		for (int i=0; i<segments.size(); i++) {
			boolean found=false;
			for (int j=0; j<segments.size(); j++) {
				if (p.getSegments().get(i).equals(segments.get(j))) {
					found=true;
				}	
			}
			if (!found) {
				segmentsCorrect=false;
			}
		}
		assertTrue(intersectionsCorrect);
		assertTrue(segmentsCorrect);
		
		
	
	}
	
	@Test
	public void loadPlanTestIOExceptionOk() {
		Plan p = new Plan();
		assertThrows(IOException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/notAFile.xml"), p);
		});
	}
	
	@Test
	public void loadPlanTestXMLParserExceptionOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanWrongIntersectionTest.xml"), p);
		});
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanWrongSegmentTest.xml"), p);
		});
	}
	
	
	@Test
	public void loadRequestsTestOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
		//Arrange
		Plan p = new Plan();

		ArrayList<Segment> segments = new ArrayList<>();
		Map<String, Intersection> intersections = new HashMap<String, Intersection>();
		
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		
		intersections.put("208769457",i1);
		intersections.put("208769499",i2);
		intersections.put("55474978",i3);
		intersections.put("55475018",i4);
		intersections.put("26033277",i5);
		intersections.put("975886496",i6);
		
		//We need to be careful about writing a double like this
		segments.add(new Segment(i2, i1, 106.73056, "Rue Frédéric Passy"));
		segments.add(new Segment(i2, i3, 79.801414, "Rue Édouard Aynard"));
		segments.add(new Segment(i2, i4, 96.57731, "Rue Édouard Aynard"));
		segments.add(new Segment(i6, i5, 78.72686, "Rue Danton"));
		
		//Requests
		Depot d = new Depot(i1,LocalTime.of(8, 0, 0));	
		
		List<Request> requests = new ArrayList<>();
		requests.add(
				new Request(
						new CheckPoint(CheckPointType.PICKUP, i3, 540), 
						new CheckPoint(CheckPointType.DELIVERY, i2, 540)
				)
		);
		requests.add(
				new Request(
						new CheckPoint(CheckPointType.PICKUP, i6, 360), 
						new CheckPoint(CheckPointType.DELIVERY, i4, 0)
				)
		);
		
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
		XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestGoodDataTest.xml"), p);
		
		
		//Asset
				
		assertEquals(requests.size(), p.getRequests().size());
		assertTrue(d.equals(p.getDepot()));
		
		for (int i=0; i<p.getRequests().size(); i++) {
			assertTrue(requests.get(i).equals(p.getRequests().get(i)));
		}
	}
	
	@Test
	public void loadRequestsTestIOExceptionOk() {
		Plan p = new Plan();
		assertThrows(IOException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/notAFile.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsTestXMLParserExceptionOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
		Plan p = new Plan();
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsWrongCheckpointTest.xml"), p);
		});
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsWrongDepotTest.xml"), p);
		});
	}
}
