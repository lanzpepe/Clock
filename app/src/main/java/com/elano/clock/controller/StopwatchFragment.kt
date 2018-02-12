package com.elano.clock.controller

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.ToggleButton
import com.elano.clock.R
import kotlinx.android.synthetic.main.fragment_stopwatch.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class StopwatchFragment : Fragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener, Runnable {

    private lateinit var mTvHours: TextView
    private lateinit var mTvMinutes: TextView
    private lateinit var mTvSeconds: TextView
    private lateinit var mBtnStartStop: ToggleButton
    private lateinit var mBtnReset: Button
    private lateinit var mHandler: Handler
    private var upTime = 0L; private var startTime = 0L
    private var mHours = 0; private var mMinutes = 0; private var mSeconds = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_stopwatch, container, false)

        mHandler = Handler()
        setViews(rootView)
        mBtnStartStop.setOnCheckedChangeListener(this)
        mBtnReset.setOnClickListener(this)

        return rootView
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (isChecked) {
            true -> {
                startTime = SystemClock.uptimeMillis()
                mHandler.postDelayed(this, 0)
                mBtnReset.isEnabled = false
            }
            else -> {
                mHandler.removeCallbacks(this)
                mBtnStartStop.isEnabled = false
                mBtnReset.isEnabled = true
            }
        }
    }

    override fun onClick(view: View?) {
        mBtnStartStop.isEnabled = true
        mTvSeconds.text = ("%02d").format(Locale.US, 0)
        mTvMinutes.text = ("%02d").format(Locale.US, 0)
        mTvHours.text = ("%02d").format(Locale.US, 0)
    }

    override fun run() {
        upTime = (SystemClock.uptimeMillis() - startTime) / 1000
        mSeconds = upTime.toInt(); mMinutes = mSeconds / 60; mHours = mMinutes / 60
        mSeconds %= 60; mMinutes %= 60; mHours %= 24

        mTvSeconds.text = ("%02d").format(Locale.US, mSeconds)
        mTvMinutes.text = ("%02d").format(Locale.US, mMinutes)
        mTvHours.text = ("%02d").format(Locale.US, mHours)

        mHandler.postDelayed(this, 0)
    }

    private fun setViews(view: View?) {
        mTvHours = view!!.tvHours
        mTvMinutes = view.tvMinutes
        mTvSeconds = view.tvSeconds
        mBtnStartStop = view.btnStartStop
        mBtnReset = view.btnReset
    }

}// Required empty public constructor
