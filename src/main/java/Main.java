import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import delivery.model.Plan;
import delivery.model.XMLParser;
import delivery.model.XMLParserException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Plan p = new Plan();
		try {
			XMLParser.loadPlan(new File("src/main/resources/smallMap.xml"), p);
		} catch (NumberFormatException | ParserConfigurationException | IOException | SAXException
				| XMLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.getIntersections().size());
		System.out.println(p.getSegments().size());
		System.out.println(p.getRequests().size());
		try {
			XMLParser.loadRequests(new File("src/main/resources/requestsSmall2.xml"), p);
		} catch (NumberFormatException | ParserConfigurationException | IOException | SAXException
				| XMLParserException | DateTimeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.getIntersections().size());
		System.out.println(p.getSegments().size());
		System.out.println(p.getRequests().size());
		System.out.println(p.getDepot().getTime());
	}

}
