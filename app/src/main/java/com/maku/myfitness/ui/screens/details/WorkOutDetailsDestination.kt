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
    const val workoutImageIdArg = "workoutImageId"
    const val workoutImageNameArg = "workoutImageName"
}

// TODO: for more advanced deep link navigation setup, refer to: https://www.droidcon.com/2021/12/16/safe-compose-arguments-an-improved-way-to-navigate-in-jetpack-compose-part-1/
fun NavGraphBuilder.workOutDetailsGraph(
    onBackClick: () -> Unit,
    appState: MyFitnessAppState
) {
    composable(
        route = "${WorkOutDetailsDestination.route}/{${WorkOutDetailsDestination.workoutItemIdArg}}/{${WorkOutDetailsDestination.workoutImageIdArg}}/{${WorkOutDetailsDestination.workoutImageNameArg}}",
        arguments = listOf(
            navArgument(WorkOutDetailsDestination.workoutItemIdArg) {
                type = NavType.IntType
            },
            navArgument(WorkOutDetailsDestination.workoutImageIdArg) {
                type = NavType.IntType
            },
            navArgument(WorkOutDetailsDestination.workoutImageNameArg) {
                type = NavType.StringType
            }
        )
    ) {
        WorkOutDetailsScreen( // TODO: we dont need to pass some of these here, especially if we arent using them in the composable function
            onBackClick = onBackClick,
            it.arguments?.getInt(WorkOutDetailsDestination.workoutItemIdArg),
            appState,
            it.arguments?.getInt(WorkOutDetailsDestination.workoutImageIdArg),
        )
    }
}

