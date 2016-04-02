package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
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
        controller.createUser("admin","e1@123.com");
        user = controller.getUserByName("admin");
        controller.login(user.getName());
    }

    @Override
    public void tearDown(){
        // delete user
        controller.deleteUserById(user.getId());

        solo.finishOpenedActivities();
    }

    /* test back button */
    public void testBackButton(){

    }

    /* test name and description displayed is the right one */
    public void testDisplay(){

    }

    /* test edit button */


    /* test view bids button */

    
    //TODO: test photo I don't know how to test if a photo is the one we desired
    //TODO: test play sample button: I don't know how to test if a sound is the sound we want to hear

    // move from the MenuActivity to ViewOwnedInstrument
    private void moveToActivity(){

    }

    // add an instrument to current user
    private void addInstrument(){

    }
}
