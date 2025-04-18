/*
 * Copyright (C) 2025 HENNGE K.K.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hennge.oss.sankou.sankouview

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
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
     * An all encompassing composable that handles both list and detail view of licenses.
     * This contains a top bar and navigation view so expects full screen treatment and will look weird inside
     * a separate navigation view. The callback is for intercepting a navigate up request
     * from the contained composable.
     */
    @Composable
    fun GenerateLicenseList(callback: LicenseScreenCallback) {
        ProcessLicenseList(assetsLicenseFile=Constants.LICENSE_DATA_LOCATION, callback=callback)
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
     * Get the list of licence items from the expected location.
     * (Warning: could change in the future)
     */
    fun licenseItems(context: Context): List<LicenseItem> {
        val data = JsonUtils.loadDataListFromAssetsFile<Array<LicenseItem>>(context, Constants.LICENSE_DATA_LOCATION)
        return data.toList()
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