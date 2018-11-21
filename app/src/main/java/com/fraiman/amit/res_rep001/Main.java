package com.fraiman.amit.res_rep001;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Main extends AppCompatActivity
        implements View.OnClickListener{

    ImageButton ibPhoto, ibVideo, ibGPS;
    ImageView ivAction;
    Intent intPhoto;
    File sdFilePath, sdFile;
    String picPath;
    Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ibGPS= (ImageButton) findViewById(R.id.ibGPS);
        ibGPS.setOnClickListener(this);
        ibPhoto= (ImageButton) findViewById(R.id.ibPhoto);
        ibPhoto.setOnClickListener(this);
        ibVideo= (ImageButton) findViewById(R.id.ibVideo);
        ibVideo.setOnClickListener(this);
        ivAction= (ImageView) findViewById(R.id.ivAction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.send) {
            Intent go=new Intent(this, SendFiles.class);
            startActivity(go);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view==ibPhoto)  {
            if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
                Toast.makeText(this, "ok",Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(this, "Not found camera",Toast.LENGTH_LONG).show();
                return;
            }

            if (!Environment.getExternalStorageState().
                    equals(Environment.MEDIA_MOUNTED))  {
                Toast.makeText(this, "SD-card not in access",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String imageFileName = "my_pic.jpg";
            String myDir="My_pics";
            sdFilePath=Environment.getExternalStorageDirectory();
            sdFilePath=new File(sdFilePath.getAbsolutePath()+"/"+myDir);
            picPath=sdFilePath.toString()+"/"+imageFileName;
            sdFilePath.mkdirs();
            sdFile=new File(sdFilePath,imageFileName);
            outputFileUri = Uri.fromFile(sdFile);
            intPhoto = new Intent
                    (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intPhoto.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(intPhoto, 1);

        }

        if (view==ibVideo)  {
            Intent go=new Intent(this, ForVideo.class);
            startActivity(go);
        }

        if (view==ibGPS)  {
            Intent go=new Intent(this, GPSview.class);
            startActivity(go);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == 1) {
                if(sdFile.exists()){
                    Bitmap bm = BitmapFactory.decodeFile
                            (sdFile.getAbsolutePath());
                    ivAction.setImageBitmap(bm);
                }
            }
    }
}
