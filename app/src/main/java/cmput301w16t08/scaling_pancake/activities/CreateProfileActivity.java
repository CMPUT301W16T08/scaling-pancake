package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;

/**
 * Presents a view to the user that allows them to create a new profile.
 *
 * @author cmput301w16t08
 * @see Controller
 */
public class CreateProfileActivity extends AppCompatActivity
{

    private static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        controller = (Controller) getApplicationContext();
    }

    /**
     * Returns to the login screen without creating a profile.
     * @param view
     */
    public void cancel(View view)
    {
        /* Indicate to the MainActivity that create profile was cancelled. */
        Intent returnedIntent = new Intent();

        returnedIntent.putExtra("login", false);

        setResult(Activity.RESULT_OK, returnedIntent);

        finish();
    }

    /**
     * Sends data to controller to be evaluated and stored.
     * @param view
     */
    public void save(View view){

        EditText nameET = (EditText) findViewById(R.id.createprofile_username_et);
        EditText emailET = (EditText) findViewById(R.id.createprofile_email_et);

        //get the String of the content of what user input
        String username = nameET.getText().toString();
        String email = emailET.getText().toString();


        if (username.equals("")){
            // if username is empty
            // prompt not valid message
            Toast.makeText(CreateProfileActivity.this,"username can't be empty",Toast.LENGTH_LONG).show();
        } else if (email.equals("")){
            // if email is empty
            // prompt username in use message
            Toast.makeText(CreateProfileActivity.this,"email can't be empty ",Toast.LENGTH_LONG).show();
        } else if(controller.createUser(username,email)) {
            // if successfully create the user
            // login new user and finish
            controller.login(username);
            Intent returnedIntent = new Intent();
            returnedIntent.putExtra("login", true);
            setResult(Activity.RESULT_OK, returnedIntent);
            finish();
        } else {
            // if username is in use
            // prompt username in use message
            Toast.makeText(CreateProfileActivity.this,"username is in use",Toast.LENGTH_LONG).show();
        }
    }

}
