package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;

/**
 * Provides a search bar for the user to makes queries.
 *
 * @author cmput301w16t08
 */
public class SearchInstrumentsActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_instruments);

        controller = (Controller) getApplicationContext();
    }

    /**
     * Navigate to the <code>DisplaySearchResultsActivity</code>
     * @param view
     */
    public void display_search_results(View view){
        EditText txtSearchTerm = (EditText) findViewById(R.id.search_instrument_et);
        String strSearchTerm = txtSearchTerm.getText().toString();

        /* Catch empty search string or string consisting of only spaces */
        if(strSearchTerm.trim().matches(""))
        {
            Toast.makeText(controller, "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, DisplaySearchResultsActivity.class);
        intent.putExtra("instrument_search_term", strSearchTerm);
        startActivity(intent);
    }

    /**
     * Return to the <code>MenuActivity</code>
     * @param view
     */
    public void goToMainMenu(View view){
        finish();
    }

}
