package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
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

/**
 * Displays a list of the current <code>Bid</code>s on an instrument.
 *
 * @author dan
 */
public class ViewBidsActivity extends ListActivity
{

    private static Controller controller;
    private BidsAdapter bidsAdapter;
    int PLACE_PICKER_REQUEST = 1;
    GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);
        Intent intent = getIntent();

        controller = (Controller) getApplicationContext();
        bidsAdapter = new BidsAdapter(controller, this,
                controller.getInstrumentById(intent.getStringExtra("instrument_id")).getBids());

        setListAdapter(bidsAdapter);

        client = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
    }

    @Override
    protected  void onStop() {
        client.disconnect();
        super.onStop();
    }

    public void setLocation() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                final Place place = PlacePicker.getPlace(data, this);
                controller.setLocationForInstrument(controller.getCurrentUsersOwnedInstruments().getInstrument(
                        getIntent().getStringExtra("instrument_id")), place.getLatLng());
            }
        }
        bidsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
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
