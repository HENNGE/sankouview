package com.hennge.sankou.sankouview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class SankouViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //TODO: give way to pull data from a separate project.
        setContent {
            SankouView.ProcessLicenseList("app/cash/licensee/artifacts.json", callback = object: LicenseScreenCallback {
                override fun onNavigateUpCalled() {
                    this@SankouViewActivity.finish()
                }
            })
        }
    }
}