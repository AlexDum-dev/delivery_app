package delivery;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.*;
import org.junit.Test;

import algorithm.tsp.DeliveryGraph;
import delivery.model.*;

public class DeliveryGraphTest {
	
	@Test
	public void canBeVisitedTestOk() {
		//Arrange
		Intersection i1 = new Intersection("208769457", 45.760174, 4.877455);
		Intersection i2 = new Intersection("208769499", 45.760597, 4.87622);
		Intersection i3 = new Intersection("55474978", 45.761276, 4.876554);
		Intersection i4 = new Intersection("55475018", 45.75978, 4.875795);
		Intersection i5 = new Intersection("26033277", 45.756165, 4.8574095);
		Intersection i6 = new Intersection("975886496", 45.756874, 4.8574047);
		
		LocalTime time = LocalTime.of(16, 0, 0);		
		
		CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, time);
		CheckPoint p1 = new CheckPoint(CheckPointType.PICKUP, i2, 0);
		p1.setIndex(1);
		CheckPoint p2 = new CheckPoint(CheckPointType.PICKUP, i3, 0);
		p2.setIndex(2);
		CheckPoint d1 = new CheckPoint(CheckPointType.DELIVERY, i4, 0);
		d1.setIndex(1);
		CheckPoint d2 = new CheckPoint(CheckPointType.DELIVERY, i5, 0);
		d2.setIndex(2);
		
		List<CheckPoint> listCheckPoint = new ArrayList<>();
		listCheckPoint.add(depot);
		listCheckPoint.add(p1);
		listCheckPoint.add(p2);
		listCheckPoint.add(d1);
		listCheckPoint.add(d2);
		
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();
		
		//Act
		
		DeliveryGraph g = new DeliveryGraph(listPath, listCheckPoint);
		Collection<Integer> unvisited = new ArrayList<Integer>();
		unvisited.add(1);
		unvisited.add(2);
		unvisited.add(3);
		unvisited.add(4);
		
		//Asset
		
		assertTrue(g.canBeVisited(1, unvisited));
		assertTrue(g.canBeVisited(2, unvisited));
		assertTrue(!g.canBeVisited(3, unvisited));
		assertTrue(!g.canBeVisited(4, unvisited));
	}
}