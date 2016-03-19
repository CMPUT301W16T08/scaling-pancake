
package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

public class InstrumentTest extends ActivityInstrumentationTestCase2 {
    public InstrumentTest() {
        super(Instrument.class);
    }

    public void testGetStatus() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getStatus(), "available");
    }

    // Use case: US 02.01.01 Status of available, bidded, and borrowed
    public void testSetStatus() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getStatus(), "available");
        instrument.setStatus("bidded");
        assertEquals(instrument.getStatus(), "bidded");
        instrument.setStatus("borrowed");
        assertEquals(instrument.getStatus(), "borrowed");
    }

    public void testGetName() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getName(), "name");
    }

    public void testSetName() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getName(), "name");
        instrument.setName("edit");
        assertEquals(instrument.getName(), "edit");
    }

    public void testGetDescription() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getDescription(), "Description");
    }

    public void testSetDescription() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getDescription(), "Description");
        instrument.setDescription("edit");
        assertEquals(instrument.getDescription(), "edit");
    }

    public void testGetOwnerId() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        assertEquals(instrument.getOwnerId(), owner.getId());
    }

    public void testGetBorrowedById() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        instrument.setBorrowedById(bidder1.getId());
        instrument.setStatus("borrowed");
        assertEquals(instrument.getBorrowedById(), bidder1.getId());
    }

    public void testSetBorrowedBy() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        instrument.setBorrowedById(bidder1.getId());
        instrument.setStatus("borrowed");
        assertEquals(instrument.getBorrowedById(), bidder1.getId());
    }

    // test use case US 05.05.01: ViewBidsOnInstrument
    public void testGetBids() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        Bid bid1 = new Bid(instrument.getId(), owner.getId(), bidder1.getId(), 1.00f);
        Bid bid2 = new Bid(instrument.getId(), owner.getId(), bidder2.getId(), 2.00f);
        BidList returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 0);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 2);

        // Test first bid
        Bid returnedBid1 = returnedBids.getBid(0);
        assertEquals(returnedBid1.getOwnerId(), owner.getId());
        assertEquals(returnedBid1.getBidderId(), bidder1.getId());
        assertEquals(returnedBid1.getInstrumentId(), instrument.getId());
        assertEquals(returnedBid1.getId(), bid1.getId());
        assertEquals(returnedBid1.getBidAmount(), bid1.getBidAmount());

        // Test second bid
        Bid returnedBid2 = returnedBids.getBid(1);
        assertEquals(returnedBid2.getOwnerId(), owner.getId());
        assertEquals(returnedBid2.getBidderId(), bidder2.getId());
        assertEquals(returnedBid2.getInstrumentId(), instrument.getId());
        assertEquals(returnedBid2.getId(), bid2.getId());
        assertEquals(returnedBid2.getBidAmount(), bid2.getBidAmount());
    }

    public void testAddBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        Bid bid1 = new Bid(instrument.getId(), owner.getId(), bidder1.getId(), 1.00f);
        BidList returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 0);
        instrument.addBid(bid1);
        assertEquals(returnedBids.size(), 1);
        assertEquals(instrument.getStatus(), "bidded");

        Bid returnedBid1 = returnedBids.getBid(0);
        assertEquals(returnedBid1.getOwnerId(), owner.getId());
        assertEquals(returnedBid1.getBidderId(), bidder1.getId());
        assertEquals(returnedBid1.getInstrumentId(), instrument.getId());
        assertEquals(returnedBid1.getId(), bid1.getId());
        assertEquals(returnedBid1.getBidAmount(), bid1.getBidAmount());
    }

    public void testAcceptBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        Bid bid1 = new Bid(instrument.getId(), owner.getId(), bidder1.getId(), 1.00f);
        Bid bid2 = new Bid(instrument.getId(), owner.getId(), bidder2.getId(), 2.00f);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        instrument.acceptBid(bid2);

        BidList returnedBids = instrument.getBids();
        assertEquals(instrument.getStatus(), "borrowed");
        assertEquals(returnedBids.size(), 1);
        assertEquals(returnedBids.getBid(0).getId(), bid2.getId());
    }

    public void testDeclineBid() {
        User owner = new User("Owner", "Owner email");
        User bidder1 = new User("Bidder1", "Bidder1 email");
        User bidder2 = new User("Bidder2", "Bidder2 email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        Bid bid1 = new Bid(instrument.getId(), owner.getId(), bidder1.getId(), 1.00f);
        Bid bid2 = new Bid(instrument.getId(), owner.getId(), bidder2.getId(), 2.00f);
        instrument.addBid(bid1);
        instrument.addBid(bid2);
        instrument.declineBid(bid2);
        assertEquals(instrument.getStatus(), "bidded");

        BidList returnedBids = instrument.getBids();
        assertEquals(returnedBids.size(), 1);
        assertEquals(returnedBids.getBid(0).getId(), bid1.getId());

        instrument.declineBid(bid1);
        assertEquals(instrument.getStatus(), "available");
    }

    /*public void testAddThumbnail() {
        User owner = new User("Owner", "Owner email");
        Instrument instrument = new Instrument(owner.getId(), "name", "Description");
        instrument.addThumbnail( //LOAD THE TEST THUMBNAIL FROM FILE
    }*/
}

