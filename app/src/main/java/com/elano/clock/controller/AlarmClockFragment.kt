package com.elano.clock.controller

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextClock
import android.widget.TimePicker
import android.widget.ToggleButton
import com.elano.clock.R
import kotlinx.android.synthetic.main.fragment_alarm_clock.view.*
import android.content.Intent
import android.icu.util.Calendar
import android.widget.Toast

/**
 * A simple [Fragment] subclass.
 */
class AlarmClockFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private lateinit var mTimePicker: TimePicker
    private lateinit var mAlarmManager: AlarmManager
    private lateinit var mPendingIntent: PendingIntent
    private lateinit var mToggleButton: ToggleButton
    private lateinit var mTextClock: TextClock

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_alarm_clock, container, false)

        mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        setViews(rootView)
        mToggleButton.setOnCheckedChangeListener(this)

        return rootView
    }

    @SuppressLint("NewApi")
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        var time: Long

        if (isChecked) {
            Toast.makeText(context, "ALARM ON", Toast.LENGTH_SHORT).show()
            val calendar = Calendar.getInstance()
            val intent = Intent(context, AlarmClockReceiver::class.java)

            calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.hour)
            calendar.set(Calendar.MINUTE, mTimePicker.minute)
            mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            time = calendar.timeInMillis - calendar.timeInMillis % 60000
            if (System.currentTimeMillis() > time) {
                time += if (calendar.get(Calendar.AM_PM) == 0)
                    1000 * 60 * 60 * 12
                else
                    1000 * 60 * 60 * 24
            }
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 60000, mPendingIntent)
        }
        else {
            mAlarmManager.cancel(mPendingIntent)
            Toast.makeText(context, "ALARM OFF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setViews(view: View?) {
        mTimePicker = view!!.findViewById(R.id.timePicker)
        mToggleButton = view.toggleButton
        mTextClock = view.textClock
    }

}// Required empty public constructor
