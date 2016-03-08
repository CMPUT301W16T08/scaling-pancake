/*

package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

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

}

*/
