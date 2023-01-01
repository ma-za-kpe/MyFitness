package com.maku.myfitness.ui

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.maku.myfitness.R
import com.maku.myfitness.core.navigation.MyFitnessNavHost
import com.maku.myfitness.core.navigation.homeNavigationRoute
import com.maku.myfitness.ui.screens.components.FitnessTopAppBar
import com.maku.myfitness.ui.screens.details.WorkOutDetailsDestination.destination
import com.maku.myfitness.ui.theme.MyFitnessTheme
import kotlinx.coroutines.launch

@Composable
fun MyFitnessApp(appState: MyFitnessAppState) {
    MyFitnessTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            Log.d("TAG", "MyFitnessApp path: ${appState.currentDestination }")
            Scaffold(
                topBar = {
                    // Show the top app bar on top level destinations.
                    val destination = appState.currentTopLevelDestination
                    if (destination != null) {
                        TopAppBar(
                            title = {
                                Text(
                                    "MyFitness App",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    if (appState.drawerState.isClosed){
                                        appState.coroutineScope.launch {
                                            appState.drawerState.open()
                                        }
                                    } else {
                                        appState.coroutineScope.launch {
                                            appState.drawerState.close()
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )
                    }
                },
                content = { innerPadding ->
                    MyFitnessNavHost(
                        innerPadding,
                        homeNavigationRoute,
                        appState
                    )
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyFitnessAppPreview() {
    MyFitnessTheme {
        val appState = rememberMyFitnessAppState()
        MyFitnessApp(appState)
    }
}
