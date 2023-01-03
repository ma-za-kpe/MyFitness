package com.maku.myfitness.ui.components

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.maku.myfitness.MainActivity
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.ui.theme.MyFitnessTheme

@Composable
fun MainSection(mainActivity: MainActivity) {
    val appState = rememberMyFitnessAppState()
    MyFitnessDrawer(appState, mainActivity)
}

@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    MyFitnessTheme {
        MainSection(mainActivity = MainActivity())
    }
}