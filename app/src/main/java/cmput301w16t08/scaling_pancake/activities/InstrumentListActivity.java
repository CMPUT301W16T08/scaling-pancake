package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import cmput301w16t08.scaling_pancake.adapters.BiddedInstrumentsAdapter;
import cmput301w16t08.scaling_pancake.adapters.BorrowedInstrumentAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.adapters.OwnedInstrumentAdapter;
import cmput301w16t08.scaling_pancake.R;

/**
 * The <code>InstrumentListActivity</code> generates a list of <code>Instrument</code>s that are
 * owned, borrowed, or bidded on depending on the spinner selection.
 *
 * @author dan
 * @see OwnedInstrumentAdapter
 * @see BorrowedInstrumentAdapter
 * @see BiddedInstrumentsAdapter
 *
 */
public class InstrumentListActivity extends ListActivity implements AdapterView.OnItemSelectedListener
{
    private static Controller controller;

    private OwnedInstrumentAdapter ownedInstrumentAdapter;
    private BorrowedInstrumentAdapter borrowedInstrumentAdapter;
    private BiddedInstrumentsAdapter biddedInstrumentsAdapter;

    /**
     * The adapters are initialized with data from the <code>Controller</code> in onCreate().
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_list);

        controller = (Controller) getApplicationContext();

        biddedInstrumentsAdapter = new BiddedInstrumentsAdapter(controller,
                controller.getCurrentUsersBids());
        ownedInstrumentAdapter = new OwnedInstrumentAdapter(controller,
                controller.getCurrentUsersOwnedInstruments());
        borrowedInstrumentAdapter = new BorrowedInstrumentAdapter(controller,
                controller.getCurrentUsersBorrowedInstruments());

        setListAdapter(ownedInstrumentAdapter);


        /*
        * These lines taken from the api guides verbatim.
        * http://developer.android.com/guide/topics/ui/controls/spinner.html
        * */
        Spinner spinner = (Spinner) findViewById(R.id.instrumentlistactivity_category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.instrument_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        /* Set this as the listener for the spinner items */
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * <code>onItemSelected()</code> responds to different selections in the spinner
     * by changing which list of instruments is displayed.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        /*
               Change what list is displayed based on the spinner selection.
         */
        String selection = parent.getItemAtPosition(position).toString();

        if(selection.matches("Owned"))
        {
            setListAdapter(ownedInstrumentAdapter);
        }
        else if(selection.matches("Borrowed"))
        {
            setListAdapter(borrowedInstrumentAdapter);
        }
        else if(selection.matches("My Bids"))
        {
            setListAdapter(biddedInstrumentsAdapter);
        }
    }

    /**
     * No changes are made when nothing is selected. The listener interface requires that this
     * method body be here.
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    /**
     * Return to the <code>MenuActivity</code>
     * @param view
     */
    public void back(View view)
    {
        /* Leave the instrument list view and return to the menu. */
        finish();
    }
}
