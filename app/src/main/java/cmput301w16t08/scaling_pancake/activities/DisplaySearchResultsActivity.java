package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.xml.transform.Result;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.adapters.SearchResultsAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.InstrumentList;
import cmput301w16t08.scaling_pancake.util.PrePostActionWrapper;

/**
 * Provides a view container for the results of an <code>ElasticSearch</code>.
 * The search string is obtained as an extra from the <code>SearchInstrumentsActivity</code>
 * and sent to the controller. While the results are obtained, a <code>ProgressBar</code> is
 * displayed. Once finished, either the list of results or a message indicating that no results
 * were found will be displayed.
 *
 * @author dan
 * @see SearchInstrumentsActivity
 * @see Controller
 *
 */
public class DisplaySearchResultsActivity extends ListActivity
{
    private Controller controller;
    private InstrumentList resultList;
    private SearchResultsAdapter searchResultsAdapter;

    /**
     * A <code>PrePostActionWrapper</code> is used to handle displaying of the <code>ProgressBar</code>
     * while the <code>Controller</code> talks to the server.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);

        controller = (Controller) getApplicationContext();

        PrePostActionWrapper prePostActionWrapper = new PrePostActionWrapper()
        {
            @Override
            public void preAction()
            {
                /* Display progress bar */
                findViewById(R.id.displaysearchresults_pb).setVisibility(View.VISIBLE);
            }

            @Override
            public void postAction()
            {
                /* Hide progress bar and set the list adapter */
                findViewById(R.id.displaysearchresults_pb).setVisibility(View.INVISIBLE);
                setListAdapter(searchResultsAdapter);
            }
        };

        Intent intent = getIntent();

        /* Send query to controller */
        if(intent != null && intent.hasExtra("instrument_search_term"))
        {
            resultList = controller.searchInstruments(prePostActionWrapper, intent.getStringExtra("instrument_search_term"));
            searchResultsAdapter = new SearchResultsAdapter(controller, resultList);
        }
    }

}
