package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

public class InstrumentTest extends ActivityInstrumentationTestCase2 {
    public InstrumentTest() {
        super(Instrument.class);
    }

    public void testGetStatus() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        assertEquals(instrument.getStatus(), "available");
        instrument.addBid(bid1);
        assertEquals(instrument.getStatus(), "bidded");
        instrument.acceptBid(0);
        assertEquals(instrument.getStatus(), "borrowed");
    }

    public void testGetDescription() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner, "Description");
        assertEquals(instrument.getDescription(), "Description");
    }

    public void testGetOwner() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner, "Description");
        User returnedOwner = instrument.getOwner();
        assertEquals(returnedOwner.getName(), "Owner");
        assertEquals(returnedOwner.getEmail(), "Owner email");
    }

    public void testGetBorrowedBy() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        instrument.addBid(bid1);
        instrument.acceptBid(0);
        User returnedBorrower = instrument.getBorrowedBy();
        assertEquals(returnedBorrower.getName(), "Bidder1");
        assertEquals(returnedBorrower.getEmail(), "Bidder1 email");
    }

    public void testGetBids() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 0);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 2);

        // Test first bid
        Bid returnedBid1 = returnedBids.get(0);
        User returnedOwner1 = returnedBid1.getOwner();
        User returnedBidder1 = returnedBid1.getBidder();
        Instrument returnedInstrument1 = returnedBid1.getInstrument();
        assertEquals(returnedOwner1.getName(), "Owner");
        assertEquals(returnedOwner1.getEmail(), "Owner email");
        assertEquals(returnedBidder1.getName(), "Bidder1");
        assertEquals(returnedBidder1.getEmail(), "Bidder1 email");
        assertEquals(returnedInstrument1.getDescription(), "Description");
        User returnedOwnerThroughInstrument1 = returnedInstrument1.getOwner();
        assertEquals(returnedOwnerThroughInstrument1.getName(), "Owner");
        assertEquals(returnedOwnerThroughInstrument1.getEmail(), "Owner email");

        // Test second bid
        Bid returnedBid2 = returnedBids.get(1);
        User returnedOwner2 = returnedBid2.getOwner();
        User returnedBidder2 = returnedBid2.getBidder();
        Instrument returnedInstrument2 = returnedBid2.getInstrument();
        assertEquals(returnedOwner2.getName(), "Owner");
        assertEquals(returnedOwner2.getEmail(), "Owner email");
        assertEquals(returnedBidder2.getName(), "Bidder2");
        assertEquals(returnedBidder2.getEmail(), "Bidder2 email");
        assertEquals(returnedInstrument2.getDescription(), "Description");
        User returnedOwnerThroughInstrument2 = returnedInstrument2.getOwner();
        assertEquals(returnedOwnerThroughInstrument2.getName(), "Owner");
        assertEquals(returnedOwnerThroughInstrument2.getEmail(), "Owner email");
    }

    public void testAcceptBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        instrument.acceptBid(0);
        assertEquals(instrument.getStatus(), "borrowed");
        User borrower = instrument.borrowedBy();
        assertEquals(borrower.getName(), "Bidder1");
        assertEquals(borrower.getEmail(), "Bidder1 email");
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        Bid returnedBid = returnedBids(0);
        User borrower = bid.getBidder();
        assertEquals(borrower.getName(), "Bidder1");
        assertEquals(borrower.getEmail(), "Bidder1 email");
        assertEquals(returnedBid.getBidAmount(), 2.00f);
    }

    public void testDeclineBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner, "Description");
        Bid bid1 = new Bid(instrument, owner, bidder1, 1.00f);
        Bid bid2 = new Bid(instrument, owner, bidder2, 2.00f);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        instrument.declineBid(0);
        ArrayList<Bid> returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        Bid returnedBid = returnedBids(0);
        User returnedBidder = returnedBid.getBidder();
        assertEquals(returnedBidder.getName(), "Bidder2");
        assertEquals(returnedBidder.getEmail(), "Bidder2 email");
        assertEquals(returnedBid.getBidAmount(), 2.00f);
    }
    
    // test use case US 04.01.01: SearchForInstruments
    public testSeachForInstruments(){
        // SearchForInstruments should return an ArrayList of instruments
        ArrayList<instrument> totalList;
        ArrayList<instrument> result;
        Instrument ins1 = new Instrument(“owner1”,“guitar,red,1999”);
        Instrument ins2 = new Instrument(“owner2”, “guitar,red,2000”);
        Instrument ins3 = new Instrument(“owner3”, “guitar,blue,2001”);
        Instrument ins4 = new Instrument(“owner4”, “guitar,red,2002”);
        Instrument ins5 = new Instrument(“owner5”, “guitar,yellow,2003”);
        
        totalList.add(ins1);
        totalList.add(ins2);
        totalList.add(ins3);
        totalList.add(ins4);
        totalList.add(ins5);
              	
      	result = SearchForInstruments(totalList,”guitar, red”);
      	assertEquals(result.size(),3);
      	assertTrue(result.contains(ins1));
      	assertTrue(result.contains(ins2));
      	assertTrue(result.contains(ins4));
 
      	result = SearchForInstruments(totalList,”piano”);
      	assertEquals(result.size(),0);
  	}
  	
  	// test use case US 05.01.01: BidOnInstrument
    public testBidOnInstrument(){
        // BidOnInstrument should return true if the bid is successful
 
        Instrument ins1 = new Instrument(“owner1”, “guitar,1”);
        Instrument ins2 = new Instrument(“owner2”, “guitar,2”);
        Instrument ins3 = new Instrument(“owner3”, “guitar,3”);
 
        ins1.setStatus(“available”);
        ins2.setStatus(“bidded”);
        ins3.setStatus(“borrowed”);
        assertTrue(BidOnInstrument(“borrower1”,ins1, 10.5));
        assertTrue(BidOnInstrument(“borrower1”,ins2,11));
        assertFalse(BidOnInstrument(“borrower1”,ins3,11.5));
    }

}
