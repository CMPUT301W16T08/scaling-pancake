package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.DisplaySearchResultsActivity;
import cmput301w16t08.scaling_pancake.activities.SearchInstrumentsActivity;

/**
 * Created by Aaron on 3/13/2016.
 */
public class SearchInstrumentsActivityUITest extends ActivityInstrumentationTestCase2{

    Solo solo;

    public SearchInstrumentsActivityUITest(){
        super(SearchInstrumentsActivity.class);
    }

    @Override
    public void setUp(){
        solo = new Solo(getInstrumentation(),getActivity());
    }

    @Override
    public void tearDown(){
        solo.finishOpenedActivities();
    }

    public void testSearchButton(){
        /* search without keywords */
        // click on search button
        solo.clickOnButton("SEARCH");
        // test if we are in next activity
        solo.assertCurrentActivity("did not change acitivity", DisplaySearchResultsActivity.class);

        /* search with keywords */
        // go back to search activity
        solo.clickOnButton("Back");
        //write in edit text
        solo.enterText((EditText) solo.getView(R.id.search_instrument_et), "apple");
        //click search button
        solo.clickOnButton("SEARCH");
        // test if we are in next activity
        solo.assertCurrentActivity("did not change acitivity", DisplaySearchResultsActivity.class);
    }

    @UiThreadTest
    public void testMenuButton(){
        // click on button
        ((Button)getActivity().findViewById(R.id.search_instrument_back_button)).performClick();
        //test if the activity is finished
        assertTrue(getActivity().isFinishing());
    }
}
