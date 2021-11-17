package delivery;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import delivery.model.*;

public class PlanTest {
	
	//this is going to be an integration test. We need to update it according to the last changes of XML Parser
	@Test
	public void loadPlanTestOk() throws NumberFormatException, ParserConfigurationException, IOException, SAXException {
		
		
	}
	
	
	
	
	public static junit.framework.Test suite() {
		return new junit.framework.JUnit4TestAdapter(PlanTest.class);
	}

}
