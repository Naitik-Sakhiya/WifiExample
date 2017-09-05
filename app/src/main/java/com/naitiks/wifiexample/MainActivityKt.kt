package com.naitiks.wifiexample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast

class MainActivityKt : AppCompatActivity() {
    private var wifiManager: WifiManager? = null
    private val MULTI_PER_CODE = 99
    private var status: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val switchCompat = findViewById(R.id.switch_on_off) as SwitchCompat
        status = findViewById(R.id.status) as TextView
        if (wifiManager!!.isWifiEnabled) {
            switchCompat.isEnabled = true
            status!!.text = "ON"
        } else {
            status!!.text = "OFF"
        }
        switchCompat.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                onWifi()
            } else {
                offWifi()
            }
        }
    }


    private fun checkPermission() {
        val chState = ContextCompat.checkSelfPermission(this@MainActivityKt,
                Manifest.permission.CHANGE_WIFI_STATE)

        val acState = ContextCompat.checkSelfPermission(this@MainActivityKt,
                Manifest.permission.ACCESS_WIFI_STATE)

        if (acState != PackageManager.PERMISSION_GRANTED || chState != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivityKt,
                    arrayOf(Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE),
                    MULTI_PER_CODE)

        }
    }

    private fun onWifi() {
        checkPermission()
        wifiManager!!.isWifiEnabled = true
        status!!.text = "ON"
    }

    private fun offWifi() {
        checkPermission()
        wifiManager!!.isWifiEnabled = false
        status!!.text = "OFF"
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MULTI_PER_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults.size > 1
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivityKt, "Permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivityKt, "Permission not granted", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}