package Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cmput301w16t08.scaling_pancake.Controller;
import cmput301w16t08.scaling_pancake.R;

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
        intent.putExtra("user_id", controller.getCurrentUser().getId());
        startActivity(intent);
    }

    public void addInstruments(View view){
        Intent intent = new Intent(this, AddInstrumentActivity.class);

        startActivity(intent);
    }

    public void viewInstruments(View view)
    {
        Intent intent = new Intent(this, InstrumentListActivity.class);

        startActivity(intent);
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
