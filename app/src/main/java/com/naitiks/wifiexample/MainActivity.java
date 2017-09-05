package com.naitiks.wifiexample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WifiManager wifiManager = null;
    private final int MULTI_PER_CODE = 99;
    private TextView status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_on_off);
        status = (TextView) findViewById(R.id.status);
        if(wifiManager.isWifiEnabled()){
            switchCompat.setEnabled(true);
            status.setText("ON");
        }else{
            status.setText("OFF");
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    onWifi();
                }else{
                    offWifi();
                }
            }
        });
    }


    private void checkPermission(){
        int chState = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CHANGE_WIFI_STATE);

        int acState = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_WIFI_STATE);

        if(acState != PackageManager.PERMISSION_GRANTED || chState != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE},
                    MULTI_PER_CODE);

        }
    }

    private void onWifi(){
        checkPermission();
        wifiManager.setWifiEnabled(true);
        status.setText("ON");
    }

    private void offWifi(){
        checkPermission();
        wifiManager.setWifiEnabled(false);
        status.setText("OFF");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTI_PER_CODE: {
                if ((grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        grantResults.length > 1
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}