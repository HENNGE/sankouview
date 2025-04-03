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
package com.hennge.sankou.sankouview

import androidx.annotation.StringRes

sealed class LicenseScreen(val route: String, @StringRes val resourceId: Int, @StringRes val menuName: Int? = null) {
    data object LicenseList: LicenseScreen("licenseList",R.string.license_list_title, R.string.license_list_title)
    data object LicenseDetail: LicenseScreen("licenseDetail",R.string.license_detail_title, R.string.license_detail_title)
}