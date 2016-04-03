package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.BidListActivity;
import cmput301w16t08.scaling_pancake.activities.EditInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 3/25/2016.
 */
public class ViewMyBidInstrumentUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User first;
    User second;
    User third;
    Instrument instrument;

    public ViewMyBidInstrumentUITest() {
        super(MenuActivity.class);
    }

    @Override
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
        controller = (Controller) getActivity().getApplicationContext();

        //create users
        createUsers();

        controller.login(first.getName());
        controller.addInstrument("instrument1", "instrument for test");
        // re-assign first user
        first = controller.getUserByName("admin");
        instrument = first.getOwnedInstruments().getInstrument(0);

        controller.logout();
        controller.login(second.getName());
    }


    @Override
    public void tearDown(){
        // delete user
        controller.deleteUserById(first.getId());
        controller.deleteUserById(second.getId());
        controller.deleteUserById(third.getId());

        solo.finishOpenedActivities();
    }

    /* test back button */
    public void testBackButton(){
        secondBidOnFirst();
        moveToActivity();
        // test if we are in the right activity
        solo.assertCurrentActivity("we are in the wrong activity", ViewInstrumentActivity.class);
        // click on back button
        solo.clickOnButton(solo.getString(R.string.back));
        // test if we are back to previous activity
        solo.assertCurrentActivity("we should have go back", InstrumentListActivity.class);
    }

    /* test name and description displayed is the right one */
    public void testDisplay(){
        secondBidOnFirst();
        moveToActivity();

        /* test the instrument name */
        TextView nameTV = (TextView) solo.getView(R.id.mybids_instrument_view_name_tv);
        assertEquals(instrument.getName(), nameTV.getText().toString());

        /* test the owner's name */
        TextView ownerNameTV = (TextView) solo.getView(R.id.mybids_instrument_view_owner_tv);
        assertEquals("Owner:" + first.getName(), ownerNameTV.getText().toString());

        /* test the description is right */
        TextView descriptionTV = (TextView) solo.getView(R.id.mybids_instrument_view_description_tv);
        assertEquals("Description:" + instrument.getDescription(), descriptionTV.getText().toString());

        /* test the bid amount displayed */
        TextView maxBidTV = (TextView) solo.getView(R.id.mybids_instrument_view_maxbid_tv);

        // when my bid is the highest
        assertEquals("Current Max Bid:10.0/hr  (Me)", maxBidTV.getText().toString());

        // when my bid is not the highest
        // make a higher bid
        solo.clickOnButton(solo.getString(R.string.back));
        solo.clickOnText(solo.getString(R.string.back));
        thirdBidOnFirst();
        moveToActivity();
        // TODO: not sure why this is happening: java.lang.IndexOutOfBoundsException

        // test
        assertEquals("Current Max Bid:15.0/hr  (" + third.getName() + ")", maxBidTV.getText().toString());

    }

    /* test BidAgain button */
    public void testBidAgainButton(){
        secondBidOnFirst();
        moveToActivity();
        // test if we are in the right activity
        solo.assertCurrentActivity("we are in the wrong activity", ViewInstrumentActivity.class);

        solo.clickOnButton(solo.getString(R.string.bid_again));
        // test if we are in the next activity
        //TODO not sure what will happen after click on bid again
        //solo.assertCurrentActivity("we should have switch to edit instrument acitivity", EditInstrumentActivity.class);
    }

    //TODO: test photo I don't know how to test if a photo is the one we desired
    //TODO: test play sample button: I don't know how to test if a sound is the sound we want to hear
    public void testPlaySampleButton(){
        secondBidOnFirst();
        moveToActivity();

        solo.clickOnView(solo.getView(R.id.play_sample_button));
    }
    // move from the MenuActivity to ViewOwnedInstrument
    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("My Bids");
        solo.clickOnText(instrument.getName());
    }

    private void secondBidOnFirst(){
        controller.makeBidOnInstrument(instrument, 10);
        first = controller.getUserByName("admin");
        instrument = first.getOwnedInstruments().getInstrument(0);
    }

    private void thirdBidOnFirst(){
        controller.logout();
        controller.login(third.getName());
        controller.makeBidOnInstrument(instrument,15);

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

        //create third user
        if (! controller.createUser("admin3", "e3@123.com")){
            controller.deleteUserById(controller.getUserByName("admin3").getId());
            controller.createUser("admin3", "e3@123.com");
        }
        third = controller.getUserByName("admin3");
    }
}
