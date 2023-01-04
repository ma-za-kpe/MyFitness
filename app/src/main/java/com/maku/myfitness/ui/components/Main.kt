package com.maku.myfitness.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.maku.myfitness.MainActivity
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.ui.theme.MyFitnessTheme

@Composable
fun MainSection() {
    val appState = rememberMyFitnessAppState()
    MyFitnessDrawer(appState)
}

@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    MyFitnessTheme {
        MainSection()
    }
}