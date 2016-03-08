package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ControllerTest extends ActivityInstrumentationTestCase2 {
    public ControllerTest() {
        super(Controller.class);
    }

    public void testCreateUser() {
        // also tests login and getCurrentUser
        Controller controller = new Controller();
        assertNull(controller.getCurrentUser());
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        assertEquals("user", user.getName());
        assertEquals("email", user.getEmail());
        controller.logout();
        assertFalse(controller.createUser("user", "email2"));
        controller.login("user");
        controller.deleteUser();
    }

    public void testDeleteUser() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUser());
        controller.createUser("user", "email");
        controller.login("user");

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(controller.getCurrentUser().getId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotSame(users.size(), 0);
        User user = new Deserializer().deserializeUser(users.get(0));
        assertEquals(user.getName(), "user");

        controller.deleteUser();
        ElasticsearchController.GetUserTask getUserTask1 = new ElasticsearchController.GetUserTask();
        getUserTask.execute(user.getId());
        try {
            users = getUserTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertEquals(users.size(), 0);
    }

    public void testLogout() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUser());
        controller.logout();
        assertNull(controller.getCurrentUser());
        controller.login("user");
        controller.deleteUser();
    }

    public void testEditCurrentUser() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        assertTrue(controller.editCurrentUser("edit1", "edit2"));
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(controller.getCurrentUser().getId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Deserializer deserializer = new Deserializer();
        User user = deserializer.deserializeUser(users.get(0));
        assertEquals(user.getName(), "edit1");
        assertEquals(user.getEmail(), "edit2");
        controller.deleteUser();
    }

    public void testGetCurrentUsersOwnedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersOwnedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersOwnedBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBorrowedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersBorrowedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersBiddedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBiddedInstruments());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersBiddedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersBids() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBids());
        controller.createUser("user", "email");
        controller.login("user");
        assertNotNull(controller.getCurrentUsersBids());
        controller.deleteUser();
    }

    public void testAddInstrument() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument = new Instrument(user.getId(), "name", "description");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(0, instruments.size());
        controller.addInstrument(instrument);
        instruments = controller.getCurrentUsersOwnedInstruments();
        assertEquals(1, instruments.size());
        instrument = instruments.getInstrument(0);
        assertEquals("name", instrument.getName());
        assertEquals("description", instrument.getDescription());
        assertEquals("available", instrument.getStatus());

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(user.getId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User u = new Deserializer().deserializeUser(users.get(0));
        assertEquals(user.getOwnedInstruments().size(), 1);
        assertEquals("name", user.getOwnedInstruments().getInstrument(0).getName());
        assertEquals("description", user.getOwnedInstruments().getInstrument(0).getDescription());
        assertEquals("available", user.getOwnedInstruments().getInstrument(0).getStatus());
        controller.deleteUser();
    }

    public void testEditInstrument() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument = new Instrument(user.getName(), "name", "description");
        controller.addInstrument(instrument);
        controller.editInstrument(instrument, "edit1", "edit2");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        instrument = instruments.getInstrument(0);
        assertEquals("edit1", instrument.getName());
        assertEquals("edit2", instrument.getDescription());

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(user.getId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User u = new Deserializer().deserializeUser(users.get(0));
        assertEquals(user.getOwnedInstruments().size(), 1);
        assertEquals("edit1", user.getOwnedInstruments().getInstrument(0).getName());
        assertEquals("edit2", user.getOwnedInstruments().getInstrument(0).getDescription());
        controller.deleteUser();
    }

    public void testDeleteInstrument() {
        Controller controller = new Controller();
        controller.createUser("user", "email");
        controller.login("user");
        User user = controller.getCurrentUser();
        Instrument instrument1 = new Instrument(user.getId(), "name1", "description1");
        Instrument instrument2 = new Instrument(user.getId(), "name2", "description2");
        controller.addInstrument(instrument1);
        controller.addInstrument(instrument2);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteInstrument(instrument1);
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        assertEquals("name2", instrument.getName());
        assertEquals("description2", instrument.getDescription());

        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(user.getId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User u = new Deserializer().deserializeUser(users.get(0));
        assertEquals(user.getOwnedInstruments().size(), 1);
        assertEquals("name2", user.getOwnedInstruments().getInstrument(0).getName());
        assertEquals("description2", user.getOwnedInstruments().getInstrument(0).getDescription());
        controller.deleteUser();
    }

    // Test use case 04.01.01
    public void testSearchInstruments() {
        //TODO: search instruments test
    }

/*    public void testMakeBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        controller.login("owner");
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");

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
    }*/
}
