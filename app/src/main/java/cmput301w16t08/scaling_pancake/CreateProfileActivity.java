package cmput301w16t08.scaling_pancake;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    public void cancel(View view)
    {
        finish();
    }

    public void save(View view){

        EditText nameET = (EditText) findViewById(R.id.createprofile_username_et);
        EditText emailET = (EditText) findViewById(R.id.createprofile_email_et);

        //get the String of the content of what user input
        String username = nameET.getText().toString();
        String email = emailET.getText().toString();

        if(controller.createUser(username,email)) {
            // if successfully create the user
            // go back to login
            finish();
        }else{
            // if username is in use
            // prompt username in use message
            Toast.makeText(CreateProfileActivity.this,"username is in use",Toast.LENGTH_LONG).show();
        }
    }

}
