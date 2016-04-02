package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.media.ThumbnailUtils;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Presents a view to the user that allows them to add an <code>Instrument</code>
 * to their profile.
 *
 * @author cmput301w16t08
 * @see Controller
 * @see cmput301w16t08.scaling_pancake.models.Instrument
 */
public class AddInstrumentActivity extends Activity
{
    private static final int AUDIO_REQUEST_CODE = 10;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int FADEDURATION = 150;
    private boolean displayingDialogBox = false;

    private Bitmap thumbnail;

    private static Controller controller;
    private String audioBase64 = null;

    private View dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instrument);

        controller = (Controller) getApplicationContext();

        dialogBox = findViewById(R.id.add_instrument_photo_dialog_box);

        dialogBox.bringToFront();
    }

    /**
     * Launches a dialog box that allows the user to select either device storage or
     * the camera to provide a picture.
     *
     * @param view
     */
    public void launchPhotoDialog(View view)
    {
        /* Quickly fade in the dialog box */
        dialogBox.setAlpha(0f);
        dialogBox.setVisibility(View.VISIBLE);
        dialogBox.animate()
                .alpha(1f)
                .setDuration(FADEDURATION)
                .setListener(null);

        View main = findViewById(R.id.add_instrument_main);

        /* And fade the background view slightly */
        main.setAlpha(1f);
        main.animate()
                .alpha(0.5f)
                .setDuration(FADEDURATION)
                .setListener(null);

        /* Keep track of state */
        displayingDialogBox = true;
    }

    /**
     * Browse the device storage for an image
     * @param view
     */
    public void launchGallery(View view)
    {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_LOAD_IMAGE);
        }
    }

    /**
     * Use the camera to supply an image
     * @param view
     */
    public void launchCamera(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Catch presses of the back button. Close dialog box if it is open, otherwise
     * move up activity stack
     */
    @Override
    public void onBackPressed()
    {
        if(displayingDialogBox)
        {
            hideDialogBox(null);
        }
        else
        {
            finish();
        }
    }

    /**
     * Handle data returned from the gallery or camera activities.
     * Constraint: All images are scaled to 256x256.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null)
        {
            switch (requestCode)
            {
                case REQUEST_IMAGE_CAPTURE:
                {
                    // http://developer.android.com/training/camera/photobasics.html
                    Bundle extras = data.getExtras();
                    thumbnail = ThumbnailUtils.extractThumbnail((Bitmap) extras.get("data"), 256, 256);
                    ((ImageView) findViewById(R.id.add_instrument_thumbnail_iv)).setImageBitmap(thumbnail);
                    break;
                }
                case REQUEST_LOAD_IMAGE:
                {
                    /*
                     * Credit:
                     * http://paragchauhan2010.blogspot.ca/2012/05/choose-image-from-gallary-and-display.html
                     * */
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView imageView = (ImageView) findViewById(R.id.add_instrument_thumbnail_iv);
                    thumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(picturePath), 256, 256);
                    imageView.setImageBitmap(thumbnail);
                    break;
                }
                case AUDIO_REQUEST_CODE:
                {
                    audioBase64 = data.getExtras().getString("audioUriBase64").replace("\n", "");
                    break;
                }
            }
        }
        hideDialogBox(null);
    }

    public void addAudioSample(View view) {
        Intent intent = new Intent(this, RecordAudioActivity.class);
        startActivityForResult(intent, AUDIO_REQUEST_CODE);
    }

    /**
     * Sends new <code>Instrument</code> data to the controller.
     * @param view
     */
    public void confirm(View view){
        EditText nameET = (EditText) findViewById(R.id.addInstrument_name_et);
        EditText descriptionET = (EditText) findViewById(R.id.addInstrument_description_et);

        String name = nameET.getText().toString();
        String description = descriptionET.getText().toString();

        if ((!name.matches("")) && (!description.matches("")))
        {
            Instrument addedInstrument = new Instrument(controller.getCurrentUser().getId(), name, description);
            controller.addInstrument(addedInstrument);
            if (thumbnail != null)
            {
                controller.addPhotoToInstrument(addedInstrument, thumbnail);
            }
            if (audioBase64 != null) {
                controller.addAudioSampleToInstrument(addedInstrument, audioBase64);
            }
            finish();
        }
        else
        {
            Toast.makeText(AddInstrumentActivity.this, "Both Name and Description are required", Toast.LENGTH_LONG).show();
        }
    }

    public void addInstrumentCancel(View view)
    {
        finish();
    }

    /**
     * Close the dialog box.
     */
    public void hideDialogBox(View view)
    {
        View main = findViewById(R.id.add_instrument_main);

        /* Fade background view bakc to full opacity */
        main.animate()
                .alpha(1f)
                .setDuration(FADEDURATION)
                .setListener(null);

        /* Fade out dialog box quickly */
        dialogBox.animate()
                .alpha(0f)
                .setDuration(FADEDURATION)
                .setListener(null);

        dialogBox.setVisibility(View.GONE);

        /* Keep track of state */
        displayingDialogBox = false;
    }
}
