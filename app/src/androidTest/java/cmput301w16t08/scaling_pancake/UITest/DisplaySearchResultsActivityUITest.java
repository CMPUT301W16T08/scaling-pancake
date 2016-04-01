package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.SearchInstrumentsActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 3/25/2016.
 */
public class DisplaySearchResultsActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User first;
    User second;
    final String searchString = "test instrument";

    public DisplaySearchResultsActivityUITest() {
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
    }

    @Override
    public void tearDown(){
        // delete users
        controller.deleteUserById(first.getId());
        controller.deleteUserById(second.getId());
        solo.finishOpenedActivities();
    }

    // this method make moves from menuActivity to displaySearchResultSActivity
    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_search_instruments_button));
        solo.enterText((EditText) solo.getView(R.id.search_instrument_et), searchString);
        solo.clickOnView(solo.getView(R.id.search_instrument_search_button));
    }

    /* test when there is no item to show */
    public void testListViewWithoutItem(){
        moveToActivity();

        // test if "No matching items found" is on screen
        assertTrue(solo.searchText("No matching items found"));
    }

    /* test when there are items showing up */
    public void testListViewWithItem(){
        // add an instrument for first user
        controller.addInstrument("instrument1", searchString);
        // add an instrument for second user
        controller.logout();
        controller.login(second.getName());
        controller.addInstrument("instrument2", searchString);
        // search for these instruments
        moveToActivity();

        // test when we click on the item that belongs to current user
        solo.clickOnText("instrument1");
        solo.assertCurrentActivity("should have go to ViewInstrumentActivity", ViewInstrumentActivity.class);
        solo.clickOnButton(solo.getString(R.string.back));
        // test when we click on the item that does not belong to current user
        solo.clickOnText("instrument2");
        solo.assertCurrentActivity("should have go to ViewInstrumentActivity", ViewInstrumentActivity.class);
    }

    /* test back button */
    public void testBackButton(){
        moveToActivity();

        // click on the back button
        solo.clickOnView(solo.getView(R.id.displaysearchresults_back_button));
        // make sure we are in searchInstrumentActivity again
        solo.assertCurrentActivity("should have go back to previous activity", SearchInstrumentsActivity.class);
    }

}
