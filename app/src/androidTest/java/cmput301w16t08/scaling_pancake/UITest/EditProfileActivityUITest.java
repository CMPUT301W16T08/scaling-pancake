package cmput301w16t08.scaling_pancake.UITest;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.EditProfileActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Created by Aaron on 3/12/2016.
 */
public class EditProfileActivityUITest extends ActivityInstrumentationTestCase2{
    Instrumentation instrumentation;
    Activity activity;
    EditText username;
    EditText email;
    Solo solo;

    public EditProfileActivityUITest(){
        super(EditProfileActivity.class);
    }

    public void setUp() throws Exception{
        //super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        username = (EditText) activity.findViewById(R.id.edit_profile_username_et);
        email = (EditText) activity.findViewById(R.id.edit_profile_email_et);
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void tearUp(){
        solo.finishOpenedActivities();
    }

    // login as admin
    public void login(){
        Controller controller = (Controller) activity.getApplicationContext();

        controller.createUser("admin","admin@test.com");
        controller.login("admin");
    }


    @UiThreadTest
    public void testChangeName(){
        Controller controller = (Controller) activity.getApplicationContext();

        // login first
        login();

        // fill in new name
        username.setText("NewAdmin");

        // click on saveChanges button
        ((Button) activity.findViewById(R.id.edit_profile_save_button)).performClick();

        // make sure activity ends
        assertTrue(activity.isFinishing());

        // check if the name is changed
        assertEquals("NewAdmin", controller.getCurrentUser().getName());

        // delete this user
        controller.deleteUser();
    }

    @UiThreadTest
    public void testChangeEmail(){
        Controller controller = (Controller) activity.getApplicationContext();

        // login first
        login();

        // fill in new name
        email.setText("newemail@test");

        // click on saveChanges button
        ((Button) activity.findViewById(R.id.edit_profile_save_button)).performClick();

        // make sure activity ends
        assertTrue(activity.isFinishing());

        // check if the name is changed
        assertEquals("newemail@test", controller.getCurrentUser().getEmail());

        // delete this user
        controller.deleteUser();
    }

    @UiThreadTest
    public void testChangeNothingOrBoth(){
        Controller controller = (Controller) activity.getApplicationContext();

        // login first
        login();

        /* change nothing */
        ((Button) activity.findViewById(R.id.edit_profile_save_button)).performClick();

        // make sure activity does NOT end
        assertFalse(activity.isFinishing());

        /* change both */
        username.setText("NewAdmin");
        email.setText("newemail@test");

        // click on saveChanges button
        ((Button) activity.findViewById(R.id.edit_profile_save_button)).performClick();

        // make sure activity ends
        assertTrue(activity.isFinishing());

        // check if username and email changed
        assertEquals("NewAdmin",controller.getCurrentUser().getName());
        assertEquals("newemail@test",controller.getCurrentUser().getEmail());

        // delete test user
        controller.deleteUser();

    }

    @UiThreadTest
    public void testCancel(){
        Controller controller = (Controller) activity.getApplicationContext();

        // login first
        login();
        String oldEmail = controller.getCurrentUser().getEmail();
        //change both email and username
        username.setText("NewAdmin");
        email.setText("newemail@test");

        // click on CANCEL button
        ((Button) activity.findViewById(R.id.edit_profile_cancel_button)).performClick();

        // make sure activity ends
        assertTrue(activity.isFinishing());

        // make sure username and email does NOT change
        assertEquals("admin",controller.getCurrentUser().getName());
        assertEquals(oldEmail,controller.getCurrentUser().getEmail());

        // delete test user
        controller.deleteUser();
    }
}
