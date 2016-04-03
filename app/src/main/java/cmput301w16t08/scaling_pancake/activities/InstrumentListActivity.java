package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.adapters.BiddedInstrumentsAdapter;
import cmput301w16t08.scaling_pancake.adapters.LendedInstrumentsAdapter;
import cmput301w16t08.scaling_pancake.adapters.BorrowedInstrumentAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.adapters.OwnedInstrumentAdapter;
import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;

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
    private LendedInstrumentsAdapter lendedInstrumentsAdapter;

    private final static int ownedInstrumentsListCode = 1;
    private final static int borrowedInstrumentsListCode = 2;
    private final static int biddedInstrumentsListCode = 3;
    private final static int borrowedByOthersListCode = 4;

    private final static String id_token = "instrument_id";

    private int currentSelection;

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
        lendedInstrumentsAdapter = new LendedInstrumentsAdapter(controller,
                controller.getCurrentUsersOwnedBorrowedInstruments());

        setListAdapter(ownedInstrumentAdapter);
        currentSelection = ownedInstrumentsListCode;


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
            currentSelection = ownedInstrumentsListCode;
        }
        else if(selection.matches("Borrowed"))
        {
            setListAdapter(borrowedInstrumentAdapter);
            currentSelection = borrowedInstrumentsListCode;
        }
        else if(selection.matches("My Bids"))
        {
            setListAdapter(biddedInstrumentsAdapter);
            currentSelection = biddedInstrumentsListCode;
        }
        else if(selection.matches("Lended Out"))
        {
            setListAdapter(lendedInstrumentsAdapter);
            currentSelection = borrowedByOthersListCode;
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        switch(currentSelection)
        {
            case ownedInstrumentsListCode:
            {
                String instrumentId = ((Instrument) ownedInstrumentAdapter.getItem(position)).getId();
                viewOwnedInstrument(instrumentId);
                break;
            }
            case borrowedInstrumentsListCode:
            {
                String instrumentId = ((Instrument) borrowedInstrumentAdapter.getItem(position)).getId();
                viewBorrowedInstrument(instrumentId);
                break;
            }
            case biddedInstrumentsListCode:
            {
                String instrumentId = ((Bid) biddedInstrumentsAdapter.getItem(position)).getInstrumentId();
                viewMyBidsInstrument(instrumentId);
                break;
            }
            case borrowedByOthersListCode:
            {
                String instrumentId = ((Instrument) lendedInstrumentsAdapter.getItem(position)).getId();
                viewBorrowedByOthersInstrument(instrumentId);
                break;
            }
        }
    }

    public void viewOwnedInstrument(String instrumentId)
    {
        Intent intent = new Intent(this, ViewInstrumentActivity.class);

        Instrument instrument = controller.getCurrentUsersOwnedInstruments()
                .getInstrument(instrumentId);
        String status = instrument.getStatus();

        // If instrument is currently being lent out
        if (status.equals("borrowed")) {
            intent.putExtra("view_code", ViewInstrumentActivity.brwd_by_others_instrument_view_code);
            intent.putExtra(id_token, instrumentId);
        }

        // else : instrument is currently available or being bidded on:
        else {
            intent.putExtra("view_code", ViewInstrumentActivity.owned_instrument_view_code);
            intent.putExtra(id_token, instrumentId);
        }


        startActivity(intent);
    }

    public void viewBorrowedInstrument(String instrumentId)
    {
        Intent intent = new Intent(this, ViewInstrumentActivity.class);

        intent.putExtra("view_code", ViewInstrumentActivity.borrowed_instrument_view_code);
        intent.putExtra(id_token, instrumentId);

        startActivity(intent);
    }

    public void viewMyBidsInstrument(String instrumentId)
    {
        Intent intent = new Intent(this, ViewInstrumentActivity.class);

        intent.putExtra("view_code", ViewInstrumentActivity.mybids_instrument_view_code);
        intent.putExtra(id_token, instrumentId);

        startActivity(intent);
    }

    public void viewBorrowedByOthersInstrument(String instrumentId)
    {
        Intent intent = new Intent(this, ViewInstrumentActivity.class);

        intent.putExtra("view_code", ViewInstrumentActivity.brwd_by_others_instrument_view_code);
        intent.putExtra(id_token, instrumentId);

        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ownedInstrumentAdapter.notifyDataSetChanged();
        borrowedInstrumentAdapter.notifyDataSetChanged();
        biddedInstrumentsAdapter.notifyDataSetChanged();
        lendedInstrumentsAdapter.notifyDataSetChanged();
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
