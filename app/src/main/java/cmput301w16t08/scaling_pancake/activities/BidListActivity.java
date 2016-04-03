package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.adapters.BidsAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;

/**
 * Displays a list of the current <code>Bid</code>s on an instrument.
 *
 * @author dan
 */
public class BidListActivity extends ListActivity
{

    private static Controller controller;
    private BidsAdapter bidsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list);
        Intent intent = getIntent();

        controller = (Controller) getApplicationContext();
        bidsAdapter = new BidsAdapter(controller, this,
                controller.getInstrumentById(intent.getStringExtra("instrument_id")).getBids());

        setListAdapter(bidsAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Bid bid = (Bid) bidsAdapter.getItem(position);
        String instrument_id = bid.getInstrumentId();
        String bid_id = bid.getId();

        Intent intent = new Intent(this, ViewBidActivity.class);

        intent.putExtra("instrument_id", instrument_id);
        intent.putExtra("bid_id", bid_id);

        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        bidsAdapter.notifyDataSetChanged();
    }

    public void back(View view)
    {
        finish();
    }
}
