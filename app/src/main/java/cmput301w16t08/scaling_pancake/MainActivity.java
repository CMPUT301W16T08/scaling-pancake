package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // set up our global controller
    private static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = (Controller) getApplicationContext();

    }

    public void createProfile(View view) {
        Intent intent = new Intent(this, CreateProfileActivity.class);

        startActivity(intent);
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

}
