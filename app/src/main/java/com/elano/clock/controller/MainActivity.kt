package com.elano.clock.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.elano.clock.R
import com.elano.clock.adapter.SectionsPageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: SectionsPageAdapter
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdapter = SectionsPageAdapter(supportFragmentManager)
        mViewPager = viewPager
        addFragmentTab()
        tabs.setupWithViewPager(mViewPager)
    }

    private fun addFragmentTab() {
        mAdapter.addFragment(AlarmClockFragment(), "ALARM CLOCK")
        mAdapter.addFragment(StopwatchFragment(), "STOPWATCH")
        mViewPager.adapter = mAdapter
    }
}