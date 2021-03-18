package com.example.flashtothenorth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.seismic.ShakeDetector;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {
    CameraManager cameraManager;
    SensorManager sensorManager;
    ShakeDetector shakeDetector;
    ImageView actionImageView;
    boolean flashFlag = false;
    LinearLayout Ll;
    TextView textView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3289952769891594~9413588846");
        mAdView = findViewById(R.id.adView);


        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Ll = (LinearLayout) findViewById(R.id.Ll);
        textView = findViewById(R.id.text);
        actionImageView = findViewById(R.id.action_image);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

    }

    @Override
    public void hearShake() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                try {
                    if (!flashFlag) {
                        cameraManager.setTorchMode("0", true);
                        Glide.with(this).load(R.drawable.flashlight_turn).into(actionImageView);
                        Ll.setBackgroundColor(Color.WHITE);
                        textView.setTextColor(Color.BLACK);
                        flashFlag = true;
                    } else {
                        cameraManager.setTorchMode("0", false);
                        Glide.with(this).load(R.drawable.flashlight).into(actionImageView);
                        Ll.setBackgroundColor(Color.BLACK);
                        textView.setTextColor(Color.WHITE);
                        flashFlag = false;

                    }

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "This device has no flash", Toast.LENGTH_LONG).show();

            }

        } else {
            Toast.makeText(this, "This device has no camera", Toast.LENGTH_LONG).show();
        }
    }
}