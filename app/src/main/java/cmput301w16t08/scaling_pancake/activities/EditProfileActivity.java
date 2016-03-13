package cmput301w16t08.scaling_pancake.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;

public class EditProfileActivity extends AppCompatActivity {

    private static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        controller = (Controller) getApplicationContext();
    }

    public void save_profile_changes(View view){
        // http://stackoverflow.com/questions/6290531/check-if-edittext-is-empty

        /* Obtain username string from edittext. */
        EditText txtUsername = (EditText) findViewById(R.id.edit_profile_username_et);
        String strUsername = txtUsername.getText().toString();

        /* Obtain email string from edittext. */
        EditText txtEmail = (EditText) findViewById(R.id.edit_profile_email_et);
        String strEmail = txtEmail.getText().toString();

        if (TextUtils.isEmpty(strUsername))
        {
            /* Empty Input */
            Toast.makeText(controller, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(strEmail))
        {
            /* Empty Input */
            Toast.makeText(controller, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(controller.editCurrentUser(strUsername, strEmail))
        {
            /* Good input. */
            Toast.makeText(controller, "Changes saved!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            /* Controller indicated this username already exists. */
            Toast.makeText(controller, "Username is already taken.", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel_profile_changes(View view)
    {
        finish();
    }

}
