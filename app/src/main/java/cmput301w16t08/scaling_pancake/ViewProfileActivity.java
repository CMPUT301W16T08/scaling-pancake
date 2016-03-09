package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class ViewProfileActivity extends AppCompatActivity {
    private static Controller controller;
    private static final String TAG = "ViewProfileActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        controller = (Controller) getApplicationContext();
        User user = controller.getCurrentUser();

        setUsernameTextView(user);
        setEmailTextView(user);
    }

    public void setUsernameTextView(User user) {
        TextView usernameTextView = (TextView) findViewById(R.id.username_textView);
        String name = user.getName();

        Log.d(TAG, name);
        usernameTextView.setText(name);
    }

    public void setEmailTextView(User user){
        TextView emailTextView = (TextView) findViewById(R.id.email_textView);
        String email = user.getEmail();
        emailTextView.setText(email);
    }

    public void launchEditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
    public void cancelViewProfile(View view){
        finish();
    }
}
