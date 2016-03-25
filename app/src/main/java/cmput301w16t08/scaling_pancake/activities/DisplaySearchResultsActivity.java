package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.adapters.SearchResultsAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;
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
    private String searchTerm;

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

        Intent intent = getIntent();

        /* Send query to controller */
        if(intent != null && intent.hasExtra("instrument_search_term"))
        {
            searchTerm = intent.getStringExtra("instrument_search_term");
        }

        PrePostActionWrapper prePostActionWrapper = new PrePostActionWrapper()
        {
            @Override
            public void preAction()
            {
                /* Display progress bar and hide no results message */
                findViewById(R.id.displaysearchresults_pb).setVisibility(View.VISIBLE);
                findViewById(R.id.displaysearchresults_empty_list_tv).setVisibility(View.INVISIBLE);
            }

            @Override
            public void postAction(Object... objects)
            {
                if(objects != null)
                {
                    resultList = (InstrumentList) objects[0];
                    searchResultsAdapter = new SearchResultsAdapter(controller, resultList);
                    /* Hide progress bar and set the list adapter */
                    findViewById(R.id.displaysearchresults_pb).setVisibility(View.INVISIBLE);
                    setListAdapter(searchResultsAdapter);

                    if (resultList.size() == 0)
                    {
                        findViewById(R.id.displaysearchresults_pb).setVisibility(View.INVISIBLE);
                        findViewById(R.id.displaysearchresults_empty_list_tv).setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        /* Obtain the search results on a background thread */
        controller.searchInstruments(prePostActionWrapper, searchTerm);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent intent = new Intent(this, ViewInstrumentActivity.class);

        intent.putExtra("view_code", ViewInstrumentActivity.searched_instrument_view_code);
        intent.putExtra("position", position);
        intent.putExtra("instrument_id", ((Instrument) searchResultsAdapter.getItem(position)).getId());

        startActivity(intent);
    }

}