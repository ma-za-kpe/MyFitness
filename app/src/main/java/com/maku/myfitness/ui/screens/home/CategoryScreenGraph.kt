package com.maku.myfitness.ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.maku.myfitness.ui.MyFitnessAppState

const val categoryNavigationRoute = "category_route"
const val homeGraphNavigationDestination = "home_destination_route"

fun NavGraphBuilder.CategoryScreenGraph(
    startDestination: String,
    appState: MyFitnessAppState,
    innerPadding: PaddingValues,
    onClick: (Int, Int) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = homeGraphNavigationDestination,
        startDestination = startDestination
    ) {
        composable(route = startDestination) {
            Home(innerPadding, appState, onClick)
        }
        nestedGraphs()
    }
}