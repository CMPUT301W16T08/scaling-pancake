package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createProfile(View view)
    {
        Intent intent = new Intent(this, CreateProfileActivity.class);

        startActivity(intent);
    }

}
