package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

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
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
