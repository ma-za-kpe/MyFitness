package com.maku.myfitness.ui.screens.details.pager

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maku.myfitness.core.navigation.WorkOutNavigationDestination
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.screens.details.WorkOutDetailsDestination

object WorkOutDetailsDescriptionDestination : WorkOutNavigationDestination {
    override val route = "workout_description_item_route"
    override val destination = "workout_description_item_destination"
    const val workoutItemIdArg = "workoutDescriptionItemId"
    const val workoutItemNameIdArg = "workoutItemCategoryIdArg"
}

// TODO: for more advanced deep link navigation setup, refer to: https://www.droidcon.com/2021/12/16/safe-compose-arguments-an-improved-way-to-navigate-in-jetpack-compose-part-1/
fun NavGraphBuilder.workOutDescriptionsDetailsGraph(
    onBackClick: () -> Unit,
    appState: MyFitnessAppState,
) {
    composable(
        route = "${WorkOutDetailsDescriptionDestination.route}/{${WorkOutDetailsDescriptionDestination.workoutItemIdArg}}/{${WorkOutDetailsDescriptionDestination.workoutItemNameIdArg}}",
        arguments = listOf(
            navArgument(WorkOutDetailsDescriptionDestination.workoutItemIdArg) {
                type = NavType.IntType
            },
            navArgument(WorkOutDetailsDescriptionDestination.workoutItemNameIdArg) {
                type = NavType.StringType
            }),
    ) {
        DetailsPager(
            // TODO: we dont need to pass some of these here, especially if we arent using them in the composable function
            onBackClick = onBackClick,
            it.arguments?.getInt(WorkOutDetailsDescriptionDestination.workoutItemIdArg),
            appState,
        )
    }
}

