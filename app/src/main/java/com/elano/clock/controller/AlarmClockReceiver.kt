package com.elano.clock.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.widget.Toast

/**
 * Created by Jess on 2/12/2018.
 */
class AlarmClockReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm worked!", Toast.LENGTH_SHORT).show()
        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if (alarmUri == null)
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val ringTone = RingtoneManager.getRingtone(context, alarmUri)
        ringTone.play()
    }
}