package cmput301w16t08.scaling_pancake;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.activities.CreateProfileActivity;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.User;


public class CreateProfileActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;
    EditText username;
    EditText email;

    //constructor
    public CreateProfileActivityUITest() {
        super(CreateProfileActivity.class);
    }

    //setup global variables
    public void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        username = (EditText) activity.findViewById(R.id.createprofile_username_et);
        email = (EditText) activity.findViewById(R.id.createprofile_email_et);
    }

    //createProfile(name,em) fills in the input text field and clicks on 'save' button
    public void createProfile(String name, String em){
        assertNotNull(activity.findViewById(R.id.createprofile_save_button));
        username.setText(name);
        email.setText(em);
        ((Button) activity.findViewById(R.id.createprofile_save_button)).performClick();
    }

    @UiThreadTest
    public void testCreateProfile() {
        Controller controller = (Controller) activity.getApplicationContext();
        User testUser = new User("admin","admin@123.com");

        controller.createUser(testUser.getName(),testUser.getEmail());

        /* test empty name and email field */
        createProfile("","");
        // make sure the activity is still running
        assertFalse(activity.isFinishing());

        /* test empty name and email field */
        createProfile("name here","");
        // make sure the activity is still running
        assertFalse(activity.isFinishing());

        /* test empty name field */
        createProfile("","email here");
        // make sure the activity is still running
        assertFalse(activity.isFinishing());

        /* click on button if the user is already there */
        createProfile(testUser.getName(), testUser.getEmail());
        //check this activity is still running
        assertFalse(activity.isFinishing());

        /* test when username is not in use */
        //make sure this is no such user
        User u = controller.getUserByName(testUser.getName());
        controller.deleteUserById(u.getId());
        assertNull(controller.getUserByName(testUser.getName()));
        // create a new profile, click on profile button
        createProfile(testUser.getName(), testUser.getEmail());
        //check the user is in userlist now
        assertNotNull(controller.getUserByName(testUser.getName()));
        //make sure this activity ends already
        assertTrue(activity.isFinishing());

        //clear the test data: delete the user
        User n = controller.getUserByName(testUser.getName());
        controller.deleteUserById(n.getId());
    }

    @UiThreadTest
    public void testCancelButton(){

        assertFalse(activity.isFinishing());
        ((Button) activity.findViewById(R.id.createprofile_cancel_button)).performClick();

        //check if this activity ends
        assertTrue(activity.isFinishing());


    }
}

