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

    public MainActivityUITest(Class activityClass) {
        super(activityClass);
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

        login("admin");
        //TODO: check toast

        controller.createUser("admin","email here");
        login("admin");
        //TODO: check we are switched to MenuActivity
    }

    //TODO: test createProfile button
}
