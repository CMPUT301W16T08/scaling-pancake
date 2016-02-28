package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // set up our global controller
    final Controller controller = (Controller) getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link views in xml to these variables
        final EditText userinput = (EditText) findViewById(R.id.startscreen_username_et);
        final Button loginButton = (Button) findViewById(R.id.startscreen_login_button);
        final Button createButton = (Button) findViewById(R.id.startscreen_createprofile_button);

        // if user click on the createprofile button
        createButton.setOnClickListener(
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to CreateProfileActivity
                    createProfile(v);
                }
        });

        // if user click on the login button
        loginButton.setOnClickListener(
            new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // store user name
                    String userName = userinput.getText().toString();

                    if (controller.login(userName)){
                        // if username is right
                        menu(v);
                    }else {
                        // if username does not exist
                        //TODO: give user a message
                    }
                }
        });
    }

    public void createProfile(View view) {
        Intent intent = new Intent(this, CreateProfileActivity.class);

        startActivity(intent);
    }

    public void menu(View view){
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

}
