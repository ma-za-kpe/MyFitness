package com.maku.myfitness.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.screens.home.homeGraphNavigationDestination

@Composable
fun MyFitnessNavHost(
    innerPadding: PaddingValues,
    startDestination: String,
    appState: MyFitnessAppState,
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        HomeGraph(
            startDestination = homeGraphNavigationDestination,
            appState = appState,
            innerPadding = innerPadding
        )
    }
}


