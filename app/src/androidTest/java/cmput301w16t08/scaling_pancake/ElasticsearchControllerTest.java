package cmput301w16t08.scaling_pancake;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;

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
        AsyncTask<User, Void, Boolean> task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        assertNotNull("The saved user should not be null",
                new ElasticsearchController.GetUserTask().execute("testuser1"));

        // delete the test user
        AsyncTask<User, Void, Boolean> task3 = new ElasticsearchController.DeleteUserTask();
        task3.execute(user);
    }

    // test also covers GetInstrumentTask()
    public void testAddInstrumentTask() {
        User user = new User("testuser2", "testemail2");
        AsyncTask<User, Void, Boolean> task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        user.addOwnedInstrument("testinstrument1", "testdescription1");
        ElasticsearchController.EditUserTask task2 = new ElasticsearchController.EditUserTask();
        task2.execute(user);
        assertTrue(task2.isSucceeded);
        ElasticsearchController.AddInstrumentTask task3 = new ElasticsearchController.AddInstrumentTask();
        task3.execute(user.getOwnedInstruments().getInstrument(0));
        assertTrue(task3.isSucceeded);

        // delete the test instrument and user
        AsyncTask<Instrument, Void, Boolean> task4 = new ElasticsearchController.DeleteInstrumentTask();
        task4.execute(user.getOwnedInstruments().getInstrument(0));
        AsyncTask<User, Void, Boolean> task5 = new ElasticsearchController.DeleteUserTask();
        task5.execute(user);
    }

    public void testEditInstrumentTask() {

    }

    public void testEditUserTask() {

    }

    public void testDeleteUserTask() {

    }

    public void testDeleteInstrumentTask() {

    }
}
