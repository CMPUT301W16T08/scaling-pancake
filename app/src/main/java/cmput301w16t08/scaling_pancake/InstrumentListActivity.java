package cmput301w16t08.scaling_pancake;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class InstrumentListActivity extends ListActivity implements AdapterView.OnItemSelectedListener
{
    private static Controller controller;

    private OwnedInstrumentAdapter ownedInstrumentAdapter;
    private BorrowedInstrumentAdapter borrowedInstrumentAdapter;
    private BiddedInstrumentsAdapter biddedInstrumentsAdapter;

    private Instrument instrument;
    private InstrumentList instrumentList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_list);

        controller = (Controller) getApplicationContext();

        biddedInstrumentsAdapter = new BiddedInstrumentsAdapter(getApplicationContext(),
                controller.getCurrentUsersBids());
        ownedInstrumentAdapter = new OwnedInstrumentAdapter(getApplicationContext(),
                controller.getCurrentUsersOwnedInstruments());
        borrowedInstrumentAdapter = new BorrowedInstrumentAdapter(getApplicationContext(),
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

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
