package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cmput301w16t08.scaling_pancake.R;

public class SearchInstrumentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_instruments);
    }

    public void display_search_results(View view){
        EditText txtSearchTerm = (EditText) findViewById(R.id.search_instrument_et);
        String strSearchTerm = txtSearchTerm.getText().toString();

        Intent intent = new Intent(this, DisplaySearchResultsActivity.class);
        intent.putExtra("instrument_search_term", strSearchTerm);
        startActivity(intent);
    }

    public void goToMainMenu(View view){
        finish();
    }

}
