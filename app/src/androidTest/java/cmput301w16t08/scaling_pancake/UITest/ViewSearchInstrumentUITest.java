package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.DisplaySearchResultsActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 3/25/2016.
 */
public class ViewSearchInstrumentUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User first;
    User second;
    Instrument instrument;

    public ViewSearchInstrumentUITest() {
        super(MenuActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
        controller = (Controller) getActivity().getApplicationContext();

        //create users
        createUsers();
        // give first user an instrument
        controller.login(first.getName());
        controller.addInstrument("test instrument", "instrument for ViewSearchInstrumentUITest");
        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);

        controller.logout();
        controller.login(second.getName());
    }


    @Override
    public void tearDown() {
        // delete user
        controller.deleteUserById(first.getId());
        controller.deleteUserById(second.getId());

        solo.finishOpenedActivities();
    }

    /* test back button */
    public void testBackButton() {
        moveToActivity();
        // test if we are in the right activity
        solo.assertCurrentActivity("we are in the wrong activity", ViewInstrumentActivity.class);
        // click on back button
        solo.clickOnButton(solo.getString(R.string.back));
        // test if we are back to previous activity
        solo.assertCurrentActivity("we should have go back", DisplaySearchResultsActivity.class);
    }

    /* test name and description displayed is the right one */
    public void testDisplay() {
        moveToActivity();

        /* test the instrument name */
        TextView nameTV = (TextView) solo.getView(R.id.searched_instrument_view_name_tv);
        assertEquals(instrument.getName(), nameTV.getText().toString());

        /* test the owner's name */
        TextView borrowerNameTV = (TextView) solo.getView(R.id.searched_instrument_view_owner_tv);
        assertEquals("Owner: " + first.getName(), borrowerNameTV.getText().toString());

        /* test the description is right */
        TextView descriptionTV = (TextView) solo.getView(R.id.searched_instrument_view_description_tv);
        assertEquals("Description: " + instrument.getDescription(), descriptionTV.getText().toString());

        /* test the status displayed */
        TextView statusTV = (TextView) solo.getView(R.id.searched_instrument_view_status_tv);
        assertEquals("Status: "+instrument.getStatus(), statusTV.getText().toString());

    }

    /* test makeBid button */
    public void testMakeBidButton() {
        moveToActivity();
        EditText bidamount = (EditText) solo.getView(R.id.searched_instrument_view_bidamount_et);

        /* test when not enter bid amount */
        solo.clickOnButton(solo.getString(R.string.make_bid));
        solo.assertCurrentActivity("should have stayed in the same activity",ViewInstrumentActivity.class);

        instrument = controller.getInstrumentById(instrument.getId());
        assertEquals(0, instrument.getBids().size());

        /* test when entered bid amount */
        solo.enterText(bidamount, "10");
        solo.clickOnButton(solo.getString(R.string.make_bid));
        solo.assertCurrentActivity("should have switched back to search result",DisplaySearchResultsActivity.class);

        instrument = controller.getInstrumentById(instrument.getId());
        assertEquals(1, instrument.getBids().size());

    }


    //TODO: test photo I don't know how to test if a photo is the one we desired
    //TODO: test play sample button: I don't know how to test if a sound is the sound we want to hear
    public void testPlaySampleButton() {
        moveToActivity();

        solo.clickOnView(solo.getView(R.id.play_sample_button));
    }

    // move from the MenuActivity to ViewOwnedInstrument
    private void moveToActivity() {
        solo.clickOnView(solo.getView(R.id.menu_search_instruments_button));

        EditText searchBox = (EditText) solo.getView(R.id.search_instrument_et);
        solo.enterText(searchBox, instrument.getName());
        solo.clickOnView(solo.getView(R.id.search_instrument_search_button));
        solo.clickOnText(instrument.getDescription());
    }



    private void createUsers() {
        //create first user
        if (!controller.createUser("admin", "e1@123.com")) {
            controller.deleteUserById(controller.getUserByName("admin").getId());
            controller.createUser("admin", "e1@123.com");
        }
        first = controller.getUserByName("admin");

        //create second user
        if (!controller.createUser("admin2", "e2@123.com")) {
            controller.deleteUserById(controller.getUserByName("admin2").getId());
            controller.createUser("admin2", "e2@123.com");
        }
        second = controller.getUserByName("admin2");
    }
}
