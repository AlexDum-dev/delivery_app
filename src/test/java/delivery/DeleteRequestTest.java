package delivery;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import algorithm.Dijkstra;
import algorithm.DijkstraResult;
import controller.AddRequestCommand;
import model.CheckPoint;
import model.CheckPointType;
import model.Intersection;
import model.Path;
import model.Plan;
import model.Request;
import model.Segment;
import model.Tour;
import view.Window;

public class DeleteRequestTest {

	@Test
	public void deleteRequestTest() {
		
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
		CheckPoint pickup = new CheckPoint(CheckPointType.PICKUP, i3,10);
		CheckPoint delivery = new CheckPoint(CheckPointType.DELIVERY, i5,20);
		
		plan.addRequest(new Request(pickup, delivery));
		
		
		List<CheckPoint> listCheckPoint = new ArrayList<CheckPoint>();
		listCheckPoint.add(depot);
		listCheckPoint.add(pickup);
		listCheckPoint.add(delivery);
		
		Request r1 = new Request(pickup, delivery);		
		plan.addRequest(r1);
		
		
		
		Tour tour = new Tour();
		
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
		
		tour.addPath(pathI1toI3, depot);
		tour.addPath(pathi3I5, pickup);
		tour.addPath(pathI5toI1, delivery);
		
		AddRequestCommand addCommand = new AddRequestCommand(plan, tour,"3", "5", new Window());
		
		
		
		addCommand.undoCommand();
		
		System.out.println(tour.getPath().get(0).getPath() == null);
		
		assertTrue (tour.getPath().size()==1);
		assertTrue (tour.getPath().get(0).getPath() == null);
		
		
	}

}
