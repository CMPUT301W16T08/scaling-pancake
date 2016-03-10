package cmput301w16t08.scaling_pancake;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dan on 10/02/16.
 */
public class ViewProfileActivityUITest extends ActivityInstrumentationTestCase2 {
    public ViewProfileActivityUITest() {
        super(ViewProfileActivity.class);
    }

    public void testViewProfile() {
        Intent intent = new Intent();

        setActivityIntent(intent);

        ViewProfileActivity vpa = (ViewProfileActivity) getActivity();

        /* Test visibility of username and email fields */

        View origin = vpa.getWindow().getDecorView();
        TextView usernameTV = (TextView) origin.findViewById(R.id.view_profile_username_tv);
        TextView emailTV = (TextView) origin.findViewById(R.id.view_profile_email_tv);

        ViewAsserts.assertOnScreen(origin, usernameTV);
        ViewAsserts.assertOnScreen(origin, emailTV);
    }
}
