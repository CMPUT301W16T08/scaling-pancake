package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;

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

    /**
     * Log the user out and return to the login screen
     * @param view
     */
    public void logout(View view){
        controller.logout();
        finish();
    }

}
