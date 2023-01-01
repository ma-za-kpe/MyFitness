package com.maku.myfitness.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.maku.myfitness.R.string.home

enum class TopLevelDestination(
    val icon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int
) {
    ABODE(
        icon = Icons.Filled.Menu,
        iconTextId = home,
        titleTextId = home
    )
}