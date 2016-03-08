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
        assertTrue(controller.createUser("user", "email"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(controller.getCurrentUser());
        controller.logout();
        assertNull(controller.getCurrentUser());
        controller.login("user");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }

    public void testEditCurrentUser() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.editCurrentUser("edit1", "edit2"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(controller.getCurrentUsersOwnedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersOwnedBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(controller.getCurrentUsersOwnedBorrowedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersBorrowedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBorrowedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(controller.getCurrentUsersBorrowedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersBiddedInstruments() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBiddedInstruments());
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(controller.getCurrentUsersBiddedInstruments());
        controller.deleteUser();
    }

    public void testGetCurrentUsersBids() {
        Controller controller = new Controller();
        assertNull(controller.getCurrentUsersBids());
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(controller.getCurrentUsersBids());
        controller.deleteUser();
    }

    public void testAddInstrument() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }

    public void testEditInstrument() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = controller.getCurrentUser();
        Instrument instrument = new Instrument(user.getName(), "name", "description");
        controller.addInstrument(instrument);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.editInstrument(instrument, "edit1", "edit2");
        InstrumentList instruments = controller.getCurrentUsersOwnedInstruments();
        instrument = instruments.getInstrument(0);
        assertEquals("edit1", instrument.getName());
        assertEquals("edit2", instrument.getDescription());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }

    public void testDeleteInstrument() {
        Controller controller = new Controller();
        assertTrue(controller.createUser("user", "email")); ;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(controller.login("user"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = controller.getCurrentUser();
        Instrument instrument1 = new Instrument(user.getId(), "name1", "description1");
        Instrument instrument2 = new Instrument(user.getId(), "name2", "description2");
        controller.addInstrument(instrument1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.addInstrument(instrument2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteInstrument(instrument1);
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        assertEquals("name2", instrument.getName());
        assertEquals("description2", instrument.getDescription());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }

    // Test use case 04.01.01
    public void testSearchInstruments() {
        //TODO: search instruments test
    }

    public void testMakeBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.logout();
        controller.createUser("bidder", "email2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("bidder");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
        getUserTask.execute(user1.getId());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }

    public void testAcceptBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.logout();
        controller.createUser("bidder", "email2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("bidder");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.logout();
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.acceptBidOnInstrument(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(controller.getCurrentUsersOwnedBorrowedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount(), 1.0f);
        controller.logout();
        controller.login("bidder");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(controller.getCurrentUsersBids().size(), 0);
        assertEquals(controller.getCurrentUsersBorrowedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersBorrowedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount(), 1.0f);

        controller.deleteUser();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }

    public void testDeclineBidOnInstrument() {
        Controller controller = new Controller();
        controller.createUser("owner", "email1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user1 = controller.getCurrentUser();
        controller.addInstrument("name", "description");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.logout();
        controller.createUser("bidder", "email2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("bidder");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.makeBidOnInstrument(user1.getOwnedInstruments().getInstrument(0), 2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user2 = controller.getCurrentUser();
        controller.logout();
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.declineBidOnInstrument(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(controller.getCurrentUsersOwnedBorrowedInstruments().size(), 0);
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);

        controller.logout();
        controller.login("bidder");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(controller.getCurrentUsersBids().size(), 1);
        assertEquals(controller.getCurrentUsersBorrowedInstruments().size(), 0);
        assertEquals(controller.getCurrentUsersBids().getBid(0).getBidAmount(), 2.0f);

        controller.logout();
        controller.login("owner");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), 1);
        assertEquals(controller.getCurrentUsersOwnedBorrowedInstruments().size(), 0);
        assertEquals(controller.getCurrentUsersOwnedInstruments().getInstrument(0).getBids().getBid(0).getBidAmount(), 2.0f);

        controller.deleteUser();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.login("bidder");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.deleteUser();
    }
}
