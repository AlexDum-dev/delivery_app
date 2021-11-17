package delivery;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
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
		Map<String, Intersection> intersections = new HashMap<String, Intersection>();
		
		Intersection i1 = new Intersection("1", 45.760174, 4.877455);
		Intersection i2 = new Intersection("2", 45.760597, 4.87622);
		Intersection i3 = new Intersection("3", 45.761276, 4.876554);
		Intersection i4 = new Intersection("4", 45.75978, 4.875795);
		Intersection i5 = new Intersection("5", 45.756165, 4.8574095);
		Intersection i6 = new Intersection("6", 45.756874, 4.8574047);
		
		intersections.put("1",i1);
		intersections.put("2",i2);
		intersections.put("3",i3);
		intersections.put("4",i4);
		intersections.put("5",i5);
		intersections.put("6",i6);
		
		//We need to be careful about writing a double like this
		segments.add(new Segment(i2, i1, 106.73056, "Rue Fr�d�ric Passy"));
		segments.add(new Segment(i2, i3, 79.801414, "Rue �douard Aynard"));
		segments.add(new Segment(i2, i4, 96.57731, "Rue �douard Aynard"));
		segments.add(new Segment(i6, i5, 78.72686, "Rue Danton"));
		
		Plan p = new Plan();
		//Act
		
		XMLParser.loadPlan(new File("loadPlanGoodDataTest"),p);
		
		
		//Asset
		assertEquals(6, p.getIntersections().size());
		assertEquals(4, p.getSegments().size());
		
		for (int i=0; i<p.getSegments().size(); i++) {
			assertTrue(segments.contains(p.getSegments().get(i)));				
		}

		for (int i=0; i<p.getIntersections().keySet().size(); i++) {
			Intersection inter = p.getIntersections().get(Integer.toString(i));
			assertTrue(intersections.containsValue(inter));		
		}
		
	}
	
	@Test
	public void loadPlanTestIOExceptionOk() {
		Plan p = new Plan();
		assertThrows(IOException.class, () -> {
			XMLParser.loadPlan(null, p);
		});
	}
	
	@Test
	public void loadPlanTestXMLParserExceptionOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("loadPlanWrongIntersectionTest"), p);
		});
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadPlan(new File("loadPlanWrongSegmentTest"), p);
		});
	}
	
	
	@Test
	public void loadRequestsTestOk() {
		
	}
	
	@Test
	public void loadRequestsTestIOExceptionOk() {
		Plan p = new Plan();
		assertThrows(IOException.class, () -> {
			XMLParser.loadRequests(null, p);
		});
	}
	
	@Test
	public void loadRequestsTestXMLParserExceptionOk() {
		Plan p = new Plan();
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadRequests(new File("loadRequestsWrongCheckpointTest"), p);
		});
		assertThrows(XMLParserException.class, () -> {
			XMLParser.loadRequests(new File("loadRequestsWrongDepotTest"), p);
		});
	}
	
	
	
	
	public static junit.framework.Test suite() {
		return new junit.framework.JUnit4TestAdapter(XMLParser.class);
	}
}
