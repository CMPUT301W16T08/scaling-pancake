package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 3/25/2016.
 */
public class ViewLentInstrumentUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User first;
    User second;
    Instrument instrument;

    public ViewLentInstrumentUITest() {
        super(MenuActivity.class);
    }

    @Override
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
        controller = (Controller) getActivity().getApplicationContext();

        //create users
        createUsers();

        controller.login(second.getName());
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
        firstBorrowSecond();
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
        firstBorrowSecond();
        moveToActivity();

        /* test the instrument name */
        TextView nameTV = (TextView) solo.getView(R.id.brwd_by_others_instrument_view_name_tv);
        assertEquals(instrument.getName(), nameTV.getText().toString());

        /* test the borrower's name */
        TextView borrowerNameTV = (TextView) solo.getView(R.id.brwd_by_others_instrument_view_borrower_tv);
        assertEquals("Borrower:" + first.getName(), borrowerNameTV.getText().toString());

        /* test the description is right */
        TextView descriptionTV = (TextView) solo.getView(R.id.brwd_by_others_instrument_view_description_tv);
        assertEquals("Description:" + instrument.getDescription(), descriptionTV.getText().toString());

        /* test the bid rate displayed */
        TextView rateTV = (TextView) solo.getView(R.id.brwd_by_others_instrument_view_rate_tv);
        assertEquals("Rate:10.00/hr", rateTV.getText().toString());

    }

    /* test return button */
    public void testReturnButton(){
        firstBorrowSecond();
        moveToActivity();
        /* click on it when borrower not returned yet */
        solo.clickOnButton(solo.getString(R.string.set_returned));
        //reassign the instrument
        second = controller.getUserByName("admin2");
        instrument = second.getOwnedInstruments().getInstrument(0);
        //test current activity and insturment status
        assertEquals("borrowed", instrument.getStatus());

        /* click on it when borrower returned */
        solo.clickOnButton(solo.getString(R.string.back));
        returnInstrument();
        solo.clickInList(1);
        solo.clickOnButton(solo.getString(R.string.set_returned));
        //reassign the instrument
        second = controller.getUserByName("admin2");
        instrument = controller.getInstrumentById(instrument.getId());
        //test current activity and insturment status
        assertEquals("available", instrument.getStatus());
    }


    //TODO: test photo I don't know how to test if a photo is the one we desired
    //TODO: test play sample button: I don't know how to test if a sound is the sound we want to hear
    public void testPlaySampleButton(){
        firstBorrowSecond();
        moveToActivity();

        solo.clickOnView(solo.getView(R.id.play_sample_button));
    }

    // move from the MenuActivity to ViewOwnedInstrument
    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Lended Out");
        solo.clickOnText(instrument.getName());
    }

    private void firstBorrowSecond() {
        // give second an instrument
        controller.addInstrument("test instrument", "test instrument");
        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);

        // first bid on second's instrument
        controller.logout();
        controller.login(first.getName());
        controller.makeBidOnInstrument(instrument, 10);


        // second accepted the bid
        controller.logout();
        controller.login(second.getName());
        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        controller.acceptBidOnInstrument(instrument.getBids().getBid(0));
        //TODO: add a location here
    }

    private void returnInstrument(){
        // let first user return the instrument
        controller.logout();
        controller.login(first.getName());
        controller.returnInstrument(instrument);

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
