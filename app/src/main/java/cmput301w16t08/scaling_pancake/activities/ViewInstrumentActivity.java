package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * This view will be completed for project part 5.
 *
 *
 * @author dan
 */
public class ViewInstrumentActivity extends AppCompatActivity
{
    private static Controller controller;

    public final static int owned_instrument_view_code = 1;
    public final static int borrowed_instrument_view_code = 2;
    public final static int mybids_instrument_view_code = 3;
    public final static int brwd_by_others_instrument_view_code = 4;
    public final static int searched_instrument_view_code = 5;

    private Instrument selected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        controller = (Controller) getApplicationContext();

        Intent intent = getIntent();

        if(!intent.hasExtra("view_code") || !intent.hasExtra("position"))
        {
            throw new RuntimeException("ViewInstrumentActivity: Intent is lacking position or view code");
        }

        switch(intent.getIntExtra("view_code", 0))
        {
            case owned_instrument_view_code:
            {
                selected = controller.getCurrentUsersOwnedInstruments()
                        .getInstrument(intent.getIntExtra("position", 0));
                setContentView(R.layout.owned_instrument_view);

                ((TextView) findViewById(R.id.owned_instrument_view_name_tv)).append(selected.getName());
                ((TextView) findViewById(R.id.owned_instrument_view_status_tv)).append(selected.getStatus());
                ((TextView) findViewById(R.id.owned_instrument_view_description_tv)).append(selected.getDescription());
                break;
            }
            case borrowed_instrument_view_code:
            {
                selected = controller.getCurrentUsersBorrowedInstruments()
                        .getInstrument(intent.getIntExtra("position", 0));
                setContentView(R.layout.borrowed_instrument_view);

                ((TextView) findViewById(R.id.borrowed_instrument_view_name_tv)).append(selected.getName());
                ((TextView) findViewById(R.id.borrowed_instrument_view_owner_tv)).append(controller.getUserById(selected.getOwnerId()).getName());
                ((TextView) findViewById(R.id.borrowed_instrument_view_description_tv)).append(selected.getDescription());
                break;
            }
            case mybids_instrument_view_code:
            {
                selected = controller.getCurrentUsersBiddedInstruments()
                        .getInstrument(intent.getIntExtra("position", 0));
                setContentView(R.layout.mybids_instrument_view);

                Bid largest = selected.getLargestBid();
                String bidData = largest.getBidAmount() + "/hr";

                if(largest.getBidderId().matches(controller.getCurrentUser().getId()))
                {
                    bidData += "  (Me)";
                }
                else
                {
                    bidData += "  (" + controller.getUserById(largest.getOwnerId()).getName() + ")";
                }

                ((TextView) findViewById(R.id.mybids_instrument_view_name_tv)).append(selected.getName());
                ((TextView) findViewById(R.id.mybids_instrument_view_owner_tv)).append(controller.getUserById(selected.getOwnerId()).getName());
                ((TextView) findViewById(R.id.mybids_instrument_view_maxbid_tv)).append(bidData);
                ((TextView) findViewById(R.id.mybids_instrument_view_description_tv)).append(selected.getDescription());
                break;
            }
            case brwd_by_others_instrument_view_code:
            {
                setContentView(R.layout.brwd_by_others_instrument_view);

                selected = controller.getCurrentUsersOwnedBorrowedInstruments()
                        .getInstrument(intent.getIntExtra("position", 0));

                ((TextView) findViewById(R.id.brwd_by_others_instrument_view_name_tv)).append(selected.getName());
                ((TextView) findViewById(R.id.brwd_by_others_instrument_view_borrower_tv))
                        .append(controller.getUserById(selected.getBorrowedById()).getName());
                ((TextView) findViewById(R.id.brwd_by_others_instrument_view_rate_tv))
                        .append(String.format("%.2f/hr", selected.getBids().getBid(0).getBidAmount()));
                ((TextView) findViewById(R.id.brwd_by_others_instrument_view_description_tv)).append(selected.getDescription());
                break;
            }
            case searched_instrument_view_code:
            {
                setContentView(R.layout.searched_instrument_view);

                if(!intent.hasExtra("instrument_id"))
                {
                    throw new RuntimeException("ViewInstrumentActivity: Missing instrument id for searched instrument");
                }
                selected = controller.getInstrumentById(intent.getStringExtra("instrument_id"));

                ((TextView) findViewById(R.id.searched_instrument_view_name_tv)).append(selected.getName());
                ((TextView) findViewById(R.id.searched_instrument_view_owner_tv)).append(controller.getUserById(selected.getOwnerId()).getName());
                ((TextView) findViewById(R.id.searched_instrument_view_status_tv)).append(selected.getStatus());
                ((TextView) findViewById(R.id.searched_instrument_view_description_tv)).append(selected.getDescription());

                break;
            }
            default:
            {
                throw new RuntimeException("Invalid view code in ViewInstrumentActivity");
            }
        }

    }

    public void makeBid(View view)
    {
        float bidAmount = Float.parseFloat(((EditText) findViewById(R.id.searched_instrument_view_bidamount_et)).getText().toString());

        controller.makeBidOnInstrument(selected, bidAmount);
    }

    public void viewBids(View view)
    {
        Intent intent = new Intent(this, ViewBidsActivity.class);

        intent.putExtra("instrument_id", selected.getId());

        startActivity(intent);
    }

    public void viewLocation(View view) {
        Intent intent = new Intent(this, ViewLocationActivity.class);
        intent.putExtra("latitude", selected.getLatitude());
        intent.putExtra("longitude", selected.getLongitude());
        startActivity(intent);
    }
}
