package com.elano.clock.controller

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder
import com.elano.clock.R

/**
 * Created by Jess on 2/12/2018.
 */
class RingtoneService: Service() {

    private var mRingtone: Ringtone? = null
    private var startId = 0
    private var isRunning = false

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val state = intent?.getStringExtra("extras")!!

        when (state) {
            "Alarm Off" -> this.startId = 0
            "Alarm On" -> this.startId = 1
            else -> this.startId = 0
        }

        if (!this.isRunning && this.startId == 1) {
            setAlarm()
            this.isRunning = true
            this.startId = 0
            setNotification()
        }
        else if (this.isRunning && this.startId == 0) {
            mRingtone?.stop()
            this.isRunning = false
            this.startId = 0
        }
        else if (!this.isRunning && this.startId == 0) {
            this.isRunning = false
            this.startId = 0
        }
        else {
            this.isRunning = true
            this.startId = 1
        }

        return START_NOT_STICKY
    }

    @SuppressLint("NewApi")
    private fun setNotification() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this,0,intent,0)
        val notification = Notification.Builder(this, "MyChannelId_01")
                .setContentTitle("An alarm is running!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Click Me")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()

        nm.notify(0, notification)
    }

    private fun setAlarm() {
        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if (alarmUri == null)
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        mRingtone = RingtoneManager.getRingtone(baseContext, alarmUri)
        mRingtone?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isRunning = false
    }
}