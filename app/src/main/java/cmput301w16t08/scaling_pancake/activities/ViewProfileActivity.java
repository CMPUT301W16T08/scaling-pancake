package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        controller = (Controller) getApplicationContext();
        User current_user = controller.getCurrentUser();

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");

        if (!(current_user.getId().matches(user_id)))
        {
            View edit_button = findViewById(R.id.edit_profile_button);
            edit_button.setVisibility(View.GONE);
            user = current_user;
        }
        else
        {
            user = controller.getUserById(user_id);
        }

        setUsernameTextView();
        setEmailTextView();
    }

    /**
     * Initialize the username <code>TextView</code>
     */
    public void setUsernameTextView() {
        TextView usernameTextView = (TextView) findViewById(R.id.view_profile_username_tv);
        String name = user.getName();

        usernameTextView.setText(String.format("Username: %s", name));
    }

    /**
     * Initialize the email <code>TextView</code>
     */
    public void setEmailTextView(){
        TextView emailTextView = (TextView) findViewById(R.id.view_profile_email_tv);
        String email = user.getEmail();
        emailTextView.setText(String.format("Email: %s", email));
    }

    /**
     * Navigate to the <code>EditProfileActivity</code>
     * @param view
     */
    public void launchEditProfile(View view)
    {
        Intent intent = new Intent(this, EditProfileActivity.class);
        finish();
        startActivity(intent);
    }


    public void back(View view){
        finish();
    }
}
