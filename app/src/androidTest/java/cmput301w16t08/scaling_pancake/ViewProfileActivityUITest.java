package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.activities.EditProfileActivity;
import cmput301w16t08.scaling_pancake.activities.ViewProfileActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Created by dan on 10/02/16.
 */
public class ViewProfileActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;

    public ViewProfileActivityUITest() {
        super(ViewProfileActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());

        // login
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.createUser("admin","admin@test.com");
        controller.login("admin");
    }

    @Override
    public void tearDown() throws Exception{
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.deleteUser();
    }
    // login as admin
    public void login(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        controller.createUser("admin", "admin@test.com");
        controller.login("admin");
    }

    /* test edit button */
    public void testEditButton(){

        // click on button
        solo.clickOnView(solo.getView(R.id.edit_profile_button));

        //check if we start EditProfileActivity
        solo.assertCurrentActivity("failed to start EditProfileActivity", EditProfileActivity.class);
    }

    @UiThreadTest
    public void testTextField() {
        Controller controller = (Controller) getActivity().getApplicationContext();

        Intent intent = new Intent();

        setActivityIntent(intent);

        ViewProfileActivity vpa = (ViewProfileActivity) getActivity();

        /* Test visibility of username and email fields */
        View origin = vpa.getWindow().getDecorView();
        TextView usernameTV = (TextView) origin.findViewById(R.id.view_profile_username_tv);
        TextView emailTV = (TextView) origin.findViewById(R.id.view_profile_email_tv);

        ViewAsserts.assertOnScreen(origin, usernameTV);
        ViewAsserts.assertOnScreen(origin, emailTV);

        /* Test correctness */
        assertEquals(usernameTV.getText().toString(), controller.getCurrentUser().getName());
        assertEquals(emailTV.getText().toString(), controller.getCurrentUser().getEmail());
    }


    @UiThreadTest
    public void testMenuButton(){
        ((Button)getActivity().findViewById(R.id.view_profile_back_button)).performClick();

        // make sure the activity is finished
        assertTrue(getActivity().isFinishing());
    }
}
