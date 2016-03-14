package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.models.User;

/**
 * <code>ViewProfileActivity</code> provides a screen for the user to view their
 * profiles data, as well as a button to navigate to the <code>EditProfileActivity</code>
 * and a button leading back to the <code>MenuActivity</code>.
 *
 * @author cmput301w16t08
 * @see EditProfileActivity
 */
public class ViewProfileActivity extends AppCompatActivity {
    private static Controller controller;
    private static final String TAG = "ViewProfileActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        controller = (Controller) getApplicationContext();
        User current_user = controller.getCurrentUser();
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        if (!(current_user.getId().equals(user_id))){
            View edit_button = findViewById(R.id.edit_profile_button);
            edit_button.setVisibility(View.GONE);
        }
        User user = controller.getUserById(user_id);
        setUsernameTextView(user);
        setEmailTextView(user);
    }

    /**
     * Initialize the username <code>TextView</code>
     * @param user
     */
    public void setUsernameTextView(User user) {
        TextView usernameTextView = (TextView) findViewById(R.id.view_profile_username_tv);
        String name = user.getName();

        usernameTextView.append(name);
    }

    /**
     * Initialize the email <code>TextView</code>
     * @param user
     */
    public void setEmailTextView(User user){
        TextView emailTextView = (TextView) findViewById(R.id.view_profile_email_tv);
        String email = user.getEmail();
        emailTextView.append(email);
    }

    /**
     * Navigate to the <code>EditProfileActivity</code>
     * @param view
     */
    public void launchEditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);

        startActivity(intent);

        finish();
    }

    /**
     * Return to the <code>MenuActivity</code>
     * @param view
     */
    public void goToMainMenu(View view){
        finish();
    }
}
