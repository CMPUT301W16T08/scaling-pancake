package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;

import java.util.concurrent.ExecutionException;

/**
 * Created by William on 2016-02-25.
 */
public class ElasticsearchControllerTest extends ActivityInstrumentationTestCase2 {
    public ElasticsearchControllerTest() {
        super(ElasticsearchController.class);
    }

    // test also covers GetUserTask()
    public void testCreateUserTask() {
        User user = new User("testuser1", "testemail1");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        ElasticsearchController.GetUserTask task2 = new ElasticsearchController.GetUserTask();
        task2.execute("testuser1");
        User user2 = null;
        try {
            user2 = task2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(user2);

        // delete the test user
        ElasticsearchController.DeleteUserTask task3 = new ElasticsearchController.DeleteUserTask();
        task3.execute(user);
    }

    // test also covers GetInstrumentTask()
    public void testAddInstrumentTask() {
        User user = new User("testuser1", "testemail1");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        user.addOwnedInstrument("testinstrument1", "testdescription1");
        ElasticsearchController.AddInstrumentTask task3 = new ElasticsearchController.AddInstrumentTask();
        task3.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.GetInstrumentTask task4 = new ElasticsearchController.GetInstrumentTask();
        task4.execute(user.getOwnedInstruments().getInstrument(0));
        Instrument instrument = null;
        try {
            instrument = task4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(instrument);

        // delete the test instrument and user
        ElasticsearchController.DeleteInstrumentTask task5 = new ElasticsearchController.DeleteInstrumentTask();
        task5.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.DeleteUserTask task6 = new ElasticsearchController.DeleteUserTask();
        task6.execute(user);
    }

    public void testGetInstrumentsTask() {
        User user = new User("testuser2", "testemail2");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        user.addOwnedInstrument("testinstrument1", "testdescription1");
        user.addOwnedInstrument("testinstrument2", "testdescription2");
        ElasticsearchController.AddInstrumentTask task2 = new ElasticsearchController.AddInstrumentTask();
        task2.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.AddInstrumentTask task3 = new ElasticsearchController.AddInstrumentTask();
        task3.execute(user.getOwnedInstruments().getInstrument(1));
        ElasticsearchController.GetInstrumentsTask task4 = new ElasticsearchController.GetInstrumentsTask();
        task4.execute(user);
        InstrumentList instruments = null;
        try {
            instruments = task4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(instruments);
        assertTrue(instruments.containsInstrument(user.getOwnedInstruments().getInstrument(0)));
        assertTrue(instruments.containsInstrument(user.getOwnedInstruments().getInstrument(1)));

        // delete the test instrument and user
        ElasticsearchController.DeleteInstrumentTask task5 = new ElasticsearchController.DeleteInstrumentTask();
        task5.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.DeleteInstrumentTask task6 = new ElasticsearchController.DeleteInstrumentTask();
        task6.execute(user.getOwnedInstruments().getInstrument(1));
        ElasticsearchController.DeleteUserTask task7 = new ElasticsearchController.DeleteUserTask();
        task7.execute(user);
    }

    public void testEditInstrumentTask() {
        User user = new User("testuser2", "testemail2");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        user.addOwnedInstrument("testinstrument1", "testdescription1");
        ElasticsearchController.AddInstrumentTask task3 = new ElasticsearchController.AddInstrumentTask();
        task3.execute(user.getOwnedInstruments().getInstrument(0));

        // edit the instrument
        user.getOwnedInstruments().getInstrument(0).setName("change");
        ElasticsearchController.EditInstrumentTask task4 = new ElasticsearchController.EditInstrumentTask();
        task4.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.GetInstrumentTask task5 = new ElasticsearchController.GetInstrumentTask();
        task5.execute(user.getOwnedInstruments().getInstrument(0));
        Instrument instrument = null;
        try {
            instrument = task5.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(instrument);
        assertEquals(instrument.getName(), user.getOwnedInstruments().getInstrument(0).getName());

        // delete the test instrument and user
        ElasticsearchController.DeleteInstrumentTask task6 = new ElasticsearchController.DeleteInstrumentTask();
        task6.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.DeleteUserTask task7 = new ElasticsearchController.DeleteUserTask();
        task7.execute(user);
    }

    public void testEditUserTask() {
        User user = new User("testuser1", "testemail1");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);

        // edit the user
        user.setName("change");
        ElasticsearchController.EditUserTask task2 = new ElasticsearchController.EditUserTask();
        task2.execute(user);
        ElasticsearchController.GetUserTask task3 = new ElasticsearchController.GetUserTask();
        task3.execute("change");
        User user2 = null;
        try {
            user2 = task3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(user2);

        // delete the test user
        ElasticsearchController.DeleteUserTask task4 = new ElasticsearchController.DeleteUserTask();
        task4.execute(user);
    }

    public void testDeleteUserTask() {
        User user = new User("testuser1", "testemail1");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        ElasticsearchController.GetUserTask task2 = new ElasticsearchController.GetUserTask();
        task2.execute("testuser1");
        User user2 = null;
        try {
            user2 = task2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(user2);

        // delete the test user
        ElasticsearchController.DeleteUserTask task3 = new ElasticsearchController.DeleteUserTask();
        task3.execute(user);
        ElasticsearchController.GetUserTask task4 = new ElasticsearchController.GetUserTask();
        task4.execute("testuser1");
        try {
            // passing previous assertion means user2 is not null before this assertion
            user2 = task4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNull(user2);
    }

    public void testDeleteInstrumentTask() {
        User user = new User("testuser2", "testemail2");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        user.addOwnedInstrument("testinstrument1", "testdescription1");
        ElasticsearchController.AddInstrumentTask task3 = new ElasticsearchController.AddInstrumentTask();
        task3.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.GetInstrumentTask task4 = new ElasticsearchController.GetInstrumentTask();
        task4.execute(user.getOwnedInstruments().getInstrument(0));
        Instrument instrument = null;
        try {
            instrument = task4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(instrument);

        // delete the test instrument and user
        ElasticsearchController.DeleteInstrumentTask task5 = new ElasticsearchController.DeleteInstrumentTask();
        task5.execute(user.getOwnedInstruments().getInstrument(0));
        ElasticsearchController.GetInstrumentTask task6 = new ElasticsearchController.GetInstrumentTask();
        task6.execute(user.getOwnedInstruments().getInstrument(0));
        try {
            // passing previous assertion means instrument is not null before this assignment
            instrument = task6.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNull(instrument);
        ElasticsearchController.DeleteUserTask task7 = new ElasticsearchController.DeleteUserTask();
        task7.execute(user);
    }

    public void testSearchInstrumentsTask() {
        //TODO: add test
    }
}
