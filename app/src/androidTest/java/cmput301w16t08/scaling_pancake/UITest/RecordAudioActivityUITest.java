package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.AddInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.RecordAudioActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * Created by Aaron on 4/1/2016.
 */
public class RecordAudioActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    Controller controller;
    User user;

    public RecordAudioActivityUITest() {
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

        moveToActivity();
    }

    @Override
    public void tearDown(){
        // delete user
        controller.deleteUserById(user.getId());

        solo.finishOpenedActivities();
    }


    /* test startRecord button */
    public void testRecordButton(){
        solo.clickOnText("Start recording");
        // TODO: don't know how to test if microphone is active
        solo.clickOnText("Stop recording");
    }

    /* test play button */
    public void testPlaylButton(){
        solo.clickOnText("Start playing");
        // TODO: don't know how to test if microphone is active
        solo.clickOnText("Stop playing");
    }

    /* test save button */
    public void testSaveButton(){
        /* save without record */
        solo.clickOnText("Save Sample");
        solo.assertCurrentActivity("should have stayed", RecordAudioActivity.class);

        /* test with record */
        solo.clickOnText("Start recording");
        solo.clickOnText("Stop recording");
        solo.clickOnText("Save Sample");
        solo.assertCurrentActivity("should have go back",AddInstrumentActivity.class);
    }

    /* test cancel button */
    public void testCancelButton(){
        solo.clickOnText("Cancel");
        solo.assertCurrentActivity("should have go back to add instrument", AddInstrumentActivity.class);
    }

    private void moveToActivity(){
        solo.clickOnView(solo.getView(R.id.menu_add_instruments_button));
        solo.clickOnButton(solo.getString(R.string.add_audio_sample));
    }

}
