package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;;




public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */
	
	private TicketPaymentService tP;
	private SeatReservationService sP;
	
	//Calculating the total cost of Tickets
	private int ticketCalculation(int totalAdultTickets, int totalChildTickets) {
		return (totalAdultTickets*20)+(totalChildTickets*10);
	}
	
	
	
	
    @Override
    public int purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
    	int totalChildTickets=0,totalInfantTickets=0,totalAdultTickets=0,totalTickets = 0;
    	int totalAmount = 0;
    	tP = new TicketPaymentServiceImpl();
    	sP = new SeatReservationServiceImpl();
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
			tP.makePayment(accountId, totalAmount);  //Making Payment
			sP.reserveSeat(accountId, totalAdultTickets+totalChildTickets); //Booking Seats after Payment Confirmed
			System.out.println("Ticket Purchase Successfull");
		} catch (InvalidPurchaseException e) {
			System.out.println("Ticket Purchase Unsuccessfull,Please Try Again");
		}
		
		return totalAmount;
		
    }

}
