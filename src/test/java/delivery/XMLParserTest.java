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
		ArrayList<Intersection> intersections = new ArrayList<>();
		
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		
		intersections.add(i1);
		intersections.add(i2);
		intersections.add(i3);
		intersections.add(i4);
		intersections.add(i5);
		intersections.add(i6);
		
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanGoodDataTest.xml"),p);
		
		
		//Asset
		/*
		assertEquals(intersections.size(), p.getIntersections().size());
		
		boolean intersectionsCorrect=true;
		
		for (Intersection i : intersections) {
			if (!p.getIntersection(i.getId()).equals(i)) {
				intersectionsCorrect=false;
			}	
		}
		
		assertTrue(intersectionsCorrect);
		
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
		
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		
		
		CheckPoint d = new CheckPoint(CheckPointType.DEPOT,i1,LocalTime.of(8, 0, 0));	
		
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
		
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
		XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestGoodDataTest.xml"), p);
		
		
		//Asset		
		assertEquals(requests.size(), p.getRequests().size());
		assertTrue(d.equals(p.getDepot()));
		
		for (int i=0; i<p.getRequests().size(); i++) {
			assertTrue(requests.get(i).equals(p.getRequests().get(i)));
		}
		*/
	}
	
	@Test
	public void loadRequestsTestIOExceptionOk() {
		Plan p = new Plan();
		/*assertThrows(IOException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/notAFile.xml"), p);
		});*/
	}
	
	@Test
	public void loadRequestsTestXMLParserExceptionOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
		Plan p = new Plan();
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
		/*assertThrows(XMLParserException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsWrongCheckpointTest.xml"), p);
		});
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsWrongDepotTest.xml"), p);
		});*/
	}
}
