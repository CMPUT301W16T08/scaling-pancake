package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * This view will be completed for project part 5.
 *  The intent to launch this view should have a viewCode
 *  as well as a position.
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
    private MediaPlayer player;
    private boolean isPlaying;
    private Uri audioUri;
    private byte [] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        controller = (Controller) getApplicationContext();

        Intent intent = getIntent();

        player = new MediaPlayer();
        isPlaying = false;
        bytes = null;

        if(!intent.hasExtra("view_code") || !intent.hasExtra("position"))
        {
            throw new RuntimeException("ViewInstrumentActivity: Intent is lacking position or view code");
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();

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

                if(selected.hasThumbnail())
                {
                    ((ImageView) findViewById(R.id.owned_instrument_view_thumbnail_iv)).setImageBitmap(selected.getThumbnail());
                }
                break;
            }
            case borrowed_instrument_view_code:
            {
                selected = controller.getCurrentUsersBorrowedInstruments()
                        .getInstrument(intent.getIntExtra("position", 0));
                setContentView(R.layout.borrowed_instrument_view);
                String message;

                if(!selected.getReturnedFlag()) {
                    message = "You are currently borrowing this instrument";
                }
                else{
                    message = "Waiting for return to be confirmed by owner";
                }

                ((TextView) findViewById(R.id.borrowedinstrumentview_borrowing_tv)).setText(message);

                ((TextView) findViewById(R.id.borrowed_instrument_view_name_tv))
                        .setText(selected.getName());

                ((TextView) findViewById(R.id.borrowed_instrument_view_rate_tv))
                        .setText(String.format("Rate: %.2f",
                                selected.getBids().getBid(0).getBidAmount()));

                ((TextView) findViewById(R.id.borrowed_instrument_view_owner_tv))
                        .setText(String.format("Owner: %s",
                                controller.getUserById(selected.getOwnerId()).getName()));

                ((TextView) findViewById(R.id.borrowed_instrument_view_description_tv))
                        .setText(String.format("Description: %s", selected.getDescription()));

                if(selected.hasThumbnail())
                {
                    ((ImageView) findViewById(R.id.borrowed_instrument_view_thumbnail_iv)).setImageBitmap(selected.getThumbnail());
                }
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

                if(selected.hasThumbnail())
                {
                    ((ImageView) findViewById(R.id.mybids_instrument_view_thumbnail_iv)).setImageBitmap(selected.getThumbnail());
                }
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

                if(selected.hasThumbnail())
                {
                    ((ImageView) findViewById(R.id.brwd_by_others_instrument_view_thumbnail_iv)).setImageBitmap(selected.getThumbnail());
                }
                break;
            }
            case searched_instrument_view_code:
            {
                if(!intent.hasExtra("instrument_id"))
                {
                    throw new RuntimeException("ViewInstrumentActivity: Missing instrument id for searched instrument");
                }
                selected = controller.getInstrumentById(intent.getStringExtra("instrument_id"));

                if(selected.getOwnerId().matches(controller.getCurrentUser().getId()))
                {
                    setContentView(R.layout.owned_instrument_view);
                    ((TextView) findViewById(R.id.owned_instrument_view_name_tv)).append(selected.getName());
                    ((TextView) findViewById(R.id.owned_instrument_view_status_tv)).append(selected.getStatus());
                    ((TextView) findViewById(R.id.owned_instrument_view_description_tv)).append(selected.getDescription());
                }
                else
                {
                    setContentView(R.layout.searched_instrument_view);
                    ((TextView) findViewById(R.id.searched_instrument_view_name_tv)).append(selected.getName());
                    ((TextView) findViewById(R.id.searched_instrument_view_owner_tv)).append(controller.getUserById(selected.getOwnerId()).getName());
                    ((TextView) findViewById(R.id.searched_instrument_view_status_tv)).append(selected.getStatus());
                    ((TextView) findViewById(R.id.searched_instrument_view_description_tv)).append(selected.getDescription());
                }

                if(selected.hasThumbnail())
                {
                    ((ImageView) findViewById(R.id.searched_instrument_view_thumbnail_iv)).setImageBitmap(selected.getThumbnail());
                }
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
        if(((EditText) findViewById(R.id.searched_instrument_view_bidamount_et)).getText().toString().matches(""))
        {
            Toast.makeText(controller, "Please Enter a Bid Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        float bidAmount = Float.parseFloat(((EditText) findViewById(R.id.searched_instrument_view_bidamount_et)).getText().toString());
        controller.makeBidOnInstrument(selected, bidAmount);
        Toast.makeText(controller, "Bid Received!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void edit(View view)
    {
        Intent intent = new Intent(this, EditInstrumentActivity.class);
        intent.putExtra("instrument_id", selected.getId());
        startActivity(intent);
    }

    public void viewBids(View view)
    {
        Intent intent = new Intent(this, BidListActivity.class);
        intent.putExtra("instrument_id", selected.getId());
        startActivity(intent);
    }

    public void viewLocation(View view) {
        Intent intent = new Intent(this, ViewLocationActivity.class);
        intent.putExtra("latitude", selected.getLatitude());
        intent.putExtra("longitude", selected.getLongitude());
        startActivity(intent);
    }

    public void returnAsBorrower(View view){
        if (selected.getStatus().equals("borrowed")) {
            if (!selected.getReturnedFlag()) { //if returnedFlag == false
                controller.returnInstrument(selected);
                String message = "Waiting for return to be confirmed by owner";
                ((TextView) findViewById(R.id.borrowedinstrumentview_borrowing_tv)).setText(message);
            }
            else{
                Toast.makeText(getApplicationContext(), "pending owner approval!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            //Toast.makeText(getApplicationContext(), "instrument status not currently borrowed", Toast.LENGTH_SHORT).show();
            throw new RuntimeException("Instrument status is not currently 'borrowed'");
        }

        selected.setReturnedFlag(true);
    }

    public void onPlayButtonClick(View view) {
        if (isPlaying == false) {
            if (bytes == null) {
                bytes = Base64.decode(selected.getSampleAudioBase64(), 0);
            }
            if (bytes.length == 0) {
                Toast.makeText(getApplicationContext(), "No audio sample", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    File file = File.createTempFile("tempFile", "tmp", null);
                    file.deleteOnExit();
                    FileOutputStream stream = new FileOutputStream(file);
                    stream.write(bytes);
                    stream.close();
                    FileInputStream stream2 = new FileInputStream(file);
                    player.setDataSource(stream2.getFD());
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
                isPlaying = true;
            }
        } else {
            player.stop();
            isPlaying = false;
        }
    }
    /**
     * returnedFlag for the instrument 'selected' should be true upon entering this method
     *      and will be false upon exit.
     * @param view
     */
    public void markReturned(View view){
        if(selected.getReturnedFlag()) {
            controller.acceptReturnedInstrument(selected);
            Toast.makeText(getApplicationContext(), "instrument returned!", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.owned_instrument_view);
            ((TextView) findViewById(R.id.owned_instrument_view_name_tv)).append(selected.getName());
            ((TextView) findViewById(R.id.owned_instrument_view_status_tv)).append(selected.getStatus());
            ((TextView) findViewById(R.id.owned_instrument_view_description_tv)).append(selected.getDescription());
        }
        else{
            Toast.makeText(getApplicationContext(), "Oops! The borrower still hasn't returned the instrument", Toast.LENGTH_SHORT).show();
        }
        selected.setReturnedFlag(false);
    }

    public void displayOwner(View view){
        Toast.makeText(getApplicationContext(), "showing owner...", Toast.LENGTH_SHORT).show();
        String user_id = selected.getOwnerId();
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);

    }

    public void displayBorrower(View view){
        Toast.makeText(getApplicationContext(), "showing borrower..", Toast.LENGTH_SHORT).show();
        String user_id = selected.getBorrowedById();
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }


    public void back(View view)
    {
        finish();
    }
}
