package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.CreateProfileActivity;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 3/2/2016.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2 {
    EditText username;
    Solo solo;

    public MainActivityUITest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        solo = new Solo(getInstrumentation(),getActivity());
        username = (EditText) solo.getCurrentActivity().findViewById(R.id.startscreen_username_et);
    }

    @Override
    public void tearDown() throws Exception{
        //tearDown() is run after a test case has finished.
        solo.finishOpenedActivities();
    }

    //login(name) fills in the input text field and clicks on 'login' button
    public void login(String name){
        assertNotNull(solo.getView(R.id.startscreen_login_button));
        solo.enterText(username,name);
        solo.clickOnView(solo.getView(R.id.startscreen_login_button));
    }

    // sign up with a username and email, by click on views
    public void signUp(String name, String em){
        solo.clickOnView(solo.getView(R.id.startscreen_createprofile_button));
        solo.enterText((EditText) solo.getView(R.id.createprofile_username_et), name);
        solo.enterText((EditText) solo.getView(R.id.createprofile_email_et),em);
        solo.clickOnView(solo.getView(R.id.createprofile_save_button));
    }

    /* test for login with no profile */
    public void testLoginWithoutAccount(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        // make sure the name 'admin' is not used
        User u = controller.getUserByName("admin");
        if(u != null){
            controller.deleteUserById(u.getId());
        }

        // login
        login("admin");

        // test we are still at this activity
        solo.assertCurrentActivity("we are not at same activity", MainActivity.class);

        // login with a correct username
        signUp("admin", "admin@test");

        // check we are switched to MenuActivity
        solo.assertCurrentActivity("not swithch to MenuActivity", MenuActivity.class);
    }

    /* test for login with right username */
    public void testLoginWithAccount(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* login with an unknown username*/
        // make sure the name 'admin' is
        User u = controller.getUserByName("admin");
        if(u == null){
            controller.createUser("admin","admin@test");
        }

        // login
        login("admin");

        //check we are switched to MenuActivity
        solo.assertCurrentActivity("not swithch to MenuActivity", MenuActivity.class);
    }


    /* test for clieck on createProfile button */
    public void testCreateProfile(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        // click on createProfile button
        solo.clickOnView(solo.getView(R.id.startscreen_createprofile_button));

        // check if another activity is launched
        solo.assertCurrentActivity("CreateProfileActivity did not launch", CreateProfileActivity.class);
    }
}
