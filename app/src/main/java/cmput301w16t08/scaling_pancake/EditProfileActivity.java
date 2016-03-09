package cmput301w16t08.scaling_pancake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private static Controller controller;
    private static Context context;
    private static final String TAG = "EditProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        controller = (Controller) getApplicationContext();
        context = getApplicationContext();
    }

    public void save_profile_changes(View view){
        // http://stackoverflow.com/questions/6290531/check-if-edittext-is-empty

        EditText txtUsername = (EditText) findViewById(R.id.edit_profile_username_et);
        String strUsername = txtUsername.getText().toString();
        if (TextUtils.isEmpty(strUsername)){
            Log.d(TAG, "User didn't edit their username");
        }
        else{
            controller.editCurrentUserName(strUsername);
            Log.d(TAG, "Username edited");
        }

        EditText txtEmail = (EditText) findViewById(R.id.edit_profile_email_et);
        String strEmail = txtEmail.getText().toString();
        if (TextUtils.isEmpty(strEmail)){
            Log.d(TAG, "User did not edit their email");
        }
        else{
            controller.editCurrentUserEmail(strEmail);
            Log.d(TAG, "User email edited");
        }

        Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    public void cancel_profile_changes(View view){
        finish();
    }

}
