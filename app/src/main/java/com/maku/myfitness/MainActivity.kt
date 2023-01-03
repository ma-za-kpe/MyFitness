package com.maku.myfitness

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.maku.myfitness.ui.MyFitnessApp
import com.maku.myfitness.ui.components.MainSection
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.ui.theme.MyFitnessTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainSection(this) // TODO: app this context in appState
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appState = rememberMyFitnessAppState()
    MyFitnessTheme {
        MyFitnessApp(appState)
    }
}