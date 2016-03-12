package cmput301w16t08.scaling_pancake;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import Activites.CreateProfileActivity;


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


        //TODO: i need arrayadapter here
        createProfile("admin","admin@123.com");
        //TODO: check userlist is increated by 1

        //TODO: check the last element in userlist is the one we created

        //TODO: check the if the activity changes back to Mainactivity ????
        //assertEquals(getActivity() ,MainActivity.class);

        createProfile("admin", "admin@123.com");
        //TODO: check no element is added to userlist

        //TODO: check if there is a toast popup ????????
    }

    @UiThreadTest
    public void testCancelButton(){


        ((Button) activity.findViewById(R.id.createprofile_cancel_button)).performClick();

        //check if the is changed back to MainActivity
        assertTrue(activity.isFinishing());

    }
}

