package com.hennge.sankou.viewtestapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hennge.sankou.sankouview.LicenseItem
import com.hennge.sankou.sankouview.OnListInteraction
import com.hennge.sankou.sankouview.SankouView
import com.hennge.sankou.viewtestapp.ui.theme.SankouViewLibraryTheme

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SankouViewLibraryTheme {
                val navController: NavHostController = rememberNavController()
                val items = SankouView.getLicenseItems(this@MainActivity, "app/cash/licensee/artifacts.json")

                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = {
                            Text("Test Activity")
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
                                    //currentTitle = "Open source licenses"
                                } else {
                                    //For now do nothing...
                                }
                            }) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "back",
                                    tint = MaterialTheme.colorScheme.onBackground)
                            }
                        })

                })  { innerPadding ->
                    NavHost(
                        navController = navController,
                        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                        startDestination = Screen.MainScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.MainScreen.route) {
                            StartScreen(innerPadding, onLaunchClick = object: ButtonClickListener{
                                override fun onActivityBtnClicked() {
                                    SankouView.launchLicenseActivity(this@MainActivity)
                                }

                                override fun onComposeListBtnClicked() {
                                    navController.navigate(Screen.ComposeListItem.route)
                                }
                            })
                        }
                        composable(Screen.ComposeListItem.route) {
                            Box {
                                SankouView.LicenseListPage(items=items, listener = object: OnListInteraction{
                                    override fun onListItemClick(
                                        clickedItem: LicenseItem,
                                        index: Int
                                    ) {
                                        navController.navigate("${Screen.ComposeDetailItem.route}/$index")
                                    }
                                })
                            }
                        }
                        composable("${Screen.ComposeDetailItem.route}/{licenseIndex}") { stackEntry ->
                            val index: Int = stackEntry.arguments?.getString("licenseIndex")?.toInt() ?: 0
                            Box {
                                SankouView.LicenseItemPage(items[index])
                            }
                        }
                    }
                }
            }
        }
    }
}

interface ButtonClickListener {
    fun onActivityBtnClicked()
    fun onComposeListBtnClicked()
}

@Composable
fun StartScreen(innerPadding: PaddingValues, onLaunchClick: ButtonClickListener) {
    Column {
        Button(modifier=Modifier.padding(15.dp), onClick = {
            onLaunchClick.onActivityBtnClicked()

        }) { Text("Show Activity Form") }
        Button(modifier=Modifier.padding(15.dp), onClick = {
            onLaunchClick.onComposeListBtnClicked()

        }) { Text("Show List Form (Compose)") }
    }
}