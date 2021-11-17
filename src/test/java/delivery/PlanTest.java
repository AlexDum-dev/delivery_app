package delivery;

import static org.junit.Assert.*;
import java.util.*;


import org.junit.Test;

import delivery.model.*;

public class PlanTest {

	@Test
	public void test() {
		//Arrange
		ArrayList<Segment> segments = new ArrayList<>();
		Map<String, Intersection> intersections = new HashMap();
		
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
		
		
		segments.add(new Segment(i2, i1, 106.73056, "Rue Frédéric Passy"));
		segments.add(new Segment(i2, i3, 79.801414, "Rue Édouard Aynard"));
		segments.add(new Segment(i2, i4, 96.57731, "Rue Édouard Aynard"));
		segments.add(new Segment(i6, i5, 78.72686, "Rue Danton"));
		
		//Act
		
		
		
		//Asset
		
	}
	
	
	
	
	public static junit.framework.Test suite() {
		return new junit.framework.JUnit4TestAdapter(PlanTest.class);
	}

}
