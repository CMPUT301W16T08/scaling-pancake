package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Presents a view to the user that allows them to add an <code>Instrument</code>
 * to their profile.
 *
 * @author cmput301w16t08
 * @see Controller
 * @see cmput301w16t08.scaling_pancake.models.Instrument
 */
public class AddInstrumentActivity extends AppCompatActivity {

    // set up our global controller
    private static Controller controller;
    private String audioBase64;
    private int AUDIO_RESULT_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instrument);

        controller = (Controller) getApplicationContext();

    }

    /**
     * To be filled out in project part 5.
     * @param view
     */
    public void addPhoto(View view){

    }

    public void addAudioSample(View view) {
        Intent intent = new Intent(this, RecordAudioActivity.class);
        startActivityForResult(intent, AUDIO_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUDIO_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                audioBase64 = data.getExtras().getString("audioUriBase64");
            } else if (resultCode == Activity.RESULT_CANCELED) {
                audioBase64 = null;
            }
        }
    }

    /**
     * Sends new <code>Instrument</code> data to the controller.
     * @param view
     */
    public void confirm(View view){
        EditText nameET = (EditText) findViewById(R.id.addInstrument_name_et);
        EditText descriptionET = (EditText) findViewById(R.id.addInstrument_description_et);

        String name = nameET.getText().toString();
        String description = descriptionET.getText().toString();

        if ((!name.equals("")) && (!description.equals(""))) {
            Instrument instrument = new Instrument(controller.getCurrentUser().getId(), name, description);
            if (audioBase64 != null) {
                instrument.addSampleAudioBase64(audioBase64.replace("\n", ""));
            }
            controller.addInstrument(instrument);
            finish();
        } else {
            Toast.makeText(AddInstrumentActivity.this, "Both Name and Description are required", Toast.LENGTH_LONG).show();
        }

    }

    public void addInstrumentCancel(View view){
        finish();
    }
}
