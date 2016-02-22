package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by William on 2016-02-21.
 */
public class ControllerTest extends ActivityInstrumentationTestCase2 {
    public ControllerTest() {
        super(Controller.class);
    }

    public void testGetCurrentUser() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUser());
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        assertEquals("user", user.getName());
        assertEquals("email", user.getEmail());
    }

    public void testCreateUser() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email"));
        UserList users = controller.getUserList();
        User user = users.getUser(0);
        assertEquals("user", user.getName());
        assertEquals("email", user.getEmail());
        assertFalse(controller.createUser("user", "email"));
    }

    public void testLogin() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        assertNull(controller.getCurrentUser());
        assertFalse(controller.login("j"));
        assertTrue(controller.login("user"));
        User user = controller.getCurrentUser();
        assertEquals("user", user.getName());
        assertEquals("email", user.getEmail());
    }

    public void testLogout() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUser());
        controller.logout();
        assertNull(controller.getCurrentUser());
    }

    public void testEditCurrentUser() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.createUser("user2", "email2");
        controller.login("user");
        assertTrue(controller.editCurrentUser("edit1", "edit2"));
        User user = controller.getCurrentUser();
        assertEquals("edit1", user.getName());
        assertEquals("edit2", user.getEmail());
        assertFalse(controller.editCurrentUser("user2", "email"));
        assertEquals("edit1", user.getName());
        assertEquals("edit2", user.getEmail());
    }

    public void testGetCurrentUsersOwnedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersOwnedInstruments());
    }

    public void testGetCurrentUsersOwnedBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersOwnedBorrowedInstruments());
    }

    public void testGetCurrentUsersBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBorrowedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersBorrowedInstruments());
    }

    public void testGetCurrentUsersBiddedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBiddedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersBiddedInstruments());
    }

    public void testGetCurrentUsersBids() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBids());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersBids());
    }

    public void testAddInstrument() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument = new Instrument(user, "name", "description");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(0, controller.getInstrumentList().size());
        assertEquals(0, instruments.size());
        controller.addInstrument(instrument);
        instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(1, instruments.size());
        instrument = instruments.getInstrument(0);
        assertEquals("name", instrument.getName());
        assertEquals("description", instrument.getDescription());
        assertEquals("available", instrument.getStatus());

        // make sure it was added to the complete instrument list
        instruments = controller.getInstrumentList();
        assertEquals(1, instruments.size());
        instrument = instruments.getInstrument(0);
        assertEquals("name", instrument.getName());
        assertEquals("description", instrument.getDescription());
        assertEquals("available", instrument.getStatus());
    }

    public void testAddInstrument1() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(0, controller.getInstrumentList().size());
        assertEquals(0, instruments.size());
        controller.addInstrument("name", "description");
        instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(1, instruments.size());
        Instrument instrument = instruments.getInstrument(0);
        assertEquals("name", instrument.getName());
        assertEquals("description", instrument.getDescription());
        assertEquals("available", instrument.getStatus());

        // make sure it was added to the complete instrument list
        instruments = controller.getInstrumentList();
        assertEquals(1, instruments.size());
        instrument = instruments.getInstrument(0);
        assertEquals("name", instrument.getName());
        assertEquals("description", instrument.getDescription());
        assertEquals("available", instrument.getStatus());
    }

    public void testEditInstrument() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument = new Instrument(user, "name", "description");
        controller.addInstrument(instrument);
        controller.editInstrument(instrument, "edit1", "edit2");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        instrument = instruments.getInstrument(0);
        assertEquals("edit1", instrument.getName());
        assertEquals("edit2", instrument.getDescription());

        // make sure the complete list of instruments was also updated
        instruments = controller.getInstrumentList();
        instrument = instruments.getInstrument(0);
        assertEquals("edit1", instrument.getName());
        assertEquals("edit2", instrument.getDescription());
    }

    public void testEditInstrument1() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument = new Instrument(user, "name", "description");
        controller.addInstrument(instrument);
        controller.editInstrument(0, "edit1", "edit2");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        instrument = instruments.getInstrument(0);
        assertEquals("edit1", instrument.getName());
        assertEquals("edit2", instrument.getDescription());

        // make sure the complete list of instruments was also updated
        instruments = controller.getInstrumentList();
        instrument = instruments.getInstrument(0);
        assertEquals("edit1", instrument.getName());
        assertEquals("edit2", instrument.getDescription());
    }

    public void testDeleteInstrument() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument1 = new Instrument(user, "name1", "description1");
        Instrument instrument2 = new Instrument(user, "name2", "description2");
        controller.addInstrument(instrument1);
        controller.addInstrument(instrument2);
        controller.deleteInstrument(instrument1);
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        assertEquals("name2", instrument.getName());
        assertEquals("description2", instrument.getDescription());

        // make sure the complete insturment list is updated as well
        instrument = controller.getInstrumentList().getInstrument(0);
        assertEquals("name2", instrument.getName());
        assertEquals("description2", instrument.getDescription());
    }

    public void testDeleteInstrument1() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument1 = new Instrument(user, "name1", "description1");
        Instrument instrument2 = new Instrument(user, "name2", "description2");
        controller.addInstrument(instrument1);
        controller.addInstrument(instrument2);
        controller.deleteInstrument(0);
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        assertEquals("name2", instrument.getName());
        assertEquals("description2", instrument.getDescription());

        // make sure the complete insturment list is updated as well
        instrument = controller.getInstrumentList().getInstrument(0);
        assertEquals("name2", instrument.getName());
        assertEquals("description2", instrument.getDescription());
    }

    // Test use case 04.01.01
    public void testSearchInstruments() {
        //TODO: search instruments test
    }

    public void testMakeBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        controller.login("owner");
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");
        Instrument instrument = controller.getInstrumentList().getInstrument(0);
        controller.makeBidOnInstrument(instrument, 1.00f);
        assertEquals("bidded", instrument.getStatus());

        // make sure bid list connected to instrument is updated
        BidList bids = instrument.getBids();
        assertEquals(1, bids.size());
        Bid bid = bids.getBid(0);
        assertEquals(1.00f, bid.getBidAmount());
        assertEquals("owner", bid.getOwner().getName());
        assertEquals("email1", bid.getOwner().getEmail());
        assertEquals("bidder", bid.getBidder().getName());
        assertEquals("email2", bid.getBidder().getEmail());
        assertEquals("name", bid.getInstrument().getName());
        assertEquals("description", bid.getInstrument().getDescription());

        // make sure bid is added to bidders list of bids
        bids = controller.getCurrentUsersBids();
        assertEquals(1, bids.size());
        bid = bids.getBid(0);
        assertEquals(1.00f, bid.getBidAmount());
        assertEquals("owner", bid.getOwner().getName());
        assertEquals("email1", bid.getOwner().getEmail());
        assertEquals("bidder", bid.getBidder().getName());
        assertEquals("email2", bid.getBidder().getEmail());
        assertEquals("name", bid.getInstrument().getName());
        assertEquals("description", bid.getInstrument().getDescription());

        // make sure instrument is added to bidders list of bidded instruments
        InstrumentList instruments = controller.getCurrentUsersBiddedInstruments();
        assertEquals(1, instruments.size());
        assertEquals("bidded", instruments.getInstrument(0).getStatus());
        assertEquals("owner", instruments.getInstrument(0).getOwner().getName());
        assertEquals("email1", instruments.getInstrument(0).getOwner().getEmail());
        assertEquals(1, instruments.getInstrument(0).getBids().size());

        // make sure instrument in owners list of owned instruments is updated
        controller.logout();
        controller.login("owner");
        instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(1, instruments.size());
        assertEquals("bidded", instruments.getInstrument(0).getStatus());
        assertEquals(1, instruments.getInstrument(0).getBids().size());
        assertEquals(1.00f, instruments.getInstrument(0).getBids().getBid(0).getBidAmount());
        assertEquals("bidder", instruments.getInstrument(0).getBids().getBid(0).getBidder().getName());
        assertEquals("email2", instruments.getInstrument(0).getBids().getBid(0).getBidder().getEmail());
    }

    public void testAcceptBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email");
        controller.login("owner");
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");
        Instrument instrument = controller.getInstrumentList().getInstrument(0);
        controller.makeBidOnInstrument(instrument, 1.00f);
        controller.makeBidOnInstrument(instrument, 2.00f);
        controller.logout();
        controller.login("owner");
        assertEquals(2, controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().size());
        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        Bid bid = instrument.getBids().getBid(0);
        controller.acceptBidOnInstrument(instrument, bid);

        // make sure all other bids are declined and instrument is set to borrowed
        assertEquals("borrowed", controller.getCurrentUsersOwnedInstruments().getInstrument(0).getStatus());
        assertEquals(1, controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().size());
        assertEquals(1.00f, controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount());

        // make sure the instrument is removed from the complete list of non borrowed instruments
        assertEquals(0, controller.getInstrumentList().size());

        // make sure the instrument is added to the bidders borrowed instrument list and removed from
        // list of bidded instruments
        controller.logout();
        controller.login("bidder");
        assertEquals(1, controller.getCurrentUsersBorrowedInstruments().size());
        assertEquals(0, controller.getCurrentUsersBids().size());
        assertEquals(0, controller.getCurrentUsersBiddedInstruments().size());
        assertEquals("borrowed", controller.getCurrentUsersBorrowedInstruments().getInstrument(0).getStatus());
        assertEquals(1, controller.getCurrentUsersBorrowedInstruments().getInstrument(0).getBids().size());
        assertEquals(1.00f, controller.getCurrentUsersBorrowedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount());
    }

    public void testDeclineBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email");
        controller.login("owner");
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");
        User bidder = controller.getCurrentUser();
        Instrument instrument = controller.getInstrumentList().getInstrument(0);
        controller.makeBidOnInstrument(instrument, 1.00f);
        controller.makeBidOnInstrument(instrument, 2.00f);
        controller.logout();
        controller.login("owner");
        assertEquals(2, controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().size());
        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        Bid bid = instrument.getBids().getBid(0);
        controller.declineBidOnInstrument(instrument, bid);

        // make sure only 1 bid left and instrument still has status bidded
        assertEquals(1, instrument.getBids().size());
        assertEquals("bidded", instrument.getStatus());
        assertEquals(2.00f, instrument.getBids().getBid(0).getBidAmount());

        // make sure the bid is deleted from the bidders list of bids
        controller.logout();
        controller.login("bidder");
        assertEquals(1, controller.getCurrentUsersBids().size());
        assertEquals(1, controller.getCurrentUsersBiddedInstruments().size());
        assertEquals(2.00f, controller.getCurrentUsersBids().getBid(0).getBidAmount());
    }
}