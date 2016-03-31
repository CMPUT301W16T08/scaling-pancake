package cmput301w16t08.scaling_pancake.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.adapters.BidsAdapter;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;

/**
 * Displays a list of the current <code>Bid</code>s on an instrument.
 *
 * @author dan
 */
public class ViewBidsActivity extends ListActivity
{

    private static Controller controller;
    private BidsAdapter bidsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);

        Intent intent = getIntent();

        controller = (Controller) getApplicationContext();
        bidsAdapter = new BidsAdapter(controller,
                controller.getInstrumentById(intent.getStringExtra("instrument_id")).getBids());

        setListAdapter(bidsAdapter);

        BidList bids = bidsAdapter.getBidList();
        for (int i = 0; i < bids.size(); i++){
            bids.getBid(i).setSeen(true);
        }
    }

    protected void onStart(){
        super.onStart();
        bidsAdapter.notifyDataSetChanged();
    }

//    public void acceptBid(View view){
//        Toast.makeText(controller, "Bid Accepted!", Toast.LENGTH_SHORT).show();
//        Bid bid;
//
//        Button acceptButton = (Button) findViewById(R.id.bid_list_item_accept_button);
//        //We have a view
//        // It needs to get it's position
//        // One way:
//        // Get its list view?
//
//
//        int position = bidsListView.getPositionForView(view);
//        controller.acceptBidOnInstrument(bid);
//        bidsAdapter.notifyDataSetChanged();
//
//    }
//
//    public void declineBid(View view){
//        Toast.makeText(controller, "Bid declined!", Toast.LENGTH_SHORT).show();
//    }

    public void back(View view)
    {
        finish();
    }
}
