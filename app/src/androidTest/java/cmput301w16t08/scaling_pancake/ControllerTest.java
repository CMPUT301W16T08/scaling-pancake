package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ControllerTest extends ActivityInstrumentationTestCase2 {
    public ControllerTest() {
        super(Controller.class);
    }

    // Use case: US 03.01.01 Profile with unique username and contact info
    public void testCreateUser() {
        // also tests login and getCurrentUser
        Controller controller = new Controller();
        assertNull(controller.getCurrentUser());
        assertTrue(controller.createUser("user", "email"));
        assertTrue(controller.login("user"));
        User user = controller.getCurrentUser();
        assertEquals("user", user.getName());
        assertEquals("email", user.getEmail());
        controller.logout();
        assertFalse(controller.createUser("user", "email2"));
        controller.login("user");
        controller.deleteUser();
    }

    // Use case: US 03.03.01 Get user by username
    public void testGetUserByName() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("user");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user = controller.getUserByName("user");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(user.getId(), controller.getCurrentUser().getId());
        assertEquals(user.getName(), controller.getCurrentUser().getName());
        controller.deleteUser();
    }

    public void testGetUserById() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("user");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        User user = controller.getUserById(controller.getCurrentUser().getId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(user.getId(), controller.getCurrentUser().getId());
        assertEquals(user.getName(), controller.getCurrentUser().getName());
        controller.deleteUser();
    }

    public void testDeleteUser() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUser());
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
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
        getUserTask1.execute(user.getId());
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
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        assertNotNull(controller.getCurrentUser());
        controller.logout();
        assertNull(controller.getCurrentUser());
        controller.login("user");
        controller.deleteUser();
    }

    // Use case: US 03.02.01 Edit contact information
    public void testEditCurrentUser() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
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

    // Use case: US 01.02.01 Get owned instruments
    public void testGetCurrentUsersOwnedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        assertNotNull(controller.getCurrentUsersOwnedInstruments());
        controller.deleteUser();
    }

    // Use case: US 06.02.01 Get owned borrowed instruments
    public void testGetCurrentUsersOwnedBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        assertNotNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        controller.deleteUser();
    }

    // Use case: US 06.01.01 Get borrowed instruments
    public void testGetCurrentUsersBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBorrowedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        assertNotNull(controller.getCurrentUsersBorrowedInstruments());
        controller.deleteUser();
    }

    // Use case: US 05.04.01 Get current users owned bidded instruments
    public void testGetCurrentUsersBiddedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBiddedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        assertNotNull(controller.getCurrentUsersBiddedInstruments());
        controller.deleteUser();
    }

    // Use case: US 05.02.01 Get current users pending bids
    public void testGetCurrentUsersBids() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBids());
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        assertNotNull(controller.getCurrentUsersBids());
        controller.deleteUser();
    }

    // Use case: US 01.01.01 Add an instrument
    public void testAddInstrument() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
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

    // Use case: US 01.04.01 Edit an instrument
    public void testEditInstrument() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
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

    // Use case: US 01.05.01 Delete an instrument
    public void testDeleteInstrument() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        assertTrue(controller.login("user"));
        User user = controller.getCurrentUser();
        Instrument instrument1 = new Instrument(user.getId(), "name1", "description1");
        Instrument instrument2 = new Instrument(user.getId(), "name2", "description2");
        controller.addInstrument(instrument1);
        controller.addInstrument(instrument2);
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

    // Use case: US 04.01.01 Search instruments
    // Use case: US 04.02.01 Get search results
    public void testSearchInstruments() {
        //TODO: search instruments test
    }

    // Use case: US 05.01.01 Make bid on instrument
    public void testMakeBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        controller.login("owner");
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 1);
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(user1.getId());
        ArrayList<String> users = null;
        try {
            users = getUserTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        User user2 = new Deserializer().deserializeUser(users.get(0));
        assertEquals(user2.getOwnedInstruments().getInstrument(0).getBids().size(), 1);
        ElasticsearchController.GetUserTask  getUserTask1 = new ElasticsearchController.GetUserTask();
        getUserTask1.execute(controller.getCurrentUser().getId());
        try {
            users = getUserTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        user2 = new Deserializer().deserializeUser(users.get(0));
        assertEquals(user2.getBids().size(), 1);
        controller.deleteUser();
        controller.login("owner");
        controller.deleteUser();
    }

    // Use case: US 05.06.01 Accept bid on instrument
    public void testAcceptBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        controller.login("owner");
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 1);
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 2);
        controller.logout();
        controller.login("owner");
        controller.acceptBidOnInstrument(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0));
        assertEquals(controller.getCurrentUsersOwnedBorrowedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount(), 1.0f);
        controller.logout();
        controller.login("bidder");
        assertEquals(controller.getCurrentUsersBids().size(), 0);
        assertEquals(controller.getCurrentUsersBorrowedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersBorrowedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount(), 1.0f);

        controller.deleteUser();
        controller.login("owner");
        controller.deleteUser();
    }

    // Use case: US 05.07.01 Decline bid on instrument
    public void testDeclineBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        controller.login("owner");
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        controller.logout();
        controller.createUser("bidder", "email2");
        controller.login("bidder");
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 1);
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 2);
        User user2 = controller.getCurrentUser();
        controller.logout();
        controller.login("owner");
        controller.declineBidOnInstrument(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0));
        assertEquals(controller.getCurrentUsersOwnedBorrowedInstruments().size(), 0);
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);

        controller.logout();
        controller.login("bidder");
        assertEquals(controller.getCurrentUsersBids().size(), 1);
        assertEquals(controller.getCurrentUsersBorrowedInstruments().size(), 0);
        assertEquals(controller.getCurrentUsersBids().getBid(0).getBidAmount(), 2.0f);

        controller.logout();
        controller.login("owner");
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersOwnedBorrowedInstruments().size(), 0);
        assertEquals(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount(), 2.0f);

        controller.deleteUser();
        controller.login("bidder");
        controller.deleteUser();
    }
}
