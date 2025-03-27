package com.hennge.sankou.viewtestapp

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int, @StringRes val menuName: Int? = null) {
    data object MainScreen: Screen("Main", R.string.main_screen, R.string.main_screen)
    data object ComposeListItem: Screen("ComposeListItem", R.string.list_screen, R.string.list_screen)
    data object ComposeDetailItem: Screen("ComposeDetailItem", R.string.detail_screen, R.string.detail_screen)
}