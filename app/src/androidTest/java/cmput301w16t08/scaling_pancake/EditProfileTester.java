package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;

public class EditProfileTester extends ActivityInstrumentationTestCase2 {
    public EditProfileTester() {
        super(EditProfileActivity.class);
    }

    @UiThreadTest
    public void testEditProfile() {
        Intent intent = new Intent();

        setActivityIntent(intent);

        EditProfileActivity epa = (EditProfileActivity) getActivity();

        String testUserName = "username";
        String testEmail = "email";

        ((EditText) epa.findViewById(R.id.edit_profile_email_tv)).setText(testEmail);
        ((EditText) epa.findViewById(R.id.edit_profile_username_tv)).setText(testUserName);

        assertEquals(testUserName,
                ((EditText) epa.findViewById(R.id.edit_profile_username_tv)).getText().toString());

        assertEquals(testUserName,
                ((EditText) epa.findViewById(R.id.edit_profile_username_tv)).getText().toString());
    }
}