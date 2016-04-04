package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.apache.commons.lang3.ObjectUtils;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.models.Bid;
import cmput301w16t08.scaling_pancake.models.BidList;
import cmput301w16t08.scaling_pancake.models.InstrumentList;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * The <code>MenuActivity</code> provides a set of options for the user to click on.
 * Each option navigates to a corresponding <code>Activity</code>.
 * This is the main "dashboard" of the application.
 *
 * @author cmput301w16t08
 * @see ViewProfileActivity
 * @see SearchInstrumentsActivity
 * @see InstrumentListActivity
 * @see AddInstrumentActivity
 * @see Controller
 *
 */
public class MenuActivity extends AppCompatActivity {
    // set up our global controller
    private static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        controller = (Controller) getApplicationContext();
    }

    protected void onResume(){
        super.onResume();

        View notif_button = findViewById(R.id.new_bid_notif_button);
        User current_user = controller.getCurrentUser();
        if(current_user != null){
            if (!current_user.getNewBidFlag()){
                notif_button.setVisibility(View.INVISIBLE);
            }
        }else {
            notif_button.setVisibility(View.INVISIBLE);
        }
    }

//    protected Bid getUnseenBid(){
//        User user = controller.getCurrentUser();
//
//        // Checks each of the owners instruments for an unseen bid.
//        // Returns the first unseen bid that it finds.
//        InstrumentList instruments = user.getOwnedInstruments();
//        for (int i = 0; i < instruments.size(); i++){
//            BidList bids = instruments.getInstrument(i).getBids();
//            for (int j = 0; j< bids.size(); j++){
//                if (!(bids.getBid(j).getSeen())){ //if bid.seen == false
//                    return bids.getBid(j);
//                }
//            }
//        }
//        return null;
//    }

    /**
     * Navigate to the <code>ViewProfileActivity</code>
     * @param view
     */
    public void viewProfile(View view){
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra("user_id", controller.getCurrentUser().getId());
        startActivity(intent);
    }

    /**
     * Navigate to the <code>AddInstrumentActivity</code>
     * @param view
     */
    public void addInstruments(View view){
        Intent intent = new Intent(this, AddInstrumentActivity.class);

        startActivity(intent);
    }

    /**
     * Navigate to the <code>InstrumentListActivity</code>
     * @param view
     */
    public void viewInstruments(View view)
    {
        Intent intent = new Intent(this, InstrumentListActivity.class);

        startActivity(intent);
    }

    public void searchInstruments(View view){
        Intent intent = new Intent(this, SearchInstrumentsActivity.class);
        startActivity(intent);
    }

    public void viewNotification(View view){
        Intent intent = new Intent(this, InstrumentListActivity.class);
        controller.resetNewBidFlag();
        startActivity(intent);
    }

    /**
     * Log the user out and return to the login screen
     * @param view
     */
    public void logout(View view){
        controller.logout();
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

}
