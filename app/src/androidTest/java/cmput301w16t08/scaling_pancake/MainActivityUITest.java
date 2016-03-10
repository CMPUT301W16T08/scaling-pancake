package cmput301w16t08.scaling_pancake;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Aaron on 3/2/2016.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    EditText username;

    public MainActivityUITest() {
        super(MainActivity.class);
    }

    //setup global variables
    public void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        username = (EditText) activity.findViewById(R.id.startscreen_username_et);
    }

    //login(name) fills in the input text field and clicks on 'login' button
    public void login(String name){
        assertNotNull(activity.findViewById(R.id.startscreen_login_button));
        username.setText(name);
        ((Button) activity.findViewById(R.id.startscreen_login_button)).performClick();
    }

    @UiThreadTest
    public void testLogin(){
        Controller controller = (Controller) activity.getApplicationContext();

        // login with an unknown username
        login("admin");
        //TODO: check toast

        // login with a correct username
        controller.createUser("admin", "email here");
        login("admin");
        //TODO: check we are switched to MenuActivity
        // i don't know how to do this still
    }

    //TODO: test createProfile button
    @UiThreadTest
    public void testCreateProfile(){
        Controller controller = (Controller) activity.getApplicationContext();

        // click on createProfile button
        ((Button) activity.findViewById(R.id.startscreen_createprofile_button)).performClick();

        //TODO: check if another activity is launched
    }
}
