package uk.gov.dwp.uc.pairtest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImplTest extends TicketServiceImpl {

	private TicketServiceImpl ticketService;		
	private TicketTypeRequest tP = new TicketTypeRequest(Type.ADULT,1);
	private TicketTypeRequest tP2 = new TicketTypeRequest(Type.CHILD,1);
	private TicketTypeRequest tP3 = new TicketTypeRequest(Type.INFANT,0);
	TicketTypeRequest[] ticketTypeRequests = {tP,tP2,tP3};
	Long accountId = Long.valueOf(4698239);
	
	

	@After
	public void tearDown() throws Exception {
		System.out.println("Test Ended");
		
	}
	
	public TicketServiceImplTest() {
		System.out.println("Start");
		ticketService = new TicketServiceImpl();	
	}
	
	@Test
	public void TestPurchaseTickets() throws InvalidPurchaseException{
		assertEquals(30, purchaseTickets(accountId, ticketTypeRequests));
		System.out.println();
	}
}
