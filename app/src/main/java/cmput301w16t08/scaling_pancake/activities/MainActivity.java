package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;

public class MainActivity extends AppCompatActivity {

    // set up our global controller
    private static Controller controller;
    public final static int createprofile_requestcode = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = (Controller) getApplicationContext();

    }


    public void createProfile(View view) {
        Intent intent = new Intent(this, CreateProfileActivity.class);

        startActivityForResult(intent, createprofile_requestcode);
    }

    public void logIn(View view){
        EditText userinput = (EditText) findViewById(R.id.startscreen_username_et);

        // store user name
        String userName = userinput.getText().toString();

        if (controller.login(userName)) {
            // if username is right
            menu(view);
        } else {
            // if username does not exist
            Toast.makeText(MainActivity.this, "wrong username", Toast.LENGTH_LONG).show();
        }
    }

    public void menu(View view){
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        /* Handle data returned from secondary activity */
        switch(requestCode)
        {
            /* Add more cases if needed in the future. */
            case createprofile_requestcode:
            {
                if(data != null && data.hasExtra("login") && data.getBooleanExtra("login", false))
                {
                    /* New user was created; start MenuActivity immediately */
                    Intent intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);

                }
            }
        }
    }

}
