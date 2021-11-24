package delivery;

import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import delivery.model.*;
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
}