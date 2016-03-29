package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.EditProfileActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Created by dan on 10/02/16.
 */
public class ViewProfileActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;

    public ViewProfileActivityUITest() {
        super(MenuActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());

        // login
        Controller controller = (Controller) this.getInstrumentation().getTargetContext().getApplicationContext();
        controller.createUser("admin","admin@test.com");
        controller.login("admin");

        solo.clickOnView(solo.getView(R.id.menu_view_profile_button));
    }

    @Override
    public void tearDown() throws Exception{
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.deleteUserById(controller.getUserByName("admin").getId());

        solo.finishOpenedActivities();
    }

    /* test edit button */
    public void testEditButton(){

        // click on button
        solo.clickOnView(solo.getView(R.id.edit_profile_button));

        //check if we start EditProfileActivity
        solo.assertCurrentActivity("failed to start EditProfileActivity", EditProfileActivity.class);
    }

    public void testTextField() {
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* Test visibility of username and email fields */
        TextView usernameTV = (TextView) solo.getView(R.id.view_profile_username_tv);
        TextView emailTV = (TextView) solo.getView(R.id.view_profile_email_tv);

        /* Test correctness */
        assertEquals(usernameTV.getText().toString(), "Username:"+controller.getCurrentUser().getName());
        assertEquals(emailTV.getText().toString(), "Email:"+controller.getCurrentUser().getEmail());
    }



    public void testMenuButton(){
        solo.clickOnView(solo.getView(R.id.view_profile_back_button));

        // make sure the activity is finished
        solo.assertCurrentActivity("did not go back to menu", MenuActivity.class);
    }
}
