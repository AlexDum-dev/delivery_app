package delivery;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

import model.*;

public class XMLParserTest {
	
	//Testing XMLParser.loadPlan
	@Test
	public void loadPlanTestOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
		//Arrange
		ArrayList<Intersection> intersections = new ArrayList<>();
		
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		i1.setIndex(0);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		i2.setIndex(1);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		i3.setIndex(2);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		i4.setIndex(3);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		i5.setIndex(4);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		i6.setIndex(5);
		
		intersections.add(i1);
		intersections.add(i2);
		intersections.add(i3);
		intersections.add(i4);
		intersections.add(i5);
		intersections.add(i6);
		
		Segment s1 = new Segment(i2, i1, 106.73056, "Rue Frédéric Passy");
		Segment s2 = new Segment(i2, i3, 79.801414, "Rue Édouard Aynard");
		Segment s3 = new Segment(i2, i4, 96.57731, "Rue Édouard Aynard");
		Segment s4 = new Segment(i6, i5, 78.72686, "Rue Danton");
		
		i2.addSegment(s1);
		i2.addSegment(s2);
		i2.addSegment(s3);
		i6.addSegment(s4);		
		
		
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanGoodDataTest.xml"),p);
		
		
		//Asset
		assertEquals(intersections.size(), p.getIntersections().size());
		
		boolean intersectionsCorrect=true;
		
		for (Intersection i : intersections) {
			if (!p.getIntersection(i.getId()).equals(i)) {
				intersectionsCorrect=false;
				System.out.println("Index = " + p.getIntersection(i.getId()).getIndex());
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
	public void loadPlanMapTagMissingTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMapTagMissingTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMalformedFileTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMalformedFileTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMissingIntersectionIdTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingIntersectionIdTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanDuplicateIntersectionIdTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanDuplicateIntersectionIdTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanIntersectionNotExistingTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanIntersectionNotExistingTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMissingLatitudeTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingLatitudeTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanLatitudeNANTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLatitudeNANTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanLatitudeOver90TestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLatitudeOver90Test.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanLatitudeLessThanMinus90TestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLatitudeLessThanMinus90Test.xml"), p);
		});
	}
	
	@Test
	public void loadPlanLongitudeNANTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLongitudeNANTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMissingLongitudeTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingLongitudeTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanLongitudeOver90TestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLongitudeOver180Test.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanLongitudeLessThanMinus180TestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLongitudeLessThanMinus180Test.xml"), p);
		});
	}	
	
	@Test
	public void loadPlanMissingOriginTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingOriginTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMissingNameTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingNameTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMissingLengthTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingLengthTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanMissingDestinationTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanMissingDestinationTest.xml"), p);
		});		
	}
		
	@Test
	public void loadPlanNegativeLengthTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanNegativeLengthTest.xml"), p);
		});		
	}
	
	@Test
	public void loadPlanLengthNANTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadPlanLengthNANTest.xml"), p);
		});		
	}
	
	
	//Testing XMLParser.loadRequests
	
	@Test
	public void loadRequestsTestOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException, XMLParserException {
		//Arrange
		
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		i1.setIndex(0);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		i2.setIndex(1);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		i3.setIndex(2);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		i4.setIndex(3);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		i5.setIndex(4);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		i6.setIndex(5);
		
		
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
		
		requests.get(0).setIndex(0);
		requests.get(1).setIndex(1);
				
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
		
	}
	
	@Test
	public void loadRequestsTestIOExceptionOk() {
		Plan p = new Plan();
		assertThrows(IOException.class, () -> {
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/notAFile.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsTestXMLParserExceptionOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsWrongCheckpointTest.xml"), p);
		});
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsWrongDepotTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingAddressTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingAddressTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingDepartureTimeTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingDepartureTimeTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingPickupAddressTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingPickupAddressTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingDeliveryAddressTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingDeliveryAddressTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingDeliveryDurationTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingDeliveryDurationTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingPickupDurationTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingPickupDurationTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsMissingDepotTagTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsMissingDepotTagTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsIncorrectDepartureTimeTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsIncorrectDepartureTimeTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsPickupNotExistingTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsPickupNotExistingTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsDeliveryNotExistingTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsDeliveryNotExistingTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsDeliveryDurationNANTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsDeliveryDurationNANTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsNegativeDeliveryDurationTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsNegativeDeliveryDurationTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsPickupDurationNANTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsPickupDurationNANTest.xml"), p);
		});
	}
	
	@Test
	public void loadRequestsNegativePickupDurationTestNotOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("src/test/resources/XMLParserTest/loadRequestGoodDataPlanTest.xml"), p);
			XMLParser.loadRequests(new File("src/test/resources/XMLParserTest/loadRequestsNegativePickupDurationTest.xml"), p);
		});
	}
	
	
}
