package cmput301w16t08.scaling_pancake;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CreateProfileActivity extends AppCompatActivity
{

    private Controller controller;

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

}
