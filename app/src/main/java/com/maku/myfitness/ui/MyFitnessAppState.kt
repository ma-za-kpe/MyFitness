package com.maku.myfitness.ui

import android.content.res.AssetManager
import android.content.res.Resources
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.maku.myfitness.core.navigation.TopLevelDestination
import com.maku.myfitness.core.util.snackbar.SnackbarManager
import com.maku.myfitness.core.util.snackbar.SnackbarMessage.Companion.toMessage
import com.maku.myfitness.ui.screens.home.categoryNavigationRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun rememberMyFitnessAppState(
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    assets: AssetManager = assets(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    systemUiController: SystemUiController = rememberSystemUiController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    pagerState: PagerState = rememberPagerState()
): MyFitnessAppState {
    return remember(
        navController,
        snackbarManager,
        resources,
        assets,
        coroutineScope,
        drawerState,
        systemUiController,
        snackbarHostState,
        pagerState
    ) {
        MyFitnessAppState(
            navController,
            snackbarManager,
            resources,
            assets,
            coroutineScope,
            drawerState,
            systemUiController,
            snackbarHostState,
            pagerState
        )
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
@ReadOnlyComposable
fun assets(): AssetManager {
    LocalConfiguration.current
    return LocalContext.current.assets
}

@Stable
class MyFitnessAppState(
    val navController: NavHostController,
    val snackbarManager: SnackbarManager,
    val resources: Resources,
    val assets: AssetManager,
    val coroutineScope: CoroutineScope,
    val drawerState: DrawerState,
    val systemUiController: SystemUiController,
    val snackbarHostState: SnackbarHostState,
    val pagerState: PagerState,
) {

    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                snackbarHostState.showSnackbar(text)
            }
        }
    }

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            categoryNavigationRoute -> TopLevelDestination.ABODE
            else -> null
        }


    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

}
