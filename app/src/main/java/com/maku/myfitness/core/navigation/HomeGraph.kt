package com.maku.myfitness.core.navigation

import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.screens.details.WorkOutDetailsDestination
import com.maku.myfitness.ui.screens.details.workOutDetailsGraph
import com.maku.myfitness.ui.screens.home.CategoryScreenGraph
import com.maku.myfitness.ui.screens.home.categoryNavigationRoute

const val homeNavigationRoute = "home_route"

fun NavGraphBuilder.HomeGraph(
    startDestination: String,
    appState: MyFitnessAppState,
    innerPadding: PaddingValues,
) {
    navigation(
        route = homeNavigationRoute,
        startDestination = startDestination
    ) {
        CategoryScreenGraph(
            startDestination = categoryNavigationRoute,
            appState,
            innerPadding,
            onClick = { workoutId, imgId, name ->
                appState.navigate("${WorkOutDetailsDestination.route}/$workoutId/$imgId/$name")
            },
            nestedGraphs = {
                workOutDetailsGraph(
                    onBackClick = { appState.popUp() },
                    appState,
                )
            },
        )
    }
}