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

import com.squareup.seismic.ShakeDetector;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {
    CameraManager cameraManager;
    SensorManager sensorManager;
    ShakeDetector shakeDetector;
    ImageView actionImageView;
    boolean flashFlag = false;
    LinearLayout Ll;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        actionImageView.setImageResource(R.drawable.flashlight_turn);
                        Ll.setBackgroundColor(Color.WHITE);
                        textView.setTextColor(Color.BLACK);
                        flashFlag = true;
                    } else {
                        cameraManager.setTorchMode("0", false);
                        actionImageView.setImageResource(R.drawable.flashlight);
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