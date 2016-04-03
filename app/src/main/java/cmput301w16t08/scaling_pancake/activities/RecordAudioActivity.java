package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import cmput301w16t08.scaling_pancake.R;

/**
 * The <code>RecordAudioActivity</code> allows the currently logged in user to record audio
 * on the phone's microphone, play it back, and save it to the instrument.
 *
 * @author William
 *
 * Taken from the Android Developer Guides at
 * http://developer.android.com/guide/topics/media/audio-capture.html
 */
public class RecordAudioActivity extends AppCompatActivity {

    private static String filePath = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private RecordButton recordButton = null;
    private Button playButton = null;
    private boolean hasRecorded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/" + UUID.randomUUID().toString() + ".3gp";

        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params1.setMargins(0, 400, 0, 10);
        recordButton = new RecordButton(this);
        recordButton.setId(View.generateViewId());
        recordButton.setLayoutParams(params1);
        recordButton.setBackgroundResource(R.drawable.btn_selector);
        layout.addView(recordButton);

        params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params2.addRule(RelativeLayout.BELOW, recordButton.getId());
        params2.setMargins(0, 10, 0, 10);
        playButton = new Button(this);
        playButton.setId(View.generateViewId());
        playButton.setLayoutParams(params2);
        playButton.setText("Play recording");
        playButton.setBackgroundResource(R.drawable.btn_selector);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasRecorded) {
                    player.reset();
                    try {
                        player.setDataSource(filePath);
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.start();
                }
            }
        });
                layout.addView(playButton);

        params3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params3.addRule(RelativeLayout.BELOW, playButton.getId());
        params3.setMargins(0, 10, 0, 10);
        Button saveButton = new Button(this);
        saveButton.setId(View.generateViewId());
        saveButton.setText("Save Sample");
        saveButton.setBackgroundResource(R.drawable.btn_selector);
        saveButton.setLayoutParams(params3);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!hasRecorded) {
                    Toast.makeText(getApplicationContext(), "Nothing recorded", Toast.LENGTH_SHORT).show();
                } else {
                    FileInputStream stream = null;
                    File audioFile = new File(filePath);
                    byte[] bytes = new byte[(int) audioFile.length()];
                    try {
                        stream = new FileInputStream(audioFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        stream.read(bytes);
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getIntent().putExtra("audioUriBase64", Base64.encodeToString(bytes, 0));
                    setResult(Activity.RESULT_OK, getIntent());
                    finish();
                }
            }
        });
        layout.addView(saveButton);

        params4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params4.addRule(RelativeLayout.BELOW, saveButton.getId());
        params4.setMargins(0, 10, 0, 10);
        Button cancelButton = new Button(this);
        cancelButton.setId(View.generateViewId());
        cancelButton.setText("Cancel");
        cancelButton.setBackgroundResource(R.drawable.btn_selector);
        cancelButton.setLayoutParams(params4);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();
            }
        });
        layout.addView(cancelButton);

        setContentView(layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        player = new MediaPlayer();
    }

    /**
     * Called when the recordButton is clicked
     *
     * @param start boolean for whether already recording
     */
    private void onRecord(boolean start) {
        if (start) {
            hasRecorded = true;
            startRecording();
        } else {
            stopRecording();
        }
    }

    /**
     * Used to start recording the audio on the phone's microphone
     */
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(filePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();
    }

    /**
     * Used to stop recording the audio on the phone's microphone
     */
    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
         public void onPause() {
        super.onPause();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**
     * Custom <code>Button</code> class that allows for different functionality on click
     * depending on whether already recording on not
     */
    class RecordButton extends Button {
        boolean startRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(startRecording);
                if (startRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                startRecording = !startRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }
}
