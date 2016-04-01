package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;


public class BidListActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User first;
    User second;
    Instrument instrument;

    public BidListActivityUITest() {
        super(MenuActivity.class);
    }

    @Override
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
        controller = (Controller) getActivity().getApplicationContext();

        //create first user
        controller.createUser("admin","e1@123.com");
        first = controller.getUserByName("admin");
        //create second user
        controller.createUser("admin2", "e2@123.com");
        second = controller.getUserByName("admin2");
        //login first user
        controller.login(first.getName());
        //add an instrument to first user
        controller.addInstrument("instrument1", "instrument for test");
        first = controller.getUserByName("admin");
        instrument = first.getOwnedInstruments().getInstrument(0);
    }

    @Override
    public void tearDown(){
        // delete users
        controller.deleteUserById(first.getId());
        controller.deleteUserById(second.getId());
        // finish current activities
        solo.finishOpenedActivities();
    }

    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnText("instrument for test");
        solo.clickOnButton(solo.getString(R.string.view_bids));
    }

    private void secondBidOnFirst(){
        controller.logout();
        controller.login(second.getName());
        controller.makeBidOnInstrument(instrument,10);

        controller.logout();
        controller.login(first.getName());
    }


    /* test instrument with no bids */
    public void testWithNoBids(){
        moveToActivity();

        assertTrue(solo.searchText(solo.getString(R.string.nothing_to_show)));
    }

    /* test instrument with bids */
    public void testWithBids(){
        secondBidOnFirst();
        moveToActivity();

        //click on accept bid
        solo.clickOnText(solo.getString(R.string.accept_bid));
        // test if the status changes
        //TODO: this does not change?????
        assertEquals(controller.getInstrumentById(instrument.getId()).getStatus(),"borrowed");
    }

    /* test back button */
    public void testBackButton(){
        moveToActivity();

        //click on back button
        solo.clickOnButton(solo.getString(R.string.back));
        // test if we are switched back
        solo.assertCurrentActivity("we should have go back to view instrument activity", ViewInstrumentActivity.class);
    }
}
