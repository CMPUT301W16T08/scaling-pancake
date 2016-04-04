package cmput301w16t08.scaling_pancake.UITest;


import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.AddInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.EditInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.InstrumentListActivity;
import cmput301w16t08.scaling_pancake.activities.MainActivity;
import cmput301w16t08.scaling_pancake.activities.MenuActivity;
import cmput301w16t08.scaling_pancake.activities.RecordAudioActivity;
import cmput301w16t08.scaling_pancake.activities.ViewInstrumentActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
import cmput301w16t08.scaling_pancake.models.InstrumentList;
import cmput301w16t08.scaling_pancake.models.User;

public class EditInstrumentActivityUITest extends ActivityInstrumentationTestCase2 {
    Controller controller;
    Solo solo;
    User user;
    Instrument instrument;

    public EditInstrumentActivityUITest() {
        super(MainActivity.class);
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
        solo.enterText((EditText) solo.getView(R.id.startscreen_username_et), user.getName());
        solo.clickOnView(solo.getView(R.id.startscreen_login_button));
        controller.addInstrument("test instrument", "test instrument description");
        // reassign user and instrument
        user =controller.getCurrentUser();
        instrument = user.getOwnedInstruments().getInstrument(0);
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
        solo.assertCurrentActivity("we are in the wrong activity", EditInstrumentActivity.class);
        // click on back button
        solo.clickOnButton(solo.getString(R.string.back));
        // test if we are back to previous activity
        solo.assertCurrentActivity("we should have go back", InstrumentListActivity.class);
    }

    /* test for display */
    public void testDisplay(){
        moveToActivity();
        // test the name of the instrument
        EditText nameET = (EditText) solo.getView(R.id.edit_instrument_view_name_et);
        assertEquals(instrument.getName(), nameET.getText().toString());

        // test the description of the instrument
        EditText descriptionET = (EditText) solo.getView(R.id.edit_instrument_view_description_et);
        assertEquals(instrument.getDescription(), descriptionET.getText().toString());
    }

    /* test the save button */
    public void testSaveButton(){
        moveToActivity();

        EditText nameET = (EditText) solo.getView(R.id.edit_instrument_view_name_et);
        String newName = "new name";
        solo.clearEditText(nameET);
        solo.enterText(nameET, newName);

        assertEquals(newName, nameET.getText().toString());
        // before click
        instrument = controller.getInstrumentById(instrument.getId());
        assertNotSame(instrument.getName(), newName);
        // click
        solo.clickOnButton(solo.getString(R.string.save));
        // after click
        solo.assertCurrentActivity("back to list",InstrumentListActivity.class);
        instrument = controller.getInstrumentById(instrument.getId());
        assertEquals(instrument.getName(), newName);
    }

    /* test for delete button */
    public void testDeleteButton(){
        moveToActivity();

        // before click
        assertNotNull(controller.getInstrumentById(instrument.getId()));
        // click
        solo.clickOnButton(solo.getString(R.string.delete));
        // after click
        solo.assertCurrentActivity("back to list",InstrumentListActivity.class);
        assertEquals(0,controller.getCurrentUser().getOwnedInstruments().size());
    }

    /* test for edit music button */
    public void testEditMusic(){
        moveToActivity();

        solo.clickOnView(solo.getView(R.id.edit_instrument_editsample_iv));
        solo.assertCurrentActivity("we are in the record activity now", RecordAudioActivity.class);
    }

    /* test for edit photo button */
    //TODO:
//    public void testEditPhoto(){
//        moveToActivity();

        /* test gallery button */
//        solo.clickOnView(solo.getView(R.id.edit_instrument_editimage_iv));
//        solo.clickOnImageButton(1);
//        //TODO: I don't know how to test if we are in the gallery...

//        solo.finishOpenedActivities();
//        moveToActivity();
        /* test camera button */
//        solo.clickOnView(solo.getView(R.id.edit_instrument_editimage_iv));
//        solo.clickOnImageButton(2);
        //TODO: I don't know how to test if we are in the camera...
//    }


    //Todo: I don't know how to test the play sample button...
    public void testPlaySampleButton(){
        moveToActivity();

        solo.clickOnView(solo.getView(R.id.edit_instrument_play_sample_button));
    }

    private void moveToActivity(){
        solo.clickOnButton(solo.getString(R.string.view_instruments));
        solo.clickOnText(instrument.getDescription());
        solo.clickOnButton(solo.getString(R.string.edit));
    }
}
