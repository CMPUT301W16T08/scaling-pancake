package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(Bid.class);
    }

    public void testGetInstrument() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        Instrument returnedInstrument = bid.getInstrument();
        assertEquals(returnedInstrument.getOwner(), instrument.getOwner());
        assertEquals(returnedInstrument.getName(), instrument.getName());
        assertEquals(returnedInstrument.getDescription(), instrument.getDescription());
    }

    public void testGetOwner() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        User returnedOwner = bid.getOwner();
        assertEquals(returnedOwner.getName(), owner.getName());
        assertEquals(returnedOwner.getEmail(), owner.getEmail());
    }

    public void testGetBidder() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        User returnedBidder = bid.getBidder();
        assertEquals(returnedBidder.getName(), bidder.getName());
        assertEquals(returnedBidder.getEmail(), bidder.getEmail());
    }

    public void testGetBidAmount() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        assertEquals(bid.getBidAmount(), 1.00f);
    }

    public void testGetAccepted() {
        User owner = new User("Owner", "Owner email");
        User bidder = new User("Bidder", "Bidder email");
        Instrument instrument = new Instrument(owner, "name", "Description");
        Bid bid = new Bid(instrument, owner, bidder, 1.00f);
        assertFalse(bid.getAccepted());
        bid.setAccepted();
        assertTrue(bid.getAccepted());
    }

    // test use case US 05.02.01: ViewCurrentBidsAsBorrower
    public void testViewCurrentBidsAsBorrower() {
        // ViewCurrentBidsAsBorrower should return an ArrayList of Instruments

        ArrayList<Instrument> result;
        User owner1 = new User("User1", "email1");
        User borrower1 = new User("User2", "email2");
        Instrument ins1 = new Instrument(owner1, "owner1", "guitar1");
        Instrument ins2 = new Instrument(owner1, "owner1", "guitar2");
        Bid bid1 = new Bid(ins1, owner1, borrower1, 1.00f);
        Bid bid2 = new Bid(ins2, owner1, borrower1, 2.00f);

        result = borrower1.getBiddedInstruments();
        assertEquals(result.size(), 0);

        ins1.addBid(bid1);
        ins2.addBid(bid2);
        result = borrower1.getBiddedInstruments();
        assertEquals(result.size(), 2);
        assertTrue(result.contains(ins1));
        assertTrue(result.contains(ins2));
    }

    // test use case US 05.04.01: ViewCurrentBidsAsOwner
    public void testViewCurrentBidsAsOwner() {
        // ViewCurrentBidsAsOwner should return an ArrayList of Instruments

        ArrayList<Instrument> result;
        User owner1 = new User("User1", "email1");
        Instrument ins1 = new Instrument(owner1, "name1", "guitar1");
        Instrument ins2 = new Instrument(owner1, "name2", "guitar2");

        result = owner1.getOwnedBiddedInstruments();
        assertEquals(result.size(), 0);

        ins1.setStatus("bidded");
        ins2.setStatus("bidded");

        result = owner1.getOwnedBiddedInstruments();
        assertEquals(result.size(), 2);
        assertTrue(result.contains(ins1));
        assertTrue(result.contains(ins2));
    }

}
