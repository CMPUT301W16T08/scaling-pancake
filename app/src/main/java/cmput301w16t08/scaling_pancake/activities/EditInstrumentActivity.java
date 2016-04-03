package cmput301w16t08.scaling_pancake.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import cmput301w16t08.scaling_pancake.R;
import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Provides a view where the <code>User</code> can edit one of their <code>Instrument</code>s.
 *
 * @author dan
 */

public class EditInstrumentActivity extends AppCompatActivity
{
    private static Controller controller;
    private Instrument instrument;
    private static final int AUDIO_REQUEST_CODE = 10;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int FADEDURATION = 150;
    private boolean displayingDialogBox = false;
    private String audioBase64 = null;
    private Bitmap thumbnail = null;
    private MediaPlayer player;
    private byte [] bytes;
    private View dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instrument);

        controller = (Controller) getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();

        player = new MediaPlayer();
        bytes = null;

        Intent intent = getIntent();

        instrument = controller.getCurrentUsersOwnedInstruments().getInstrument(intent.getStringExtra("instrument_id"));

        if (thumbnail != null) {
            ((ImageView) findViewById(R.id.edit_instrument_thumbnail_iv)).setImageBitmap(thumbnail);
        } else if (instrument.hasThumbnail())
        {
            ((ImageView) findViewById(R.id.edit_instrument_thumbnail_iv)).setImageBitmap(instrument.getThumbnail());
        }

        ((EditText) findViewById(R.id.edit_instrument_view_name_et)).setText(instrument.getName());
        ((EditText) findViewById(R.id.edit_instrument_view_description_et)).setText(instrument.getDescription());

        dialogBox = findViewById(R.id.edit_instrument_photo_dialog_box);

        dialogBox.bringToFront();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
    }

    public void onPlayButtonClick(View view)
    {
        player.reset();
        if (audioBase64 != null) {
            bytes = Base64.decode(audioBase64, 0);
        } else if (bytes == null) {
            bytes = Base64.decode(instrument.getSampleAudioBase64(), 0);
        }
        if (bytes.length == 0) {
            Toast.makeText(getApplicationContext(), "No audio sample", Toast.LENGTH_SHORT).show();
        } else {
            try {
                File file = File.createTempFile("tempFile", "tmp", null);
                file.deleteOnExit();
                FileOutputStream stream = new FileOutputStream(file);
                stream.write(bytes);
                stream.close();
                FileInputStream stream2 = new FileInputStream(file);
                player.setDataSource(stream2.getFD());
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.start();
        }
    }


    public void deleteInstrument(View view)
    {
        controller.deleteInstrument(instrument);

        Toast.makeText(controller, "Instrument deleted.", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void back(View view)
    {
        finish();
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

        View main = findViewById(R.id.edit_instrument_main);

        /* And fade the background view slightly */
        main.setAlpha(1f);
        main.animate()
                .alpha(0.5f)
                .setDuration(FADEDURATION)
                .setListener(null);

        /* Keep track of state */
        displayingDialogBox = true;
    }


    public void addAudioSample(View view)
    {
        Intent intent = new Intent(this, RecordAudioActivity.class);
        startActivityForResult(intent, AUDIO_REQUEST_CODE);
    }

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
                    ((ImageView) findViewById(R.id.edit_instrument_thumbnail_iv)).setImageBitmap(thumbnail);
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
                    ImageView imageView = (ImageView) findViewById(R.id.edit_instrument_thumbnail_iv);
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
     * Close the dialog box.
     */
    public void hideDialogBox(View view)
    {
        View main = findViewById(R.id.edit_instrument_main);

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

    public void save(View view)
    {
        EditText nameET = (EditText) findViewById(R.id.edit_instrument_view_name_et);
        EditText descriptionET = (EditText) findViewById(R.id.edit_instrument_view_description_et);

        String name = nameET.getText().toString();
        String description = descriptionET.getText().toString();

        if ((!name.matches("")) && (!description.matches("")))
        {
            controller.editInstrument(instrument, name, description);

            if (thumbnail != null)
            {
                controller.addPhotoToInstrument(instrument, thumbnail);
            }
            if (audioBase64 != null)
            {
                controller.addAudioSampleToInstrument(instrument, audioBase64);
            }
            finish();
        }
        else
        {
            Toast.makeText(this, "Both Name and Description are required", Toast.LENGTH_LONG).show();
        }
    }
}
