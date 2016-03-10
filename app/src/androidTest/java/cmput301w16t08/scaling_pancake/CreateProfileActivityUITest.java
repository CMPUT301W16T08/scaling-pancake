package cmput301w16t08.scaling_pancake;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;




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

        /* click on button if the user is already there */
        createProfile(testUser.getName(), testUser.getEmail());
        //TODO: check no element is added to userlist

        //check this activity is still running
        assertFalse(activity.isFinishing());

        //make sure this is no such user
        controller.getUserList().removeUser(controller.getUserList().size()-1);
        //assertFalse(controller.getUserList().containsUser(testUser));

        // create a new profile, click on profile button
        createProfile(testUser.getName(), testUser.getEmail());

        //check the user is in userlist now
        //assertTrue(controller.getUserList().containsUser(testUser));

        //make sure this activity ends already
        assertTrue(activity.isFinishing());


    }

    @UiThreadTest
    public void testCancelButton(){

        assertFalse(activity.isFinishing());
        ((Button) activity.findViewById(R.id.createprofile_cancel_button)).performClick();
        //check if this activity ends
        assertTrue(activity.isFinishing());

        // button in MainActivity:
        boolean flag = true;
        /*while(flag){
            Button loginButton = (Button) getActivity().findViewById(R.id.startscreen_login_button);
            if(loginButton != null){
                flag = false;
            }
        }*/

        //assertTrue(loginButton.isShown());

    }
}

