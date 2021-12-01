package delivery;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.*;
import org.junit.Test;

import algorithm.Dijkstra;
import algorithm.DijkstraResult;
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
		
		//Assert
		
		assertTrue(g.canBeVisited(1, unvisited));
		assertTrue(g.canBeVisited(2, unvisited));
		assertTrue(!g.canBeVisited(3, unvisited));
		assertTrue(!g.canBeVisited(4, unvisited));
	}
	
	@Test
	public void isArcTest () {
		Intersection i1 = new Intersection("1", 1.1, 1.2);
		i1.setIndex(0);
		Intersection i2 = new Intersection("2", 2.1, 2.2);
		i2.setIndex(1);
		Segment si12 = new Segment(i1, i2, 10.0, "first");
		i1.addSegment(si12);
		
		Intersection i3 = new Intersection("3", 2.1, 2.2);
		i3.setIndex(2);
		Segment si13 = new Segment(i1, i3, 2.0, "first");
		i1.addSegment(si13);
		
		Intersection i4 = new Intersection("4", 2.1, 2.2);
		i4.setIndex(3);
		Segment si34 = new Segment(i3, i4, 2.0, "first");
		i3.addSegment(si34);
		
		Segment si24 = new Segment(i2, i4, 3.0, "first");
		i2.addSegment(si24);
		
		Intersection i5 = new Intersection("5", 2.1, 2.2);
		i5.setIndex(4);
		Segment si45 = new Segment(i4, i5, 3.0, "first");
		i4.addSegment(si45);
		
		Segment si51 = new Segment(i5, i1, 5.0, "first");
		i5.addSegment(si51);

		
		CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, LocalTime.now());
		CheckPoint pickup = new CheckPoint(CheckPointType.PICKUP, i3,10);
		CheckPoint delivery = new CheckPoint(CheckPointType.DELIVERY, i5,20);
		
		
		
		List<CheckPoint> listCheckPoint = new ArrayList<CheckPoint>();
		listCheckPoint.add(depot);
		listCheckPoint.add(pickup);
		listCheckPoint.add(delivery);
		
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();
		
		ArrayList <Path> paths0 = new ArrayList<Path>();
		
		List<Segment> segments01 =new ArrayList<Segment>();
		segments01.add(si13);
		
		Path path01= new Path (segments01);
		
		List<Segment> segments02 =new ArrayList<Segment>();
		segments02.add(si13);
		segments02.add(si34);
		segments02.add(si45);
		
		Path path02= new Path (segments02);
		
		paths0.add(null);
		paths0.add(path01);
		paths0.add(path02);
		
		ArrayList <Path> paths1 = new ArrayList<Path>();
		
		List<Segment> segments10 =new ArrayList<Segment>();
		segments10.add(si34);
		segments10.add(si45);
		segments10.add(si51);
		
		Path path10= new Path (segments10);
		
		List<Segment> segments12 =new ArrayList<Segment>();
		segments12.add(si34);
		segments12.add(si45);
		
		Path path12= new Path (segments12);
		
		paths1.add(path10);
		paths1.add(null);
		paths1.add(path12);
		
		ArrayList <Path> paths2 = new ArrayList<Path>();
		
		List<Segment> segments20 =new ArrayList<Segment>();
		segments20.add(si51);
		
		Path path20= new Path (segments20);
		
		List<Segment> segments21 =new ArrayList<Segment>();
		segments21.add(si51);
		segments21.add(si13);
		
		Path path21= new Path (segments21);
		
		paths2.add(path20);
		paths2.add(path21);
		paths2.add(null);
		
		listPath.add(paths0);
		listPath.add(paths1);
		listPath.add(paths2);
		
		DeliveryGraph g = new DeliveryGraph(listPath, listCheckPoint);
		
		assertTrue (g.isArc(0, 1));
		assertTrue (g.isArc(0, 2));
		assertTrue (g.isArc(1, 0));
		assertTrue (g.isArc(1, 2));
		assertTrue (g.isArc(2, 0));
		assertTrue (g.isArc(2, 1));
	}
	
	@Test
	public void getCostTest() {
		
		Intersection i1 = new Intersection("1", 1.1, 1.2);
		i1.setIndex(0);
		Intersection i2 = new Intersection("2", 2.1, 2.2);
		i2.setIndex(1);
		Segment si12 = new Segment(i1, i2, 10.0, "first");
		i1.addSegment(si12);
		
		Intersection i3 = new Intersection("3", 2.1, 2.2);
		i3.setIndex(2);
		Segment si13 = new Segment(i1, i3, 2.0, "first");
		i1.addSegment(si13);
		
		Intersection i4 = new Intersection("4", 2.1, 2.2);
		i4.setIndex(3);
		Segment si34 = new Segment(i3, i4, 2.0, "first");
		i3.addSegment(si34);
		
		Segment si24 = new Segment(i2, i4, 3.0, "first");
		i2.addSegment(si24);
		
		Intersection i5 = new Intersection("5", 2.1, 2.2);
		i5.setIndex(4);
		Segment si45 = new Segment(i4, i5, 3.0, "first");
		i4.addSegment(si45);
		
		Segment si51 = new Segment(i5, i1, 5.0, "first");
		i5.addSegment(si51);

		
		CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, LocalTime.now());
		CheckPoint pickup = new CheckPoint(CheckPointType.PICKUP, i3,10);
		CheckPoint delivery = new CheckPoint(CheckPointType.DELIVERY, i5,20);
		
		
		
		List<CheckPoint> listCheckPoint = new ArrayList<CheckPoint>();
		listCheckPoint.add(depot);
		listCheckPoint.add(pickup);
		listCheckPoint.add(delivery);
		
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();
		
		ArrayList <Path> paths0 = new ArrayList<Path>();
		
		List<Segment> segments01 =new ArrayList<Segment>();
		segments01.add(si13);
		
		Path path01= new Path (segments01);
		
		List<Segment> segments02 =new ArrayList<Segment>();
		segments02.add(si13);
		segments02.add(si34);
		segments02.add(si45);
		
		Path path02= new Path (segments02);
		
		paths0.add(null);
		paths0.add(path01);
		paths0.add(path02);
		
		ArrayList <Path> paths1 = new ArrayList<Path>();
		
		List<Segment> segments10 =new ArrayList<Segment>();
		segments10.add(si34);
		segments10.add(si45);
		segments10.add(si51);
		
		Path path10= new Path (segments10);
		
		List<Segment> segments12 =new ArrayList<Segment>();
		segments12.add(si34);
		segments12.add(si45);
		
		Path path12= new Path (segments12);
		
		paths1.add(path10);
		paths1.add(null);
		paths1.add(path12);
		
		ArrayList <Path> paths2 = new ArrayList<Path>();
		
		List<Segment> segments20 =new ArrayList<Segment>();
		segments20.add(si51);
		
		Path path20= new Path (segments20);
		
		List<Segment> segments21 =new ArrayList<Segment>();
		segments21.add(si51);
		segments21.add(si13);
		
		Path path21= new Path (segments21);
		
		paths2.add(path20);
		paths2.add(path21);
		paths2.add(null);
		
		listPath.add(paths0);
		listPath.add(paths1);
		listPath.add(paths2);
		
		DeliveryGraph g = new DeliveryGraph(listPath, listCheckPoint);
		
		assertEquals (g.getCost(0, 0) , -1.0 , 0);
		assertEquals (g.getCost(0, 1) , 2.0 , 0);
		assertEquals (g.getCost(0, 2) , 7.0 , 0);
		assertEquals (g.getCost(1, 0) , 10.0, 0);
		assertEquals (g.getCost(1, 1) , -1.0 , 0);
		assertEquals (g.getCost(1, 2) , 5.0 , 0);
		assertEquals (g.getCost(2, 0) , 5.0 , 0);
		assertEquals (g.getCost(2, 1) , 7.0 , 0);
		assertEquals (g.getCost(2, 2) , -1.0 , 0);
	}
}