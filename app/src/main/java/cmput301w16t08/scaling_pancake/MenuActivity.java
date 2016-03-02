package cmput301w16t08.scaling_pancake;

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


        // link views in xml to these variables
        final Button viewProfileButton = (Button) findViewById(R.id.menu_view_profile_button);
        final Button addInsButton = (Button) findViewById(R.id.menu_add_instruments_button);
        final Button viewInsButton = (Button) findViewById(R.id.menu_view_instruments_button);
        final Button searchInsButton = (Button) findViewById(R.id.menu_search_instruments_button);
    }

}
