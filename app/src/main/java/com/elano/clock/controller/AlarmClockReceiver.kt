package com.elano.clock.controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Jess on 2/12/2018.
 */
class AlarmClockReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val result = intent?.getStringExtra("extras")
        val serviceIntent = Intent(context, RingtoneService::class.java)

        serviceIntent.putExtra("extras", result)
        context?.startService(serviceIntent)
    }
}