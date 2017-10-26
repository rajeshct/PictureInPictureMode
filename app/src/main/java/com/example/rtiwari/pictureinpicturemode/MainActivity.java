package com.example.rtiwari.pictureinpicturemode;

import android.app.PictureInPictureParams;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    VideoView videoView;
    int stopPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        videoView = findViewById(R.id.videoView);
        loadVideo();
        showToast("onCreateCalled");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadVideo() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.testvideo);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setMediaController(new MediaController(this));
    }

    public void launchPictureInPictureMode(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams pictureInPictureParams = new PictureInPictureParams.Builder().build();
            enterPictureInPictureMode(pictureInPictureParams);
        } else {
            enterPictureInPictureMode();
        }
    }

    /*You might want to include logic that switches an activity into PIP mode instead of going into the background.
    For example, Google Maps switches to PIP mode if the user presses the home or recents button while the app is navigating.
    You can catch this case by overriding onUserLeaveHint()*/
    @Override
    public void onUserLeaveHint() {

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
            toolbar.setVisibility(View.GONE);
        } else {
            // Restore the full-screen UI.
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        showToast("On Pause");
        // If called while in PIP mode, do not pause playback
        if (!isInPictureInPictureMode()) {
            stopPosition = videoView.getCurrentPosition();
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("On Resume" + videoView.isPlaying());
        // If called while in PIP mode, do not pause playback
        if (!videoView.isPlaying()) {
            videoView.seekTo(stopPosition);
            videoView.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("On Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("On Destroy");
    }
}
