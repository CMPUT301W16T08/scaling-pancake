package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Created by Aaron on 3/12/2016.
 */
public class InstrumentListActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;

    public InstrumentListActivityUITest(){
        super(MainActivity.class);
    }

    @Override
    // set this up before each test
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());

        //login
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.createUser("admin","admin@test");
        controller.login("admin");
        solo.enterText((EditText) solo.getView(R.id.startscreen_username_et), "admin");
        solo.clickOnView(solo.getView(R.id.startscreen_login_button));

        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
    }

    @Override
    // do this after each test
    public void tearDown(){
        Controller controller = (Controller) getActivity().getApplicationContext();
        solo.finishOpenedActivities();

        controller.deleteUserById(controller.getUserByName("admin").getId());
        if(controller.getUserByName("admin2")!=null){
            controller.deleteUserById(controller.getUserByName("admin2").getId());
        }
    }

    /* test back button */
    public void testBackButton(){
        // click on back button
        solo.clickOnButton("Back");
        // test if we go back to main menu
        solo.assertCurrentActivity("did not go back", MenuActivity.class);
    }

    /* test owened list view */
    public void testOwnedList(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* test when user owns no instrument */
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user owns one or more instruments */
        // add two instruments to current user
        solo.clickOnButton("Back");
        controller.addInstrument("insturment1", "1st description");
        controller.addInstrument("instrument2", "2nd description");
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        // make sure that they appear
        assertTrue(solo.searchText("1st description"));
        assertTrue(solo.searchText("2nd description"));

        /* test click on the listed instrument */
        // click on first list item
        solo.clickOnText("1st description", 1);
        //test we swiched to another activity
        solo.assertCurrentActivity("not changed to view instrument activity", ViewInstrumentActivity.class);
        //go back insttumentlist
        solo.clickOnButton(solo.getString(R.string.back));
        // click on Second list item
        solo.clickOnText("2nd description",1);
        // test we swiched to another activity
        solo.assertCurrentActivity("not changed to view instrument activity", ViewInstrumentActivity.class);

    }


    /* test borrowed list view */
    public void testBorrowedList(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* test when user borrowed no instrument */
        // change to borrowedList view
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Borrowed");
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user has some borrowed list */
        //go back to menu
        solo.clickOnButton("Back");
        // add some borrowed insturment to "admin"
        String instrumentDescription = BorrowItem(controller);
        // go back to Borrowed list view
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Borrowed");
        // click on item and test if we changed to next activity
        solo.clickOnText(instrumentDescription);
        solo.assertCurrentActivity("not changed to view instrument activity", ViewInstrumentActivity.class);
    }

    /* test my bids list view */
    public void testMyBids(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* test when user bids on no instrument */
        // change to mybids view
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("My Bids");
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user has some borrowed list */
        //go back to menu
        solo.clickOnButton("Back");
        // add some bid  as admin "admin"
        String instrumentDescription = bidOnOneInsturment(controller);
        // go back to My bids list view
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("My Bids");
        // click on item and test if we changed to next activity
        solo.clickOnText(instrumentDescription);
        solo.assertCurrentActivity("not changed to view instrument activity", ViewInstrumentActivity.class);

    }

    /* test lended out view */
    public void testLendedOut(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* test when user lend out no instrument */
        // change to lend out view
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Lended Out");
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user has one lended out instrument */
        // go back to menu
        solo.clickOnButton("Back");
        // give current user a lended out item
        String instrumentDescription = lendOutInstrument(controller);
        // go to lend out view again
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Lended Out");

        // click on item and test if we changed to next activity
        solo.clickOnText(instrumentDescription);
        solo.assertCurrentActivity("not changed to view instrument activity", ViewInstrumentActivity.class);
    }

    // make current user borrow an item
    private String BorrowItem(Controller controller) {
        controller.createUser("admin2","test");

        // give admin2 an instrument
        controller.logout();
        controller.login("admin2");
        controller.addInstrument("test instrument", "test instrument");
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        String instrumentDescription = instrument.getDescription();

        // admin bid on admin2's instrument
        controller.logout();
        controller.login("admin");
        controller.makeBidOnInstrument(instrument, 10);


        // admin2 accepted the bid
        controller.logout();
        controller.login("admin2");
        controller.acceptBidOnInstrument(controller.getInstrumentById(instrument.getId()).getBids().getBid(0));

        // log back in
        controller.logout();
        controller.login("admin");

        return instrumentDescription;
    }

    // make current user bid on an item
    private String bidOnOneInsturment(Controller controller) {
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

        return instrumentDescription;
    }

    // let current user lend out an instrument
    private String lendOutInstrument(Controller controller){
        controller.createUser("admin2","test");

        // give admin2 an instrument
        controller.addInstrument("test instrument", "test instrument");
        Instrument instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(0);
        String instrumentDescription = instrument.getDescription();

        // admin bid on admin2's instrument
        controller.logout();
        controller.login("admin2");
        controller.makeBidOnInstrument(instrument, 10);

        // admin2 accepted the bid
        controller.logout();
        controller.login("admin");
        controller.acceptBidOnInstrument(controller.getInstrumentById(instrument.getId()).getBids().getBid(0));

        return instrumentDescription;
    }
}
