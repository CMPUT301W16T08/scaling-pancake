package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;

/**
 * Allows a user to view a bid, then decline or accept the bid
 *
 * @author dan
 */
public class ViewBidActivity extends Activity
{
    private static Controller controller;
    private Bid bid;
    private int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient client;
    private Place place = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid);

        controller = (Controller) getApplicationContext();

        Intent intent = getIntent();

        String instrument_id = intent.getStringExtra("instrument_id");
        String bid_id = intent.getStringExtra("bid_id");

        bid = controller.getInstrumentById(instrument_id).getBids().getBid(bid_id);

        ((TextView) findViewById(R.id.view_bid_activity_name_tv)).setText(String.format("Instrument: %s", controller.getInstrumentById(bid.getInstrumentId()).getName()));
        ((TextView) findViewById(R.id.view_bid_activity_bidder_tv)).setText(String.format("Bidder: %s", controller.getUserById(bid.getBidderId()).getName()));
        ((TextView) findViewById(R.id.view_bid_activity_rate_tv)).setText(String.format("Rate: %s", bid.getBidAmount()));

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

    public void setLocation(View view) {
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
                place = PlacePicker.getPlace(data, this);
            }
        }
    }

    public void acceptBid(View view)
    {
        if (place == null) {
            Toast.makeText(controller, "Must select a pick up location!", Toast.LENGTH_SHORT).show();
        } else if(!bid.getAccepted())
        {
            controller.acceptBidOnInstrument(bid);
            controller.setLocationForInstrument(
                    controller.getCurrentUsersOwnedInstruments().getInstrument(getIntent().getStringExtra("instrument_id")),
                    place.getLatLng());
            Toast.makeText(controller, "Bid Accepted!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void declineBid(View view)
    {
        if (!bid.getAccepted())
        {
            controller.declineBidOnInstrument(bid);
            Toast.makeText(controller, "Bid Declined", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void back(View view)
    {
        finish();
    }

}
