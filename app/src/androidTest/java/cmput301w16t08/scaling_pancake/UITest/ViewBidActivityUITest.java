package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.BidListActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewBidActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 4/1/2016.
 */
public class ViewBidActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User first;
    User second;
    Instrument instrument;

    public ViewBidActivityUITest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
        controller = (Controller) getActivity().getApplicationContext();

        //create users
        createUsers();
        controller.login(second.getName());
        solo.enterText((EditText) solo.getView(R.id.startscreen_username_et), second.getName());
        solo.clickOnView(solo.getView(R.id.startscreen_login_button));

        // this is just to wait for the data updated....
        controller.login(second.getName());
        controller.login(second.getName());
        controller.login(second.getName());
        controller.login(second.getName());
        controller.login(second.getName());
        controller.login(second.getName());
        controller.login(second.getName());

        firstBidSecond();


    }

    @Override
    public void tearDown(){
        // delete user
        controller.deleteUserById(first.getId());
        controller.deleteUserById(second.getId());

        solo.finishOpenedActivities();
    }

    /* test back button */
    public void testBackButton(){
        moveToActivity();
        // test if we are in the right activity
        solo.assertCurrentActivity("we are in the wrong activity", ViewBidActivity.class);
        // click on back button
        solo.clickOnButton(solo.getString(R.string.back));
        // test if we are back to previous activity
        solo.assertCurrentActivity("we should have go back", BidListActivity.class);
    }

    /* test name and description displayed is the right one */
    public void testDisplay(){
        moveToActivity();

        /* test the instrument name */
        TextView nameTV = (TextView) solo.getView(R.id.view_bid_activity_name_tv);
        assertEquals("Instrument: " + instrument.getName(), nameTV.getText().toString());

        /* test the bidder's name */
        TextView bidderNameTV = (TextView) solo.getView(R.id.view_bid_activity_bidder_tv);
        assertEquals("Bidder: " + first.getName(), bidderNameTV.getText().toString());

        /* test the bid rate displayed */
        TextView rateTV = (TextView) solo.getView(R.id.view_bid_activity_rate_tv);
        assertEquals("Rate: 10.0", rateTV.getText().toString());
    }

    /* test location button */
    public void testReturnButton(){
        moveToActivity();

        solo.clickOnButton(solo.getString(R.string.choose_location));

        //TODO: not sure how to test if we are in google map..
    }

    /* test accept bid button */
    public void testAcceptButton(){
        moveToActivity();

        /* test click without a specify a pick up location */
        solo.clickOnButton(solo.getString(R.string.accept_bid));
        solo.assertCurrentActivity("we should have stayed in the same activity", ViewBidActivity.class);

        /* test click after having a location */
        LatLng location = new LatLng(10,10);
        controller.setLocationForInstrument(controller.getInstrumentById(instrument.getId()),location);
        solo.clickOnButton(solo.getString(R.string.accept_bid));
        solo.assertCurrentActivity("we should have go back", BidListActivity.class);
    }

    /* test decline bid button */
    public void testDeclineButton(){
        moveToActivity();

        solo.clickOnButton(solo.getString(R.string.decline_bid));
        solo.assertCurrentActivity("should have go back after decline",BidListActivity.class);
    }

    // move from the MenuActivity to ViewOwnedInstrument
    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnText(instrument.getName());
        solo.clickOnButton(solo.getString(R.string.view_bids));
        solo.clickInList(1);
    }

    private void firstBidSecond() {
        // give second an instrument
        controller.addInstrument("test instrument", "test instrument");
        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);

        // first bid on second's instrument
        controller.logout();
        controller.login(first.getName());
        controller.makeBidOnInstrument(controller.getInstrumentById(instrument.getId()), 10);

        // log back in second user
        controller.logout();
        controller.login(second.getName());
    }


    private void createUsers(){
        //create first user
        if (! controller.createUser("admin","e1@123.com")){
            controller.deleteUserById(controller.getUserByName("admin").getId());
            controller.createUser("admin", "e1@123.com");
        }
        first = controller.getUserByName("admin");

        //create second user
        if (! controller.createUser("admin2", "e2@123.com")){
            controller.deleteUserById(controller.getUserByName("admin2").getId());
            controller.createUser("admin2", "e2@123.com");
        }
        second = controller.getUserByName("admin2");
    }
}
