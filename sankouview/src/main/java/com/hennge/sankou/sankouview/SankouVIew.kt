package com.hennge.sankou.sankouview

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

/**
 * Interface for the Sankou View API. Use this class to call the simple view data ite
 */
object SankouView {

    /**
     * Simple method to launch the license info activity
     */
    fun launchLicenseActivity(activity: Activity) {
        activity.startActivity(Intent(activity.baseContext, SankouViewActivity::class.java))
    }

    /**
     * For generating a whole screen from our license asset data. Includes a
     * top bar with navigation and a callback for closing the view.
     */
    @Composable
    fun ProcessLicenseList(assetsLicenseFile: String, callback: LicenseScreenCallback) {

        val items = getLicenseItems(LocalContext.current, assetsLicenseFile)

        val colorScheme = if (isSystemInDarkTheme()) {
            DarkColors
        } else {
            LightColors
        }

        LicenseListScreenComposable(items, colorScheme, callbacks = callback)
    }

    /**
     * Generate a list of license items from the asset license file
     */
    fun getLicenseItems(context: Context, assetsLicenseFile: String): List<LicenseItem> {
        val data = JsonUtils.loadDataListFromAssetsFile<Array<LicenseItem>>(context, assetsLicenseFile)
        return data.toList()
    }

    /**
     * Method to just generate a list composable with click callbacks to allow
     * more direct control of navigation and placement.
     */
    @Composable
    fun LicenseListPage(items: List<LicenseItem>, modifier: Modifier = Modifier, listener: OnListInteraction? = null) {
        LicenseListComposable(items, modifier, listener)
    }

    /**
     * Method to just generate a list composable with click callbacks to allow
     * more direct control of navigation and placement.
     */
    @Composable
    fun LicenseItemPage(item: LicenseItem) {
        LicensePageItem(item)
    }
}