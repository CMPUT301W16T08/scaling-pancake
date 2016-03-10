
package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(User.class);
    }

    public void testGetName() {
        User user = new User("name", "email");
        assertEquals(user.getName(), "name");
    }

    public void testSetName() {
        User user = new User("name", "email");
        assertEquals(user.getName(), "name");
        user.setName("edit");
        assertEquals(user.getName(), "edit");
    }

    public void testGetEmail() {
        User user = new User("name", "email");
        assertEquals(user.getEmail(), "email");
    }

    public void testSetEmail() {
        User user = new User("name", "email");
        assertEquals(user.getEmail(), "email");
        user.setEmail("edit");
        assertEquals(user.getEmail(), "edit");
    }

    public void testGetOwnedInstruments() {
        User user = new User("name", "email");
        InstrumentList instruments = user.getOwnedInstruments();
        assertEquals(instruments.size(), 0);
        user.addOwnedInstrument("name1", "description");
        instruments = user.getOwnedInstruments();
        assertEquals(instruments.size(), 1);
        assertEquals(instruments.getInstrument(0).getName(), "name1");
        assertEquals(instruments.getInstrument(0).getDescription(), "description");
        assertEquals(instruments.getInstrument(0).getOwnerId(), user.getId());
    }

    public void testGetBorrowedInstruments() {
        User user = new User("name", "email");
        User user2 = new User("name1", "email1");
        user2.addOwnedInstrument("name2", "description");
        InstrumentList instruments = user.getBorrowedInstruments();
        assertEquals(instruments.size(), 0);
        user.addBorrowedInstrument(user2.getOwnedInstruments().getInstrument(0));
        instruments = user.getBorrowedInstruments();
        assertEquals(instruments.size(), 1);
        assertEquals(instruments.getInstrument(0).getName(), "name2");
        assertEquals(instruments.getInstrument(0).getDescription(), "description");
        assertEquals(instruments.getInstrument(0).getOwnerId(), user2.getId());
    }

    public void testGetOwnedBorrowedInstruments() {
        User user = new User("name", "email");
        User borrower = new User("borrower", "email1");
        user.addOwnedInstrument("name1", "description1");
        user.addOwnedInstrument("name2", "description2");
        InstrumentList instruments = user.getOwnedBorrowedInstruments();
        assertEquals(instruments.size(), 0);
        borrower.addBid(new Bid(user.getOwnedInstruments().getInstrument(0).getId(), user.getId(), borrower.getId(), 1));
        user.getOwnedInstruments().getInstrument(0).addBid(borrower.getBids().getBid(0));
        user.getOwnedInstruments().getInstrument(0).acceptBid(0);
        instruments = user.getOwnedBorrowedInstruments();
        assertEquals(instruments.size(), 1);
        assertEquals(instruments.getInstrument(0).getId(), user.getOwnedInstruments().getInstrument(0).getId());
    }

    public void testGetBids() {
        User user = new User("name", "email");
        User user2 = new User("name1", "email1");
        user2.addOwnedInstrument("name2", "description");
        BidList bids = user.getBids();
        assertEquals(bids.size(), 0);
        Bid bid1 = new Bid(user2.getOwnedInstruments().getInstrument(0).getId(), user2.getId(), user.getId(), 1);
        user.addBid(bid1);
        bids = user.getBids();
        assertEquals(bids.size(), 1);
        assertEquals(bids.getBid(0).getId(), bid1.getId());
    }

    public void testAddOwnedInstrument() {
        User user = new User("name", "email");
        InstrumentList instruments = user.getOwnedInstruments();
        assertEquals(instruments.size(), 0);
        Instrument instrument = new Instrument(user.getId(), "name1", "description");
        user.addOwnedInstrument(instrument);
        instruments = user.getOwnedInstruments();
        assertEquals(instruments.size(), 1);
        assertEquals(instruments.getInstrument(0).getId(), instrument.getId());
    }

    public void testAddBorrowedInstrument() {
        User user = new User("name", "email");
        User user2 = new User("user2", "email2");
        InstrumentList instruments = user.getBorrowedInstruments();
        assertEquals(instruments.size(), 0);
        Instrument instrument = new Instrument(user2.getId(), "name1", "description");
        user.addBorrowedInstrument(instrument);
        instruments = user.getBorrowedInstruments();
        assertEquals(instruments.size(), 1);
        assertEquals(instruments.getInstrument(0).getId(), instrument.getId());
    }

    public void testEditOwnedInstrument() {
        User user = new User("name", "email");
        Instrument instrument = new Instrument(user.getId(), "name1", "description");
        user.addOwnedInstrument(instrument);
        user.editOwnedInstrument(instrument, "edit1", "edit2");
        instrument = user.getOwnedInstruments().getInstrument(0);
        assertEquals(instrument.getName(), "edit1");
        assertEquals(instrument.getDescription(), "edit2");
    }

    public void testDeleteOwnedInstrument() {
        User user = new User("name", "email");
        Instrument instrument = new Instrument(user.getId(), "name1", "description");
        user.addOwnedInstrument(instrument);
        assertEquals(user.getOwnedInstruments().size(), 1);
        user.deleteOwnedInstrument(instrument);
        assertEquals(user.getOwnedInstruments().size(), 0);
    }

    public void testDeleteBorrowedInstrument() {
        User user = new User("name", "email");
        User user2 = new User("user2", "email2");
        Instrument instrument = new Instrument(user2.getId(), "name1", "description");
        user.addBorrowedInstrument(instrument);
        assertEquals(user.getBorrowedInstruments().size(), 1);
        user.deleteBorrowedInstrument(instrument);
        assertEquals(user.getBorrowedInstruments().size(), 0);
    }

    public void testAddBid() {
        User user = new User("name", "email");
        User user2 = new User("user2", "email2");
        Instrument instrument = new Instrument(user2.getId(), "name1", "description");
        assertEquals(user.getBids().size(), 0);
        Bid bid = new Bid(instrument.getId(), user2.getId(), user.getId(), 1);
        user.addBid(bid);
        assertEquals(user.getBids().size(), 1);
        assertEquals(user.getBids().getBid(0).getId(), bid.getId());
    }

    public void testDeleteBid() {
        User user = new User("name", "email");
        User user2 = new User("user2", "email2");
        Instrument instrument = new Instrument(user2.getId(), "name1", "description");
        Bid bid = new Bid(instrument.getId(), user2.getId(), user.getId(), 1);
        user.addBid(bid);
        assertEquals(user.getBids().size(), 1);
        user.deleteBid(bid);
        assertEquals(user.getBids().size(), 0);
    }
}
