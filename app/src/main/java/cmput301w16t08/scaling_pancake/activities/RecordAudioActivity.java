package cmput301w16t08.scaling_pancake.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import cmput301w16t08.scaling_pancake.controllers.Controller;
import cmput301w16t08.scaling_pancake.models.Instrument;

/**
 * Created by William on 2016-03-26.
 *
 * Taken from the Android Developer Guides at
 * http://developer.android.com/guide/topics/media/audio-capture.html
 */
public class RecordAudioActivity extends AppCompatActivity {

    private static String filePath = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private RecordButton recordButton = null;
    private PlayButton playButton = null;
    private boolean hasRecorded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/" + UUID.randomUUID().toString() + ".3gp";

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        recordButton = new RecordButton(this);
        layout.addView(recordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        playButton = new PlayButton(this);
        layout.addView(playButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));

        Button saveButton = new Button(this);
        saveButton.setText("Save Sample");
        saveButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!hasRecorded) {
                    Toast.makeText(getApplicationContext(), "Nothing recorded", Toast.LENGTH_SHORT).show();
                } else {
                    FileInputStream stream = null;
                    File audioFile =  new File(filePath);
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
                    String string = Base64.encodeToString(bytes, 0);
                    getIntent().putExtra("audioUriBase64", Base64.encodeToString(bytes, 0));
                    setResult(Activity.RESULT_OK, getIntent());
                    finish();
                }
            }
        });
        layout.addView(saveButton);

        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, getIntent());
                finish();
            }
        });
        layout.addView(cancelButton);

        setContentView(layout);
    }

    private void onRecord(boolean start) {
        if (start) {
            hasRecorded = true;
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(filePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

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

    class PlayButton extends Button {
        boolean startPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(startPlaying);
                if (startPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                startPlaying = !startPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

}
