package com.maku.myfitness.ui.screens.details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maku.myfitness.core.navigation.WorkOutNavigationDestination
import com.maku.myfitness.ui.MyFitnessAppState

object WorkOutDetailsDestination : WorkOutNavigationDestination {
    override val route = "workout_item_route"
    override val destination = "workout_item_destination"
    const val workoutItemIdArg = "workoutItemId"
}

fun NavGraphBuilder.workOutDetailsGraph(
    onBackClick: () -> Unit,
    appState: MyFitnessAppState
) {
    composable(
        route = "${WorkOutDetailsDestination.route}/{${WorkOutDetailsDestination.workoutItemIdArg}}",
        arguments = listOf(
            navArgument(WorkOutDetailsDestination.workoutItemIdArg) {
                type = NavType.IntType
            }
        )
    ) {
        WorkOutDetailsScreen(
            onBackClick = onBackClick,
            it.arguments?.getInt(WorkOutDetailsDestination.workoutItemIdArg),
            appState)
    }
}
