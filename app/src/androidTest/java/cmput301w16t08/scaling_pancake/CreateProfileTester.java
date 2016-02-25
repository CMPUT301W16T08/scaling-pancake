package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;




public class CreateProfileTester extends ActivityInstrumentationTestCase2 {
    public CreateProfileTester() {
        super(CreateProfileActivity.class);
    }

    @UiThreadTest
    public void testCreateProfile() {
        Intent intent = new Intent();

        setActivityIntent(intent);

        CreateProfileActivity epa = (CreateProfileActivity) getActivity();

        String testUserName = "username";
        String testEmail = "email";

        ((EditText) epa.findViewById(R.id.createprofile_email_et)).setText(testEmail);
        ((EditText) epa.findViewById(R.id.createprofile_username_et)).setText(testUserName);

        assertEquals(testUserName,
                ((EditText) epa.findViewById(R.id.createprofile_username_et)).getText().toString());

        assertEquals(testUserName,
                ((EditText) epa.findViewById(R.id.createprofile_username_et)).getText().toString());
    }
}

