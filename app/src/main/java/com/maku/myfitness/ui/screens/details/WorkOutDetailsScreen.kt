package com.maku.myfitness.ui.screens.details

import android.content.res.TypedArray
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.maku.myfitness.R
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.core.util.allAssetsFiltered
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.ui.theme.MyFitnessTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun WorkOutDetailsScreen(
    onBackClick: () -> Unit = {},
    appState: MyFitnessAppState,
    imgIndex: Int?,
    onClick: (Int, String) -> Unit,
    homeDetailsViewModel: HomeDetailsViewModel = hiltViewModel()
) {
    // TODO: find and remove repeatetion
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
    val workOutDetails by homeDetailsViewModel.state.collectAsState()
    MenuItemScreen(onBackClick, workOutInfo, appState, imgIndex, workOutDetails, onClick)
}

@Composable
fun MenuItemScreen(
    onBackClick: () -> Unit = {},
    workOutInfo: WorkOut,
    appState: MyFitnessAppState,
    imgIndex: Int?,
    workOutDetails: WorkOutDetailsViewState,
    onClick: (Int, String) -> Unit,
) {
    MenuItemScaffold(
        onBackClick = onBackClick,
        workOutInfo = workOutInfo,
        appState = appState,
        imgIndex = imgIndex,
        workOutDetails = workOutDetails,
        onClick = onClick,
    )
}

@Composable
fun MenuItemScaffold(
    onBackClick: () -> Unit = {},
    workOutInfo: WorkOut,
    appState: MyFitnessAppState,
    imgIndex: Int?,
    workOutDetails: WorkOutDetailsViewState,
    onClick: (Int, String) -> Unit,
) {
    // TODO: remove this warning
    val categoryImage: TypedArray = appState.resources.obtainTypedArray(R.array.category_image)
    val img = categoryImage.getResourceId(imgIndex!!, -1)
    val image: Painter = painterResource(id = img)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (img_drw, toolbar, column, btn, numberOf) = createRefs()
        Image( // TODO: reduce image height to match original app asthetic
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
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(toolbar) {
                    top.linkTo(img_drw.top)
                    end.linkTo(img_drw.end)
                    start.linkTo(img_drw.start)
                    width = Dimension.fillToConstraints
                }
                .padding(16.dp, 32.dp, 16.dp, 0.dp)
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

            Text(
                "${workOutInfo.Name}\n${workOutDetails.workOuts.size} WORKOUTS",
                maxLines = 2,
                fontSize = 24.sp
            )
        }

        // val imageUrl = remember { mutableStateOf("") }

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
            itemsIndexed(workOutDetails.workOuts) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(index, workOutInfo.Name) }
                        .padding(8.dp, 0.dp, 8.dp, 0.dp),
                    elevation = 10.dp
                ) {

                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val detailPagerState = rememberPagerState()
                        // TODO: replace this pager with a ViewFlipper AndroidView or similar library
                        HorizontalPager(
                            count = allAssetsFiltered(
                                workOutDetails.workOuts[index].Excrcise_Name,
                                appState.assets
                            ).size,
                            modifier = Modifier
                                .size(70.dp),
                            state = detailPagerState,
                            verticalAlignment = Alignment.Top
                        ) { position ->
                            AsyncImage(
                                model = allAssetsFiltered(
                                    workOutDetails.workOuts[index].Excrcise_Name,
                                    appState.assets
                                )[position],
                                contentDescription = "Translated description of what the image contains",
                                modifier = Modifier.size(70.dp)
                            )
                        }
                        // please refer to this article for more info: https://levelup.gitconnected.com/create-an-auto-scroll-viewpager-with-transformation-and-ken-burns-effect-in-android-jetpack-compose-efdf46f2e8ed
                        LaunchedEffect(Unit) {
                            while(true) {
                                yield()
                                delay(1000)
                                detailPagerState.animateScrollToPage(
                                    page = (detailPagerState.currentPage + 1) % (detailPagerState.pageCount),
                                )
                            }
                        }
                        Text(
                            text = workOutDetails.workOuts[index].Excrcise_Name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
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
            1,
            WorkOutDetailsViewState( // TODO: clean this by having it in previews util
                false, listOf(
                    WorkOut(
                        1,
                        "Maku",
                        "squats",
                        "huytfh",
                        "4",
                        "dont give up",
                        "jfhfhj"
                    ),
                    WorkOut(
                        1,
                        "Maku",
                        "squats",
                        "huytfh",
                        "4",
                        "dont give up",
                        "jfhfhj"
                    ),
                    WorkOut(
                        1,
                        "Maku",
                        "squats",
                        "huytfh",
                        "4",
                        "dont give up",
                        "jfhfhj"
                    ),
                    WorkOut(
                        1,
                        "Maku",
                        "squats",
                        "huytfh",
                        "4",
                        "dont give up",
                        "jfhfhj"
                    )
                )
            ),
            { _, _ -> {} },
        )
    }
}