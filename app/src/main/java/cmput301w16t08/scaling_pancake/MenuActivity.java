package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    // set up our global controller
    private static Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        controller = (Controller) getApplicationContext();

    }

    public void viewProfile(View view){
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    public void addInstruments(View view){
        Intent intent = new Intent(this, AddInstrumentActivity.class);

        startActivity(intent);
    }

    public void viewInstruments(View view){

    }

    public void searchInstruments(View view){
        Intent intent = new Intent(this, SearchInstrumentsActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        controller.logout();
        finish();
    }

}
