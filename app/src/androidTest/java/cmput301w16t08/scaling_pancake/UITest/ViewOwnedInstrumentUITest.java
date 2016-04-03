package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.BidListActivity;
import cmput301w16t08.scaling_pancake.activities.EditInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewBidActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.BidList;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 3/25/2016.
 */
public class ViewOwnedInstrumentUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User user;

    public ViewOwnedInstrumentUITest() {
        super(MenuActivity.class);
    }


    @Override
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
        controller = (Controller) getActivity().getApplicationContext();

        //create a user
        if (! controller.createUser("admin","e1@123.com")){
            controller.deleteUserById(controller.getUserByName("admin").getId());
            controller.createUser("admin", "e1@123.com");
        }
        //login user and add an instrument
        user = controller.getUserByName("admin");
        controller.login(user.getName());
        controller.addInstrument("test instrument", "test instrument description");


    }

    @Override
    public void tearDown(){
        // delete user
        controller.deleteUserById(user.getId());

        solo.finishOpenedActivities();
    }

    /* test back button */
    public void testBackButton(){
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
        moveToActivity();

        Instrument instrument = controller.getCurrentUser().getOwnedInstruments().getInstrument(0);

        /* test the instrument name */
        TextView nameTV = (TextView) solo.getView(R.id.owned_instrument_view_name_tv);
        assertEquals(instrument.getName(), nameTV.getText().toString());

        /* test the status is right */
        TextView statusTV = (TextView) solo.getView(R.id.owned_instrument_view_status_tv);
        assertEquals("Status:"+instrument.getStatus(), statusTV.getText().toString());

        /* test the description is right */
        TextView descriptionTV = (TextView) solo.getView(R.id.owned_instrument_view_description_tv);
        assertEquals("Description:"+instrument.getDescription(), descriptionTV.getText().toString());
    }

    /* test edit button */
    public void testEditButton(){
        moveToActivity();
        // test if we are in the right activity
        solo.assertCurrentActivity("we are in the wrong activity", ViewInstrumentActivity.class);

        solo.clickOnButton(solo.getString(R.string.edit));
        // test if we are in the next activity
        solo.assertCurrentActivity("we should have switch to edit instrument acitivity", EditInstrumentActivity.class);
    }

    /* test view bids button */
    public void testViewBidsButton(){
        moveToActivity();
        // test if we are in the right activity
        solo.assertCurrentActivity("we are in the wrong activity", ViewInstrumentActivity.class);

        solo.clickOnButton(solo.getString(R.string.view_bids));
        // test if we are in the next activity
        solo.assertCurrentActivity("we should have switch to BidList acitivity", BidListActivity.class);
    }

    //TODO: test photo I don't know how to test if a photo is the one we desired
    //TODO: test play sample button: I don't know how to test if a sound is the sound we want to hear
    public void testPlaySampleButton(){
        moveToActivity();

        solo.clickOnView(solo.getView(R.id.play_sample_button));
    }
    // move from the MenuActivity to ViewOwnedInstrument
    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickInList(1);
    }

}
