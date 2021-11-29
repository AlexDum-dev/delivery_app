package delivery;

import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import delivery.model.*;

import java.time.LocalTime;
import java.util.*;

import algorithm.*;

public class DijkstraTest {
	
	@Test 
	  public void djikstraTestWrongCostOk() {
		    Intersection i1 = new Intersection("1", 1.1, 1.2);
			i1.setIndex(0);
			Intersection i2 = new Intersection("2", 2.1, 2.2);
			i2.setIndex(1);
			Segment si12 = new Segment(i1, i2, -10.0, "first");
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

			List<Intersection> adjacencyList = new ArrayList<Intersection>();
			adjacencyList.add(i1);
			
			List<Integer> predecesor = Dijkstra.dijkstra(adjacencyList, i1);
			// TODO : GÉRER LE CAS LIMITE SUR DIJSKTRA
			System.out.println(predecesor.get(0));
			assertNull(predecesor.get(0));
	}
	  
	  @Test 
	  public void djikstraTestOk() {
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

			List<Intersection> adjacencyList = new ArrayList<Intersection>();
			adjacencyList.add(i1);
			adjacencyList.add(i2);
			adjacencyList.add(i3);
			adjacencyList.add(i4);
			adjacencyList.add(i5);
			
					
			List<Integer> predecesor = Dijkstra.dijkstra(adjacencyList, i1);
			assertNull(predecesor.get(0));
			Integer i = new Integer(0);
			assertEquals(i,predecesor.get(1));
			assertEquals(i,predecesor.get(2));
			i=2;
			assertEquals(i,predecesor.get(3));
			i=3;
			assertEquals(i,predecesor.get(4));
	  }	  
	  
	  @Test
	  public void computeTourTestOk () {
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

			List<Intersection> adjacencyList = new ArrayList<Intersection>();
			adjacencyList.add(i1);
			adjacencyList.add(i2);
			adjacencyList.add(i3);
			adjacencyList.add(i4);
			adjacencyList.add(i5);
			
			CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, LocalTime.now());
			CheckPoint pickup = new CheckPoint(CheckPointType.PICKUP, i3,10);
			CheckPoint delivery = new CheckPoint(CheckPointType.DELIVERY, i5,20);
			Request r1 = new Request(pickup, delivery);
			
			List<Request> listRequest = new ArrayList<Request>();
			listRequest.add(r1);
			
			DijkstraResult result = Dijkstra.computePaths(adjacencyList, listRequest, depot);
			
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
			
			DijkstraResult expectedResult = new DijkstraResult(listPath,listCheckPoint);
			
			assertEquals(expectedResult , result);
	  }
	  
}