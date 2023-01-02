package com.maku.myfitness.ui.components

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maku.myfitness.BuildConfig
import com.maku.myfitness.MainActivity
import com.maku.myfitness.R
import com.maku.myfitness.ui.MyFitnessApp
import com.maku.myfitness.ui.MyFitnessAppState
import com.maku.myfitness.ui.rememberMyFitnessAppState
import com.maku.myfitness.core.navigation.homeNavigationRoute
import com.maku.myfitness.ui.theme.MyFitnessTheme
import kotlinx.coroutines.launch

@Composable
fun MyFitnessDrawer(appState: MyFitnessAppState, context: MainActivity) {
// icons to mimic drawer destinations
    val items = listOf(
        Icons.Default.Home,
        Icons.Default.Email,
        Icons.Default.Share,
        Icons.Default.Star,
        Icons.Default.ExitToApp
    )

    val map = hashMapOf(
        "0Home" to items[0],
        "1Contact Us" to items[1],
        "2Share" to items[2],
        "3Rate Us" to items[3],
        "4Exit App" to items[4]
    )

    val selectedItem = remember { mutableStateOf(map["0Home"]) }
     val appPackageName: String = BuildConfig.APPLICATION_ID

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                DrawerHeader()
                Spacer(Modifier.height(12.dp))
                map.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                item.value,
                                contentDescription = null
                            )},
                        label = { Text(item.key.drop(1)) },
                        selected = item.value == selectedItem.value,
                        onClick = {
                            appState.coroutineScope.launch { appState.drawerState.close() }
                            selectedItem.value = item.value
                            when(item.key){
                                "0Home" -> {
                                    appState.navigate(homeNavigationRoute)
                                }
                                "1Contact Us" -> {
                                    contactUs(context)
                                }
                                "2Share" -> {
                                    shareAppLink(context, appPackageName)
                                }
                                "3Rate Us" -> {
                                    rateUs(context, appPackageName)
                                }
                                "4Exit App" -> {

                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(Modifier.height(12.dp))
                // DrawerFooter()
            }
        },
        content = {
            MyFitnessApp(appState)
        }
    )
}

@Composable
fun DrawerHeader() {
    Row(
        modifier = Modifier.padding(15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val image: Painter = painterResource(id = R.drawable.ic_launcher_web)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            buildAnnotatedString {
                append("My - ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))) {
                    append("Fitness")
                }
            }
        )
    }
}

@Composable
fun DrawerFooter() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val image: Painter = painterResource(id = R.drawable.ic_launcher_web)
        Image(painter = image,contentDescription = "",
      )
        // Text("MyFitness!")
    }
}

fun shareAppLink(context: MainActivity, appPackageName: String) {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    val link = "https://play.google.com/store/apps/details?id=$appPackageName"
    shareIntent.putExtra(Intent.EXTRA_TEXT, link)
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.resources.getString(R.string.app_name))
    shareIntent.type = "text/plain"
    context.startActivity(Intent.createChooser(shareIntent, context.resources.getString(R.string.app_name)))
}

@SuppressLint("QueryPermissionsNeeded")
fun contactUs(context: MainActivity) {
    try {
        val sendIntentGmail = Intent(Intent.ACTION_SEND)
        sendIntentGmail.type = "plain/text"
        sendIntentGmail.setPackage("com.google.android.gm")
        sendIntentGmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("makpalyy@gmail.com"))
        sendIntentGmail.putExtra(
            Intent.EXTRA_SUBJECT,
            context.resources.getString(R.string.app_name) + " - Android"
        )
        context.startActivity(sendIntentGmail)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        Toast.makeText(context, "Gmail app not found on this device", Toast.LENGTH_LONG).show()
    }
}

//// TODO: REPLACE THIS WITH IN APP RATING...
fun rateUs(context: MainActivity, appPackageName: String) {
    val link = "https://play.google.com/store/apps/details?id=$appPackageName"

    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    } catch (anfe: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}

@Preview(showBackground = true)
@Composable
fun MyFitnessDrawerPreview() {
    val appState = rememberMyFitnessAppState()
    MyFitnessTheme {
        MyFitnessDrawer(appState, context = MainActivity())
    }
}