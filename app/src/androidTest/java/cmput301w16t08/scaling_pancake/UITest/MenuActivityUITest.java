package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.AddInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.SearchInstrumentsActivity;
import cmput301w16t08.scaling_pancake.activities.ViewProfileActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Created by Aaron on 3/10/2016.
 */
public class MenuActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;

    public MenuActivityUITest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(),getActivity());
        Controller controller = (Controller)getActivity().getApplicationContext();

        //create a user
        if (! controller.createUser("admin","e1@123.com")){
            controller.deleteUserById(controller.getUserByName("admin").getId());
            controller.createUser("admin", "e1@123.com");
        }
        //login user
        controller.login("admin");
        solo.enterText((EditText) solo.getView(R.id.startscreen_username_et),"admin");
        solo.clickOnView(solo.getView(R.id.startscreen_login_button));

    }

    @Override
    public void tearDown(){
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

    public void testNotificationButton(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        // go back and make a bid
        solo.clickOnView(solo.getView(R.id.menu_back_button));
        bidOnOneInsturment(controller);

        //login user
        solo.enterText((EditText) solo.getView(R.id.startscreen_username_et), "admin2");
        solo.clickOnView(solo.getView(R.id.startscreen_login_button));

        solo.clickOnView(solo.getView(R.id.new_bid_notif_button));
        solo.assertCurrentActivity("should have switched to instrumentlist",InstrumentListActivity.class);
    }

    public void testBackButton(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        //click on viewProfile
        solo.clickOnView(solo.getView(R.id.menu_back_button));

        //check if activity ends
       solo.assertCurrentActivity("should have gone back to start",MainActivity.class);

        //check if user logout
        assertNull(controller.getCurrentUser());
    }

    private void bidOnOneInsturment(Controller controller) {
        controller.createUser("admin2","test");

        // give admin2 an instrument
        controller.logout();
        controller.login("admin2");
        controller.addInstrument("test instrument","test instrument");
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        String instrumentDescription = instrument.getDescription();

        // admin bid on admin2's instrument
        controller.logout();
        controller.login("admin");
        controller.makeBidOnInstrument(instrument, 10);

        controller.logout();
        controller.login("admin2");
    }
}
