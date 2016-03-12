package cmput301w16t08.scaling_pancake.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;

public class AddInstrumentActivity extends AppCompatActivity {

    // set up our global controller
    private static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instrument);

        controller = (Controller) getApplicationContext();

    }

    public void addPhoto(View view){

    }

    public void confirm(View view){
        EditText nameET = (EditText) findViewById(R.id.addInstrument_name_et);
        EditText descriptionET = (EditText) findViewById(R.id.addInstrument_description_et);

        String name = nameET.getText().toString();
        String description = descriptionET.getText().toString();

        controller.addInstrument(name,description);

        finish();
    }

    public void addInstrumentCancel(View view){
        finish();
    }
}
