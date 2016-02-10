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
}