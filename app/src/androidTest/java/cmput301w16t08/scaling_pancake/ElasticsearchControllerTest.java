
package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by William on 2016-02-25.
 */

public class ElasticsearchControllerTest extends ActivityInstrumentationTestCase2 {
    public ElasticsearchControllerTest() {
        super(ElasticsearchController.class);
    }

    // test also covers GetUserTask() and GetUserByNamaTask()
    public void testCreateUserTask() {
        User user = new User("testuser1", "testemail1");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // GetUserTask()
        ElasticsearchController.GetUserTask task2 = new ElasticsearchController.GetUserTask();
        task2.execute(user.getId());
        ArrayList<String> users = null;
        try {
            users = task2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(users);
        Deserializer deserializer = new Deserializer();
        User user2 = deserializer.deserializeUser(users.get(0));
        assertNotNull(user2);
        assertTrue(user2.getId().equals(user.getId()));
        assertTrue(user2.getName().equals(user.getName()));
        assertTrue(user2.getEmail().equals(user.getEmail()));

        // GetUserByNameTask()
        ElasticsearchController.GetUserByNameTask task3 = new ElasticsearchController.GetUserByNameTask();
        task3.execute(user.getName());
        ArrayList<String> users2 = null;
        try {
            users2 = task3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(users2);
        user2 = deserializer.deserializeUser(users.get(0));
        assertNotNull(user2);
        assertTrue(user2.getId().equals(user.getId()));
        assertTrue(user2.getName().equals(user.getName()));
        assertTrue(user2.getEmail().equals(user.getEmail()));

        // delete the test user
        ElasticsearchController.DeleteUserTask task4 = new ElasticsearchController.DeleteUserTask();
        task4.execute(user);
    }

    // test also covers GetInstrumentTask()
    public void testUpdateUserTask() {
        User user = new User("testuser1", "testemail1");
        Log.d("ESC", "CreateUserTask starting...");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);
        user.addOwnedInstrument("testinstrument1", "testdescription1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("ESC", "UpdateUserTask starting...");
        ElasticsearchController.UpdateUserTask task3 = new ElasticsearchController.UpdateUserTask();
        task3.execute(user);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("ESC", "GetUserTask starting...");
        ElasticsearchController.GetUserTask task4 = new ElasticsearchController.GetUserTask();
        task4.execute(user.getId());
        ArrayList<String> string = null;
        Deserializer deserializer = new Deserializer();
        try {
            string = task4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(string);
        User user2 = deserializer.deserializeUser(string.get(0));
        assertTrue(user2.getOwnedInstruments().containsInstrument(user.getOwnedInstruments().getInstrument(0)));

        // delete the user
        ElasticsearchController.DeleteUserTask task5 = new ElasticsearchController.DeleteUserTask();
        task5.execute(user);
    }

    public void testDeleteUserTask() {
        User user = new User("testuser1", "testemail1");
        ElasticsearchController.CreateUserTask task1 = new ElasticsearchController.CreateUserTask();
        task1.execute(user);

        // delete the test user
        ElasticsearchController.DeleteUserTask task3 = new ElasticsearchController.DeleteUserTask();
        task3.execute(user);
        ElasticsearchController.GetUserTask task4 = new ElasticsearchController.GetUserTask();
        task4.execute(user.getId());
        ArrayList<String> user2 = null;
        try {
            // passing previous assertion means user2 is not null before this assertion
            user2 = task4.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(user2.size() == 0);
    }

    public void testSearchInstrumentsTask() {
        //TODO: add test
    }
}
