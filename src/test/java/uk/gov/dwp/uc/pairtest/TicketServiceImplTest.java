package uk.gov.dwp.uc.pairtest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImplTest extends TicketServiceImpl {
	
	private TicketPaymentServiceImpl tPS;
	private SeatReservationServiceImpl sRS;
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
	}
	
	private int ticketCalculation(int totalAdultTickets, int totalChildTickets) {
		return (totalAdultTickets*20)+(totalChildTickets*10);
	}
	
	
    public int purchaseTicketsTest(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
    	int totalChildTickets=0,totalInfantTickets=0,totalAdultTickets=0,totalTickets = 0;
    	int totalAmount = 0;
    	tPS = new TicketPaymentServiceImpl();
    	sRS = new SeatReservationServiceImpl();
		try {
			for(TicketTypeRequest tc: ticketTypeRequests) {       //Iterating through the requests
				if(tc.getTicketType()== Type.ADULT)
					totalAdultTickets += tc.getNoOfTickets();
				if(tc.getTicketType()== Type.INFANT)
					totalInfantTickets += tc.getNoOfTickets();
				if(tc.getTicketType()== Type.CHILD)
					totalChildTickets += tc.getNoOfTickets();
			}
		    totalTickets = totalAdultTickets + totalChildTickets + totalInfantTickets;  //Calculating total no of tickets
			if(totalTickets >20 || totalInfantTickets > totalAdultTickets || totalAdultTickets == 0) {
				throw new InvalidPurchaseException();
			}
			totalAmount = ticketCalculation(totalAdultTickets,totalChildTickets);
			tPS.makePayment(accountId, totalAmount);  //Making Payment
			sRS.reserveSeat(accountId, totalAdultTickets+totalChildTickets); //Booking Seats after Payment Confirmed
			System.out.println("Ticket Purchase Successfull");
		} catch (InvalidPurchaseException e) {
			System.out.println("Ticket Purchase Unsuccessfull,Please Try Again");
		} catch (Exception e) {
			System.out.print("All other Exceptions");
		}
		
		return totalAmount;
		
    }
	
	@Test
	public void TestPurchaseTickets() throws InvalidPurchaseException{
		assertEquals(10, purchaseTicketsTest(accountId, ticketTypeRequests));
		System.out.println();
	}
}
