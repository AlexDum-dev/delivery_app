package delivery;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import model.*;

public class PlanTest {
	
	
	@Test
	public void getMaxLatitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(45.761276,p.getMaxLatitude(),0);
	}
	
	@Test
	public void getMinLatitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(45.756165,p.getMinLatitude(),0);
	}
	
	@Test
	public void getMaxLongitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(4.877455,p.getMaxLongitude(),0);
	}
	
	@Test
	public void getMinLongitudeTestOk() throws ParserConfigurationException, IOException, SAXException, XMLParserException{
		//Arrange
		Plan p = new Plan();
		
		//Act
		XMLParser.loadPlan(new File("src/test/resources/PlanTest/planTestFile.xml"),p);
		
		//Asset
		assertEquals(4.8574047,p.getMinLongitude(),0);
	}
	
}
