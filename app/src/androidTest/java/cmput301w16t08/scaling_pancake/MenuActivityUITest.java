package cmput301w16t08.scaling_pancake;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by Aaron on 3/10/2016.
 */
public class MenuActivityUITest extends ActivityInstrumentationTestCase2 {
    public MenuActivityUITest() {
        super(MenuActivity.class);
    }

    public void testViewProfileButton(){
        //click on viewProfile
        ((Button) getActivity().findViewById(R.id.menu_view_profile_button)).performClick();
        // TODO: check if another activity is launched
    }

    public void testAddInstrumentsButton(){
        //click on viewProfile
        ((Button) getActivity().findViewById(R.id.menu_add_instruments_button)).performClick();
        // TODO: check if another activity is launched
    }

    public void testViewInstrumentsButton(){
        //click on viewProfile
        ((Button) getActivity().findViewById(R.id.menu_view_instruments_button)).performClick();
        // TODO: check if another activity is launched
    }

    public void testSearchInstrumentsButton(){
        //click on viewProfile
        ((Button) getActivity().findViewById(R.id.menu_search_instruments_button)).performClick();
        // TODO: check if another activity is launched
    }

    public void testBackButton(){
        Controller controller = (Controller) getActivity().getApplicationContext();

        //click on viewProfile
        ((Button) getActivity().findViewById(R.id.menu_back_button)).performClick();

        //check if activity ends
        assertTrue(getActivity().isFinishing());

        //check if user logout
        assertNull(controller.getCurrentUser());
    }
}
