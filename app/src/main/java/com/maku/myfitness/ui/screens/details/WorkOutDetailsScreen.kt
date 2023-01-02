package com.maku.myfitness.ui.screens.details

import android.content.res.TypedArray
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.maku.myfitness.R
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.ui.theme.MyFitnessTheme

@Composable
fun WorkOutDetailsScreen(
    onBackClick: () -> Unit = {},
    id: Int?, // TODO: remove this, logic is already being handled in the viewmodel
    appState: MyFitnessAppState,
    imgIndex: Int?,
    homeDetailsViewModel: HomeDetailsViewModel = hiltViewModel()
    ) {
    // TODO: find all repetition and optimize
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // set transparent color so that our image is visible
        // under the status bar
        systemUiController.setStatusBarColor(
            color = Color.Transparent, darkIcons = true
        )
    }
    val workOutInfo: WorkOut by homeDetailsViewModel.workOutInfo.collectAsState(
        initial = WorkOut(
            0, "", "", "",
            "", "", ""
        )
    )
    MenuItemScreen(onBackClick, workOutInfo, appState, imgIndex)
    // MenuItemScreen(onBackClick)
}

@Composable
fun MenuItemScreen(
    onBackClick: () -> Unit = {},
    workOutInfo: WorkOut,
    appState: MyFitnessAppState,
    imgIndex: Int?,
) {
    MenuItemScaffold(
        onBackClick = onBackClick,
        workOutInfo = workOutInfo,
        appState = appState,
        imgIndex = imgIndex
    )
}

@Composable
fun MenuItemScaffold(
    onBackClick: () -> Unit = {},
    workOutInfo: WorkOut,
    appState: MyFitnessAppState,
    imgIndex: Int?,
    ) {
    // TODO: remove this warning
    val categoryImage: TypedArray = appState.resources.obtainTypedArray(R.array.category_image)
    val img = categoryImage.getResourceId(imgIndex!!, -1)

    LazyColumn(
        modifier = Modifier
            .paint(
                painterResource(
                    id = img
                ),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.FillWidth
            )
            .systemBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            WorkOutItemToolbar(
                onBackClick = onBackClick,
            )
        }
    }
}

@Composable
fun WorkOutItemToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        IconButton(
            onClick = {
                onBackClick()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(id = R.string.back)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MenuItemScaffoldPreview() {
    val appState = rememberMyFitnessAppState()
    MyFitnessTheme {
        MenuItemScaffold(
            {},
            WorkOut(
                0, "", "", "",
                "", "", ""
            )
            ,
            appState,
            1
        )
    }
}