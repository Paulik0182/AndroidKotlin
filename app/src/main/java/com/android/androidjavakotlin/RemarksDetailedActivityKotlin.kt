package com.android.androidjavakotlin

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class RemarksDetailedActivityKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remarks_detailed)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }
        if (savedInstanceState == null) {
            val remarksDetailed = RemarksDetailedFragmentKotlin()
            remarksDetailed.arguments = getIntent().getExtras()
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, remarksDetailed).commit()
        }
    }
}