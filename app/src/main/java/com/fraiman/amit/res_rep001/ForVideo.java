package com.fraiman.amit.res_rep001;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class ForVideo extends AppCompatActivity {

    Intent goVideo;
    VideoView vivi;
    File sdFilePath, sdFile;
    Uri outputFileUri;
    String videoPath;
    Button btnSR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vivi= (VideoView) findViewById(R.id.vivi);

        String videoFileName = "my_video.mp4";
        String myDir="My_pics";
        sdFilePath= Environment.getExternalStorageDirectory();
        sdFilePath=new File(sdFilePath.getAbsolutePath()+"/"+myDir);
        videoPath=sdFilePath.toString()+"/"+videoFileName;
        sdFilePath.mkdirs();
        sdFile=new File(sdFilePath,videoFileName);
        outputFileUri = Uri.fromFile(sdFile);

        goVideo=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        goVideo.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        goVideo.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(goVideo,1);

        btnSR= (Button) findViewById(R.id.btnSR);
        btnSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vivi.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==RESULT_OK) {
            //Uri videoUri=data.getData();
            vivi.setVideoPath(videoPath);
            //vivi.setVideoURI(videoUri);
        }
        else finish();
    }

}
