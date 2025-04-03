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

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDestination

interface LicenseScreenCallback {
    fun onNavigateUpCalled()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseListScreenComposable (items: List<LicenseItem>, theme: ColorScheme, callbacks: LicenseScreenCallback) {
    val navController: NavHostController = rememberNavController()
    var currentTitle by remember { mutableStateOf("Open source licenses") }

    MaterialTheme(
        colorScheme = theme
    ) {
        Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Text(currentTitle)
                    },
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        scrolledContainerColor = MaterialTheme.colorScheme.surface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    ), navigationIcon = {
                            IconButton (onClick = {
                                if (navController.previousBackStackEntry!=null) {
                                    navController.popBackStack()
                                    currentTitle = "Open source licenses"
                                } else {
                                    callbacks.onNavigateUpCalled()
                                }
                            }) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "back",
                                    tint = MaterialTheme.colorScheme.onBackground)
                            }
                        })

            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                startDestination = LicenseScreen.LicenseList.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(LicenseScreen.LicenseList.route) {
                    LicenseListComposable(items = items, modifier = Modifier, listener = object: OnListInteraction{
                        override fun onListItemClick(clickedItem: LicenseItem, index: Int) {
                            currentTitle = items[index].name
                            navController.navigate("${LicenseScreen.LicenseDetail}/$index")
                        }
                    })
                }
                composable("${LicenseScreen.LicenseDetail.route}/{licenseIndex}") { stackEntry ->
                    val index: Int = stackEntry.arguments?.getString("licenseIndex")?.toInt() ?: 0
                    LicensePageItem(items[index])
                }
            }
        }
    }
}

@Composable
internal fun LicenseListComposable (items: List<LicenseItem>, modifier: Modifier = Modifier, listener: OnListInteraction? = null) {
    Column {
        LazyColumn(modifier =
            modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 10.dp, end = 10.dp)

        ) {
            itemsIndexed(items) { index, item ->
                LicenseListItem(item, index, listener=listener)
                if(items.size>1&&index<items.size-1) {
                    HorizontalDivider(Modifier.fillMaxWidth().alpha(0.8f), thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)
                }
            }

            if (items.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(height = 30.dp))
                }
            }
        }
    }
}

interface OnListInteraction {
    fun onListItemClick(clickedItem: LicenseItem, index: Int)
}

@Composable
internal fun LicenseListItem (item: LicenseItem, index: Int, modifier: Modifier = Modifier, listener: OnListInteraction? = null) {
    Box(
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .clickable(listener != null, onClick = {
                listener?.onListItemClick(item, index)
            })
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            Text(
                text = item.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.W600,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp
                ),
            )
            Text(
                text = "(${item.groupId})",
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.W200,
                    fontStyle = FontStyle.Normal,
                    fontSize = 10.sp
                ),
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }
    }
}

@Composable
internal fun LicensePageItem (item: LicenseItem) {
    val localContext = LocalContext.current

    Column(Modifier.fillMaxWidth().padding(15.dp)) {
        Row(Modifier.fillMaxWidth().padding(bottom = 5.dp), verticalAlignment = Alignment.Bottom) {
            Text(
                text = item.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W600,
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "v${item.version}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W200,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }
        LazyRow {
            itemsIndexed(item.spdxLicenses) { index, item ->
                Text(text = item.name,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(start=5.dp, top=5.dp, end=5.dp)
                )
            }
        }

        Text(text = item.scm.url,
            style = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.W800,
                fontStyle = FontStyle.Normal,
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.padding(start=5.dp, top=5.dp, end=5.dp)
                .clickable {
                    try {
                        //val localContext = LocalContext.current
                        val browseIntent =
                            Intent(Intent.ACTION_VIEW, item.scm.url.toUri())
                        localContext.startActivity(browseIntent, null)
                    } catch (e: Exception) {

                    }
                }
        )
    }
}

@Preview  (showBackground = true)
@Composable
fun PreviewLicenseDetailComposable () {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            DarkColors
        } else {
            LightColors
        }
    ) {
        LicensePageItem(
            LicenseItem(
                groupId = "Group 2",
                artifactId = "ArtifactId 2",
                version = "Version 2",
                name = "Name 2",
                spdxLicenses = listOf(
                    LicenseItem.SpdxLicense(
                        identifier = "identifier 2",
                        name = "name 2",
                        url = "https://www.sample.com"
                    ),
                ),
                scm = LicenseItem.Scm(url = "https://www.hennge.com")
            )
        )

    }
}

@Preview
@Composable
fun PreviewLicenseListComposable () {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            DarkColors
        } else {
            LightColors
        }
    ) {
        LicenseListComposable(
            listOf(
                LicenseItem(
                    groupId = "Group 1",
                    artifactId = "ArtifactId 1",
                    version = "Version 1",
                    name = "Name 1",
                    spdxLicenses = listOf(
                        LicenseItem.SpdxLicense(
                            identifier = "identifier 1",
                            name = "name 1",
                            url = "https://www.sample.com"
                        ),
                    ),
                    scm = LicenseItem.Scm(url = "https://www.hennge.com")
                ),
                LicenseItem(
                    groupId = "Group 2",
                    artifactId = "ArtifactId 2",
                    version = "Version 2",
                    name = "Name 2",
                    spdxLicenses = listOf(
                        LicenseItem.SpdxLicense(
                            identifier = "identifier 2",
                            name = "name 2",
                            url = "https://www.sample.com"
                        ),
                    ),
                    scm = LicenseItem.Scm(url = "https://www.hennge.com")
                )
            )
        )
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewLicenseListItemComposable () {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            DarkColors
        } else {
            LightColors
        }
    ) {
        LicenseListItem(
            LicenseItem(
                groupId = "Group",
                artifactId = "ArtifactId",
                version = "Version",
                name = "Name",
                spdxLicenses = listOf(
                    LicenseItem.SpdxLicense(
                        identifier = "identifier",
                        name = "name",
                        url = "https://www.sample.com"
                    ),
                ),
                scm = LicenseItem.Scm(url = "https://www.hennge.com")
            ),
            index = 0
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewLicenseListItemComposableDark () {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            DarkColors
        } else {
            LightColors
        }
    ) {
        LicenseListItem(
            LicenseItem(
                groupId = "Group",
                artifactId = "ArtifactId",
                version = "Version",
                name = "Name",
                spdxLicenses = listOf(
                    LicenseItem.SpdxLicense(
                        identifier = "identifier",
                        name = "name",
                        url = "https://www.sample.com"
                    ),
                ),
                scm = LicenseItem.Scm(url = "https://www.hennge.com")
            ),
            index = 0
        )
    }
}