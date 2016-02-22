

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

        ((EditText) epa.findViewById(R.id.create_profile_email_tv)).setText(testEmail);
        ((EditText) epa.findViewById(R.id.create_profile_username_tv)).setText(testUserName);

        assertEquals(testUserName,
                ((EditText) epa.findViewById(R.id.create_profile_username_tv)).getText().toString());

        assertEquals(testUserName,
                ((EditText) epa.findViewById(R.id.create_profile_username_tv)).getText().toString());
    }
}

