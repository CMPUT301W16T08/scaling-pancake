package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.activities.AddInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.SearchInstrumentsActivity;
import cmput301w16t08.scaling_pancake.activities.ViewProfileActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Created by Aaron on 3/10/2016.
 */
public class MenuActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;

    public MenuActivityUITest() {
        super(MenuActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
        Controller controller = (Controller)getActivity().getApplicationContext();
        controller.createUser("admin", "admin@test");
        controller.login("admin");

    }

    @Override
    public void tearDown() throws Exception{
        Controller controller = (Controller)getActivity().getApplicationContext();
        controller.deleteUserById(controller.getUserByName("admin").getId());
        solo.finishOpenedActivities();
    }


    public void testViewProfileButton(){
        //click on viewProfile
        solo.clickOnView(solo.getView(R.id.menu_view_profile_button));

        // check if switched to ViewProfileAcitivity
        solo.assertCurrentActivity("not switched to ViewProfileAcitivity", ViewProfileActivity.class);
    }


    public void testAddInstrumentsButton(){
        //click on viewProfile
        solo.clickOnView(solo.getView(R.id.menu_add_instruments_button));

        // check if switched to AddInstrumentActivity
        solo.assertCurrentActivity("not switched to ViewProfileAcitivity", AddInstrumentActivity.class);
    }


    public void testViewInstrumentsButton(){
        //click on viewProfile
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));

        // check if switched to InstrumentListActivity
        solo.assertCurrentActivity("not switched to ViewProfileAcitivity", InstrumentListActivity.class);
    }


    public void testSearchInstrumentsButton(){
        //click on viewProfile
        solo.clickOnView(solo.getView(R.id.menu_search_instruments_button));

        // check if switched to SearchInstrumentsActivity
        solo.assertCurrentActivity("not switched to ViewProfileAcitivity", SearchInstrumentsActivity.class);
    }

    @UiThreadTest
    public void testBackButton(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        //click on viewProfile
        ((Button)getActivity().findViewById(R.id.menu_back_button)).performClick();

        //check if activity ends
        assertTrue(getActivity().isFinishing());

        //check if user logout
        assertNull(controller.getCurrentUser());
    }
}
