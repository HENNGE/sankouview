package com.hennge.sankou.sankouview

import androidx.annotation.StringRes

sealed class LicenseScreen(val route: String, @StringRes val resourceId: Int, @StringRes val menuName: Int? = null) {
    data object LicenseList: LicenseScreen("licenseList",R.string.license_list_title, R.string.license_list_title)
    data object LicenseDetail: LicenseScreen("licenseDetail",R.string.license_detail_title, R.string.license_detail_title)
}