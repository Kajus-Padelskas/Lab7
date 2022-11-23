package com.example.lab7

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class BatteryReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context?, p1: Intent?) {
        val percentage = p1?.getIntExtra("level", 0)
        if (percentage != null) {
            if(percentage <= 30){
                val intent = Intent(p0, MainActivity::class.java).apply {
                    putExtra("switchState", true)
                }
                val pendingIntent = PendingIntent.getActivity(p0, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val notif = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notify = Notification.Builder(p0, "CHANNEL_ID").apply {
                    setSmallIcon(R.mipmap.ic_launcher)
                    setContentTitle("Battery level warning")
                    setContentText("Your Battery level is below 30%")
                    setAutoCancel(true)
                    setContentIntent(pendingIntent)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name: CharSequence = "Channel Name"
                    notif.createNotificationChannel(NotificationChannel("CHANNEL_ID", name, NotificationManager.IMPORTANCE_HIGH))
                }
                notif.notify(1, notify.build())
            }
        }
    }


}