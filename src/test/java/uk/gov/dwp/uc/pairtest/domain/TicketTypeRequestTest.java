package uk.gov.dwp.uc.pairtest.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketTypeRequestTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	 private int noOfTickets;
	 private Type type;


	
	
	@Test
	public void testTicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
	}

	@Test
	public int testGetNoOfTickets() {
		return noOfTickets;
	}

	@Test
	public Type testGetTicketType() {
		return type;
	}
	
	 public enum Type {
	     ADULT, CHILD , INFANT
	 }	 

}
