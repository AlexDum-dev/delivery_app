package delivery;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.CheckPoint;
import model.CheckPointType;
import model.Intersection;
import model.Path;
import model.Segment;
import model.Tour;

public class TourTest {
	
	@Test
	public void actualizeTourTestOk() {
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

		List<Intersection> l = new ArrayList<Intersection>();
		l.add(i1);
		l.add(i2);
		l.add(i3);
		l.add(i4);
		l.add(i5);
		
		CheckPoint depot = new CheckPoint(CheckPointType.DEPOT, i1, LocalTime.now());
		CheckPoint pickup1 = new CheckPoint(CheckPointType.PICKUP, i3,10);
		CheckPoint delivery1 = new CheckPoint(CheckPointType.DELIVERY, i5,20);
		CheckPoint pickup2 = new CheckPoint(CheckPointType.PICKUP, i2,10);
		CheckPoint delivery2 = new CheckPoint(CheckPointType.DELIVERY, i4,20);
		
		depot.setIndex(0);
		pickup1.setIndex(1);
		delivery1.setIndex(1);
		pickup2.setIndex(2);
		delivery2.setIndex(2);
		
			
		List<CheckPoint> listCheckPoint = new ArrayList<CheckPoint>();
		listCheckPoint.add(depot);
		listCheckPoint.add(pickup1);
		listCheckPoint.add(delivery1);
		listCheckPoint.add(pickup2);
		listCheckPoint.add(delivery2);
		
		
		List<ArrayList<Path>> listPath = new ArrayList<ArrayList<Path>>();
		
		Tour tour = new Tour();
		tour.getCheckPoints().add(depot);
		List<Segment> list = new ArrayList<Segment>();
		list.add(si13);
		Path pathI1toI3 = new Path(list);
		
		
		List<Segment> list35 = new ArrayList<Segment>();
		list35.add(si34);
		list35.add(si45);
		Path pathi3I5 = new Path(list35);
		
		List<Segment> list51 = new ArrayList<Segment>();
		list51.add(si51);
		Path pathI5toI1 = new Path(list51);
		
		tour.addPath(pathI1toI3, pickup1);
		tour.addPath(pathI1toI3, delivery1);
		tour.addPath(pathI1toI3, pickup2);
		tour.addPath(pathI1toI3, delivery2);
		tour.addPath(pathI1toI3, depot);
		
		tour.deleteCheckPoint(1, l);
		
		assertEquals(tour.getCheckPoints().get(0).getIndex(), 0);
		assertEquals(tour.getCheckPoints().get(1).getIndex(), 1);
		assertEquals(tour.getCheckPoints().get(2).getIndex(), 2);
		assertEquals(tour.getCheckPoints().get(3).getIndex(), 2);
		
		tour.deleteCheckPoint(1, l);
		
		assertEquals(tour.getCheckPoints().get(0).getIndex(), 0);
		assertEquals(tour.getCheckPoints().get(1).getIndex(), 2);
		assertEquals(tour.getCheckPoints().get(2).getIndex(), 2);
		
		
	}

}
