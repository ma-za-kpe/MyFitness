package com.maku.myfitness.ui.screens.home

import android.content.res.TypedArray
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maku.myfitness.R
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.ui.theme.MyFitnessTheme

@Composable
fun Home(
    innerPadding: PaddingValues,
    appState: MyFitnessAppState,
    onClick: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    ) {
    val state by homeViewModel.state.collectAsState()
    HomeRoute(innerPadding, state, appState, onClick)
}

@Composable
fun HomeRoute(innerPadding: PaddingValues, state: HomeViewState, appState: MyFitnessAppState,
              onClick: (Int) -> Unit,) {
    when{
        state.loading -> {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        state.workOuts.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Spacer(
                        Modifier
                            .size(50.dp)
                    )
                }
                itemsIndexed(state.workOuts) { index, item ->
                    CategoryItem(
                        state.workOuts[index].Name,
                        appState,
                        index,
                        onClick = {
                            onClick(state.workOuts[index].ID)
                        })
                }
            }
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
fun CategoryItem(
    name: String,
    appState: MyFitnessAppState,
    index: Int,
    onClick: () -> Unit,
) {
    // TODO: find a better way to handle this
    val categoryImage: TypedArray = appState.resources.obtainTypedArray(R.array.category_image)
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .fillMaxWidth()
        .height(200.dp)
    ) {
        val img = categoryImage.getResourceId(index, -1)
        val image: Painter = painterResource(id = img) // TODO: this crushes the app at the 8 or 9th image scroll with error 2022-12-30 23:14:27.860 18249-18249 Error: stack=java.lang.IllegalArgumentException: Only VectorDrawables and rasterized asset types are supported ex. PNG, JPG
        val error: Painter = painterResource(id = R.drawable.image_not_found)

        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .clickable { onClick() }
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                0.5f to Color.Black.copy(alpha = 0F),
                                2F to Color.Black
                            )
                        )
                    }
                }
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

//        AsyncImage(
//            model = image,
//            contentDescription = null,
//            modifier = Modifier
//                .drawWithCache {
//                    onDrawWithContent {
//                        drawContent()
//                        drawRect(
//                            Brush.verticalGradient(
//                                0.5f to Color.Black.copy(alpha = 0F),
//                                2F to Color.Black
//                            )
//                        )
//                    }
//                }
//                .fillMaxSize(),
//            contentScale = ContentScale.FillBounds,
//            error = painterResource(R.drawable.image_not_found)
//        )

        Text(
            text = "$name\nWORKOUT",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.W400,
            fontSize = 18.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryItemPreview() {
    val appState = rememberMyFitnessAppState()
    MyFitnessTheme {
        CategoryItem("Mazakpe", appState, 0, {})
    }
}

@Composable
@Preview(showBackground = true)
fun HomeRoutePreview() {
    MyFitnessTheme {
        val appState = rememberMyFitnessAppState()
        HomeRoute(
            innerPadding = PaddingValues(10.dp),
            state = HomeViewState(false, listOf(
                WorkOut(
                    1,
                    "Maku",
                    "Pull Ups",
                "huytfh",
                    "4",
                    "dont give up",
                    "jfhfhj"
                ),
                WorkOut(
                    1,
                    "Maku",
                    "Pull Ups",
                    "huytfh",
                    "4",
                    "dont give up",
                    "jfhfhj"
                ),
                WorkOut(
                    1,
                    "Maku",
                    "Pull Ups",
                    "huytfh",
                    "4",
                    "dont give up",
                    "jfhfhj"
                ),
                WorkOut(
                    1,
                    "Maku",
                    "Pull Ups",
                    "huytfh",
                    "4",
                    "dont give up",
                    "jfhfhj"
                )
            )),
            appState = appState,
            {}
        )
    }
}