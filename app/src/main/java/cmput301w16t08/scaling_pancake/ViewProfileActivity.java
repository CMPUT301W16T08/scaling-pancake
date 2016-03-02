package cmput301w16t08.scaling_pancake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ViewProfileActivity extends AppCompatActivity {

    private static Controller controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_profile);

        controller = (Controller) getApplicationContext();
    }

}
