package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Provides a view where the <code>User</code> can edit one of their <code>Instrument</code>s.
 *
 * @author dan
 */

public class EditInstrumentActivity extends AppCompatActivity
{
    private static Controller controller;
    private Instrument instrument;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instrument);

        controller = (Controller) getApplicationContext();

        Intent intent = getIntent();

        instrument = controller.getInstrumentById(intent.getStringExtra("instrument_id"));

        ((EditText) findViewById(R.id.edit_instrument_view_name_et)).setText(instrument.getName());
        ((EditText) findViewById(R.id.edit_instrument_view_description_et)).setText(instrument.getDescription());
    }


    public void deleteInstrument(View view)
    {
        controller.deleteInstrument(instrument);

        Toast.makeText(controller, "Instrument deleted.", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void back(View view)
    {
        finish();
    }

}
