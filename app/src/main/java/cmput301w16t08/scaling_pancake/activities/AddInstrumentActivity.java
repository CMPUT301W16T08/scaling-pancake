package cmput301w16t08.scaling_pancake.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;

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
            controller.addInstrument(name, description);
            finish();
        } else {
            Toast.makeText(AddInstrumentActivity.this, "Both Name and Description are required", Toast.LENGTH_LONG).show();
        }

    }

    public void addInstrumentCancel(View view){
        finish();
    }
}
