package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Adapter;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.activities.DisplaySearchResultsActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Created by Aaron on 3/12/2016.
 */
public class InstrumentListActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;

    public InstrumentListActivityUITest(){
        super(MenuActivity.class);
    }

    @Override
    // set this up before each test
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());

        //login as Hongyang
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.createUser("admin","admin@test");
        controller.login("admin");

        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
    }

    @Override
    // do this after each test
    public void tearDown(){
        Controller controller = (Controller) getActivity().getApplicationContext();
        solo.finishOpenedActivities();
        controller.deleteUser();
    }

    public void testBackButton(){
        // click on back button
        solo.clickOnButton("Back");
        // test if we go back to main menu
        solo.assertCurrentActivity("did not go back", MenuActivity.class);
    }

    public void testOwnedList(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        // TODO: need getAdapter method from instumentlistActivyt

        /* test when user owns no instrument */
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user owns one or more instruments */
        // go back to menu
        solo.clickOnButton("Back");
        // add some instrument to this user
        controller.addInstrument("insturment1","1st description");
        controller.addInstrument("instrument2", "2nd description");
        // go to OwnedList view
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        // make sure there are they appear
        assertTrue(solo.searchText("1st description"));
        assertTrue(solo.searchText("2nd description"));

        /* test View button and Edit Button for a insturment */
        // click on first "VIEW" text
        solo.clickOnText("VIEW",1);
        //TODO: test we swiched to another activity
               //that activity is not known yet......
        // go back insttumentlist
        //solo.clickOnView(solo.getView("I need Id here"));
        // click on Second "Edit" text(button)
        solo.clickOnText("EDIT",2);
        //TODO: test we swiched to another activity
              //that activity is not known yet......

    }


    public void testBorrowedList(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* test when user owns no instrument */
        // change to borrowedList view
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Borrowed");
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user has some borrowed list */
        //go back to menu
        solo.clickOnButton("Back");
        // add some borrowed insturment to "admin"
        controller.logout();
        controller.login("Hongyang");
        String instrumentId = controller.getCurrentUsersOwnedInstruments().getInstrument(0).getId();

        controller.logout();
        controller.login("admin");
        controller.makeBidOnInstrument(controller.getInstrumentById(instrumentId), 10);

        controller.logout();
        controller.login("Hongyang");
        controller.acceptBidOnInstrument(controller.getCurrentUsersBids().getBid(0));

        controller.logout();
        controller.login("admin");
        // go back to Borrowed list view
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("Borrowed");
        // test if there is one item listed
        assertFalse(solo.searchText("Nothing to show"));
    }

    public void testMyBids(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        /* test when user owns no instrument */
        // change to borrowedList view
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("My Bids");
        // make sure this is ONE VISIBLE text "Nothing to show" on screen
        assertTrue(solo.searchText("Nothing to show", 1, false, true));

        /* test when user has some borrowed list */
        //go back to menu
        solo.clickOnButton("Back");
        // add some bid  as admin "admin"
        controller.logout();
        controller.login("Hongyang");
        String instrumentId = controller.getCurrentUsersOwnedInstruments().getInstrument(0).getId();

        controller.logout();
        controller.login("admin");
        controller.makeBidOnInstrument(controller.getInstrumentById(instrumentId), 10);
        // go back to My bids list view
        solo.clickOnView(solo.getView(R.id.menu_view_instruments_button));
        solo.clickOnView(solo.getView(R.id.instrumentlistactivity_category_spinner));
        solo.clickOnText("My Bids");
        // test if there is one item listed
        assertFalse(solo.searchText("Nothing to show"));
    }

}
