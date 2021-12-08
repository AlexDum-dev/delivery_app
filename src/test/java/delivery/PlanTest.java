package delivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import model.CheckPoint;
import model.CheckPointType;
import model.Intersection;
import model.Plan;
import model.Request;
import model.Segment;
import model.XMLParser;
import model.XMLParserException;

public class PlanTest {
	
	
	@Test
	public void getMaxLatitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(45.761276,p.getMaxLatitude(),0);
	}
	
	@Test
	public void getMinLatitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(45.756165,p.getMinLatitude(),0);
	}
	
	@Test
	public void getMaxLongitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(4.877455,p.getMaxLongitude(),0);
	}
	
	@Test
	public void getMinLongitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(4.8574047,p.getMinLongitude(),0);
	}
	
	@Test
	public void deleteRequestByCheckPointTestOk(){
		
		Plan plan = new Plan();
		Intersection i1 = new Intersection("1", 1.1, 1.2);
		i1.setIndex(0);
		plan.addIntersection(i1);
		Intersection i2 = new Intersection("2", 2.1, 2.2);
		i2.setIndex(1);
		plan.addIntersection(i2);
		Segment si12 = new Segment(i1, i2, 10.0, "first");
		i1.addSegment(si12);
		
		Intersection i3 = new Intersection("3", 2.1, 2.2);
		i3.setIndex(2);
		plan.addIntersection(i3);
		Segment si13 = new Segment(i1, i3, 2.0, "first");
		i1.addSegment(si13);
		
		Intersection i4 = new Intersection("4", 2.1, 2.2);
		i4.setIndex(3);
		plan.addIntersection(i4);
		Segment si34 = new Segment(i3, i4, 2.0, "first");
		i3.addSegment(si34);
		
		Segment si24 = new Segment(i2, i4, 3.0, "first");
		i2.addSegment(si24);
		
		Intersection i5 = new Intersection("5", 2.1, 2.2);
		i5.setIndex(4);
		plan.addIntersection(i5);
		Segment si45 = new Segment(i4, i5, 3.0, "first");
		i4.addSegment(si45);
		
		Segment si51 = new Segment(i5, i1, 5.0, "first");
		i5.addSegment(si51);

		
		CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, LocalTime.now());
		plan.setDepot(depot);
		CheckPoint pickup1 = new CheckPoint(CheckPointType.PICKUP, i3,10);
		CheckPoint delivery1 = new CheckPoint(CheckPointType.DELIVERY, i5,20);
		
		CheckPoint pickup2 = new CheckPoint(CheckPointType.PICKUP, i2,10);
		CheckPoint delivery2 = new CheckPoint(CheckPointType.DELIVERY, i4,20);
		
		
		plan.addRequest(new Request(pickup1, delivery1));
		plan.addRequest(new Request(pickup2, delivery2));
	}
	
	@Test
	public void actualizeRequestsIndexTest () {
		
		Plan plan = new Plan();
		Intersection i1 = new Intersection("1", 1.1, 1.2);
		i1.setIndex(0);
		plan.addIntersection(i1);
		Intersection i2 = new Intersection("2", 2.1, 2.2);
		i2.setIndex(1);
		plan.addIntersection(i2);
		Segment si12 = new Segment(i1, i2, 10.0, "first");
		i1.addSegment(si12);
		
		Intersection i3 = new Intersection("3", 2.1, 2.2);
		i3.setIndex(2);
		plan.addIntersection(i3);
		Segment si13 = new Segment(i1, i3, 2.0, "first");
		i1.addSegment(si13);
		
		Intersection i4 = new Intersection("4", 2.1, 2.2);
		i4.setIndex(3);
		plan.addIntersection(i4);
		Segment si34 = new Segment(i3, i4, 2.0, "first");
		i3.addSegment(si34);
		
		Segment si24 = new Segment(i2, i4, 3.0, "first");
		i2.addSegment(si24);
		
		Intersection i5 = new Intersection("5", 2.1, 2.2);
		i5.setIndex(4);
		plan.addIntersection(i5);
		Segment si45 = new Segment(i4, i5, 3.0, "first");
		i4.addSegment(si45);
		
		Segment si51 = new Segment(i5, i1, 5.0, "first");
		i5.addSegment(si51);

		
		CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, LocalTime.now());
		plan.setDepot(depot);
		CheckPoint pickup1 = new CheckPoint(CheckPointType.PICKUP, i3,10);
		CheckPoint delivery1 = new CheckPoint(CheckPointType.DELIVERY, i5,20);
		
		CheckPoint pickup2 = new CheckPoint(CheckPointType.PICKUP, i2,10);
		CheckPoint delivery2 = new CheckPoint(CheckPointType.DELIVERY, i4,20);
		
		
		plan.addRequest(new Request(pickup1, delivery1));
		plan.addRequest(new Request(pickup2, delivery2));
		
		assertTrue(plan.getRequests().get(0).getPickup().getAddress().getId() == "3" && plan.getRequests().get(0).getDelivery().getAddress().getId() == "5" &&  plan.getRequests().get(0).getIndex() == 0);
		assertTrue(plan.getRequests().get(1).getPickup().getAddress().getId() == "2" && plan.getRequests().get(1).getDelivery().getAddress().getId() == "4" &&  plan.getRequests().get(1).getIndex() == 1);
	}
	
	@Test
	public void getNearestIntersectionTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		
		Plan p = new Plan();
		Intersection i1 = new Intersection("1", 0.1, 0.2);
		p.addIntersection(i1);
		Intersection i2 = new Intersection("2", -0.15, -0.2);
		p.addIntersection(i2);
		Intersection i3 = new Intersection("3", -0.1, 0.25);
		p.addIntersection(i3);
		Intersection i4 = new Intersection("4", 1.1, 1.2);
		p.addIntersection(i4);
		Intersection i5 = new Intersection("5", 1.1, 1.2);
		p.addIntersection(i5);
		Intersection i6 = new Intersection("6", 1.1, 1.2);
		p.addIntersection(i6);
		
		Intersection result = p.getNearestIntersection(0, 0);
		
		assertTrue(result.getId().equals("1"));
		
		
	}
	
	
}
