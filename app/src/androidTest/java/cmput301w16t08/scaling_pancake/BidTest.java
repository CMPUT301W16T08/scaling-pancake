
package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest() {
        super(Bid.class);
    }

    public void testGetInstrumentId() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1);
        assertEquals(bid.getInstrumentId(), instrument.getId());
    }

    public void testGetOwnerId() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1);
        assertEquals(bid.getOwnerId(), "ownerId");
    }

    public void testGetBidderId() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1);
        assertEquals(bid.getBidderId(), "bidderId");
    }

    public void testGetBidAmount() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1);
        assertEquals(bid.getBidAmount(), 1.0f);
    }

    public void testGetAccepted() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1);
        assertEquals(bid.getAccepted(), false);
        bid.setAccepted();
        assertEquals(bid.getAccepted(), true);
    }

    public void testSetAccepted() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1);
        assertEquals(bid.getAccepted(), false);
        bid.setAccepted();
        assertEquals(bid.getAccepted(), true);
    }

    public void testGetId() {
        Instrument instrument = new Instrument("ownerId", "name", "description");
        Bid bid = new Bid(instrument.getId(), "ownerId", "bidderId", 1, "bidId");
        assertEquals(bid.getId(), "bidId");
    }
}
