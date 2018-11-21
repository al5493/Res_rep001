package com.fraiman.amit.res_rep001;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;

public class SendFiles extends AppCompatActivity
        implements View.OnClickListener{

    ListView lvAF;
    String myDir="My_pics",filesPath="";
    File sdFilePath;
    String[] allfiles;
    ArrayAdapter adap;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_files);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvAF= (ListView) findViewById(R.id.lvAF);
        sdFilePath=Environment.getExternalStorageDirectory();
        sdFilePath=new File(sdFilePath.getAbsolutePath()+"/"+myDir);
        filesPath=sdFilePath.toString();
        allfiles=sdFilePath.list();
        adap=new ArrayAdapter(this, android.R.layout.simple_list_item_1,allfiles);
        lvAF.setAdapter(adap);
        btnSend= (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("*/*");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email@example.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "body text");
        for (int i=0; i<allfiles.length; i++) {
            String pathToMyAttachedFile = allfiles[i];
            File file = new File(sdFilePath, pathToMyAttachedFile);
            if (!file.exists()) {
                btnSend.append("Problem with "+allfiles[i]+"\n");
                return;
            }
            Uri uri = Uri.fromFile(file);
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        }
        startActivity(emailIntent);
    }
}
