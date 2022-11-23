package com.example.lab7

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    lateinit var switch : SwitchCompat
    lateinit var batteryReceiver: BroadcastReceiver
    var isOnPause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val state = intent.extras?.getBoolean("switchState")
        switch = findViewById(R.id.switch1)
        batteryReceiver = BatteryReceiver()
//        if(state != null){
//            switch.isChecked = state
//        }
        switch.setOnCheckedChangeListener { _, b ->
            isOnPause = b
            if (b){
                trackBatteryLevel()
            } else {
                unregisterBatteryTracker()
            }
        }
    }

    private fun unregisterBatteryTracker() {
        unregisterReceiver(batteryReceiver)
    }

    private fun trackBatteryLevel() {
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }
}