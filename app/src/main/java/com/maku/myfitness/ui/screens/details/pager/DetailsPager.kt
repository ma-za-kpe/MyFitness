package com.maku.myfitness.ui.screens.details.pager

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.maku.myfitness.R
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.rememberMyFitnessAppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@Composable
fun DetailsPager(
    onBackClick: () -> Unit = {},
    id: Int?, // TODO: remove this, logic is already being handled in the view model, also, you could be pagerState in local scope
    appState: MyFitnessAppState,
    homeDetailsDescriptionViewModel: HomeDetailsDescriptionViewModel = hiltViewModel()
) {
    Log.d("TAG", "DetailsPager: id $id")
    val state by homeDetailsDescriptionViewModel.state.collectAsState()
    // val id = homeDetailsDescriptionViewModel.workOutDescriptionId
    when {
        state.loading -> {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        state.workOuts.isNotEmpty() -> {
            DetailsPagerScreen(appState, id, state.workOuts, onBackClick)
        }

        state.workOuts.isEmpty() -> {
            Text(
                text = "Empty List",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
            )
        }
    }
}

@Composable
fun DetailsPagerScreen(
    appState: MyFitnessAppState,
    id: Int?,
    workOutDetails: List<WorkOut>,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            count = workOutDetails.size,
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize(),
            state = appState.pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(
                id,
                workOutDetails[position],
                onBackClick,
                position,
                workOutDetails.size
            )
        }
        LaunchedEffect(Unit) {
            // Later, scroll to page 2
            appState.coroutineScope.launch {
                appState.pagerState.scrollToPage(id!!)
            }
        }

        // please refer to this article for more info: https://levelup.gitconnected.com/create-an-auto-scroll-viewpager-with-transformation-and-ken-burns-effect-in-android-jetpack-compose-efdf46f2e8ed
//        LaunchedEffect(Unit) {
//            while(true) {
//                yield()
//                delay(2000)
//                appState.pagerState.animateScrollToPage(
//                    page = (appState.pagerState.currentPage + 1) % (appState.pagerState.pageCount),
//                    animationSpec = tween(600)
//                )
//            }
//        }
    }
}

@Composable
fun PagerScreen(id: Int?, workOut: WorkOut, onBackClick: () -> Unit, position: Int, size: Int) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        val (toolbar, img_drw, card, previous, next, btn) = createRefs()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp, 32.dp, 8.dp, 0.dp)
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

        val image: Painter = painterResource(id = R.drawable.ic_launcher_web)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .constrainAs(img_drw) {
                    top.linkTo(toolbar.bottom)
                    end.linkTo(toolbar.end)
                    start.linkTo(toolbar.start)
                    width = Dimension.fillToConstraints
                }
                .size(200.dp)
        )

        Card(
            modifier = Modifier
                .constrainAs(card) {
                    top.linkTo(img_drw.bottom, 8.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    // bottom.linkTo(btn.top)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth(),
            // backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            elevation = 10.dp
        ) {
            Column(
                Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = workOut.Excrcise_Name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = workOut.Excrcise_Note,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        Button(
            modifier = Modifier
                .constrainAs(btn) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, 8.dp)
                    width = Dimension.fillToConstraints
                },
            onClick = {
                //your onclick code here
            }) {
            Text(
                text = "${position}/${size}",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun DetailsPagerScreenPreview() {
    val appState = rememberMyFitnessAppState()
    DetailsPagerScreen(
        appState,
        5,
        listOf(
            WorkOut(
                1,
                "Maku",
                "squats",
                "huytfh",
                "4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eget arcu et venenatis. Ut vitae risus et justo consequat venenatis vel eu ante. Nulla dignissim tortor a nibh dictum imperdiet. Cras non enim in urna vehicula lacinia sit amet ac metus. Phasellus nec nunc vel dui tincidunt fringilla",
                "jfhfhj"
            ),
            WorkOut(
                1,
                "Maku",
                "squats",
                "huytfh",
                "4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eget arcu et venenatis. Ut vitae risus et justo consequat venenatis vel eu ante. Nulla dignissim tortor a nibh dictum imperdiet. Cras non enim in urna vehicula lacinia sit amet ac metus. Phasellus nec nunc vel dui tincidunt fringilla",
                "jfhfhj"
            ),
            WorkOut(
                1,
                "Maku",
                "squats",
                "huytfh",
                "4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eget arcu et venenatis. Ut vitae risus et justo consequat venenatis vel eu ante. Nulla dignissim tortor a nibh dictum imperdiet. Cras non enim in urna vehicula lacinia sit amet ac metus. Phasellus nec nunc vel dui tincidunt fringilla",
                "jfhfhj"
            ),
            WorkOut(
                1,
                "Maku",
                "squats",
                "huytfh",
                "4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis porta eget arcu et venenatis. Ut vitae risus et justo consequat venenatis vel eu ante. Nulla dignissim tortor a nibh dictum imperdiet. Cras non enim in urna vehicula lacinia sit amet ac metus. Phasellus nec nunc vel dui tincidunt fringilla",
                "jfhfhj"
            )
        )
    ) {}
}