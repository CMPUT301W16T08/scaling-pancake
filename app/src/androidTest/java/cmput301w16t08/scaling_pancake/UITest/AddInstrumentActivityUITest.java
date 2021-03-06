package cmput301w16t08.scaling_pancake.UITest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.robotium.solo.Solo;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.activities.AddInstrumentActivity;
import cmput301w16t08.scaling_pancake.activities.RecordAudioActivity;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Created by Aaron on 3/13/2016.
 */
public class AddInstrumentActivityUITest extends ActivityInstrumentationTestCase2 {
    Solo solo;
    EditText name;
    EditText description;

    public AddInstrumentActivityUITest(){
        super(AddInstrumentActivity.class);

    }

    @Override
    // setUp runs before every test
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
        name = (EditText) getActivity().findViewById(R.id.addInstrument_name_et);
        description = (EditText) getActivity().findViewById(R.id.addInstrument_description_et);

        // login a test user
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.createUser("admin","admin@test.com");
        controller.login("admin");
    }

    @Override
    // tearDown runs after every test
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();

        // delete test user
        Controller controller = (Controller) getActivity().getApplicationContext();
        controller.deleteUserById(controller.getUserByName("admin").getId());
    }


    /* test confirm button */
    @UiThreadTest
    public void testConfirmButton(){
        Controller controller = (Controller) getActivity().getApplicationContext();
        int oldSize = controller.getCurrentUsersOwnedInstruments().size();

        /* do not enter anything */
        ((Button) getActivity().findViewById(R.id.addInstrument_confirm_button)).performClick();
        assertFalse(getActivity().isFinishing());

        /*only enter name*/
        name.setText("apple");
        //click confirm
        ((Button) getActivity().findViewById(R.id.addInstrument_confirm_button)).performClick();
        assertFalse(getActivity().isFinishing());

        name.setText("");

        /*only enter description*/
        description.setText("this is an apple instrument");
        //click confirm
        ((Button) getActivity().findViewById(R.id.addInstrument_confirm_button)).performClick();
        assertFalse(getActivity().isFinishing());



        /* enter both */
        name.setText("apple");
        //click confirm
        ((Button) getActivity().findViewById(R.id.addInstrument_confirm_button)).performClick();
        assertTrue(getActivity().isFinishing());
        assertEquals(controller.getCurrentUsersOwnedInstruments().size(), oldSize + 1);
    }

    /* test cancel */
    @UiThreadTest
    public void testCancel(){
        ((Button) getActivity().findViewById(R.id.addInstrument_cancel_button)).performClick();
        assertTrue(getActivity().isFinishing());
    }

    /* test add photo button */
    public void testPhotoButton(){
        solo.clickOnButton(solo.getString(R.string.add_photo));

        // click on camera
        solo.clickOnImageButton(1);
        //TODO: don't know how to test this, because we are still in the AddInstrumentActivity in the background

    }

    /* test add audio button */
    public void testAudioButton(){
        solo.clickOnButton(solo.getString(R.string.add_audio_sample));
        solo.assertCurrentActivity("should have go to audio activity", RecordAudioActivity.class);
    }
}
