package com.maku.myfitness.ui.screens.details

import android.content.res.TypedArray
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
    val image: Painter = painterResource(id = img)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (img_drw, toolbar, column, btn) = createRefs()
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .constrainAs(img_drw) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(toolbar) {
                top.linkTo(img_drw.top)
                end.linkTo(img_drw.end)
                start.linkTo(img_drw.start)
                width = Dimension.fillToConstraints
            }
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

        LazyColumn(
            modifier = Modifier
                .constrainAs(column) {
                    top.linkTo(img_drw.bottom, 8.dp)
                    end.linkTo(img_drw.end)
                    start.linkTo(img_drw.start)
                    width = Dimension.fillToConstraints
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val list = (0..18).map { it.toString() }
            items(count = list.size) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{ }
                        .padding(8.dp, 0.dp, 8.dp, 0.dp),
                    elevation = 10.dp
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val mm: Painter = painterResource(id = R.drawable.ic_launcher_web)
                        Image(
                            painter = mm,
                            contentDescription = "",
                            modifier = Modifier.size(70.dp)
                        )
                        Text(
                            text = list[it],
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
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
            ),
            appState,
            1
        )
    }
}