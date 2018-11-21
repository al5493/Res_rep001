package com.fraiman.amit.res_rep001;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GPSview extends AppCompatActivity
        implements View.OnClickListener{

    Button btnGeo;
    TextView tvGeo;
    WebView wvMap;
    LocationManager locman;
    LocationListener LoLi;
    String pos1, pos2, geo1, geo2;
    private Bitmap myBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnGeo = (Button) findViewById(R.id.btnGeo);
        btnGeo.setOnClickListener(this);
        tvGeo = (TextView) findViewById(R.id.tvGeo);

        wvMap = (WebView) findViewById(R.id.wvMap);
        wvMap.getSettings().setJavaScriptEnabled(true);
        wvMap.setWebViewClient(new MyWebViewClient());

        locman = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        LoLi = new mylocationlistener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locman.requestLocationUpdates
                (LocationManager.GPS_PROVIDER, 5000, 10, LoLi);

    }

    @Override
    public void onClick(View view) {
        myBitmap = captureScreen(wvMap);
        Toast.makeText(this, "Screenshot captured..!", Toast.LENGTH_LONG).show();
        if (myBitmap != null) {
            //save image to SD card
            saveImage(myBitmap);
        }
        Toast.makeText(this, "Screenshot saved..!", Toast.LENGTH_LONG).show();
        finish();
    }

    public static Bitmap captureScreen(View v) {
        Bitmap screenshot = null;
        try {
            if(v!=null) {
                screenshot = Bitmap.createBitmap
                        (v.getMeasuredWidth(),v.getMeasuredHeight(),
                                Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return screenshot;
    }

    public static void saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
        String shootFileName = "my_shoot.jpg";
        String myDir="My_pics";
        File f = new File(Environment.getExternalStorageDirectory()+
                            "/"+myDir+"/"+shootFileName);
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class mylocationlistener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                geo1=""+location.getLatitude();
                geo2=""+location.getLongitude();
                pos1 = "Latitude=" + geo1;
                pos2 = "Longitude=" + geo2;
                tvGeo.setText(pos1+"\n"+pos2);
                String goMap = "https://www.google.com/maps/@" + geo1 + "," + geo2 + "," + 21 + "z";
                wvMap.loadUrl(goMap);
            } else {
                tvGeo.setText("NULL");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

        private class MyWebViewClient extends WebViewClient {

        }
}
