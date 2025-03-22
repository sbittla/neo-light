package com.neo.light

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.light.ui.theme.NeoLightTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val context = LocalContext.current

    LaunchedEffect(true) {
        delay(4000)  // 3 seconds delay

        context.startActivity(Intent(context, ChapterListActivity::class.java))
        (context as Activity).finish()
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Image(modifier = Modifier.padding(20.dp),painter = painterResource(id = R.drawable.spash_logo),contentScale = ContentScale.Fit,contentDescription = "Splash Logo")
        Image(modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(), painter = painterResource(id = R.drawable.splash_screen),contentScale = ContentScale.FillWidth,contentDescription = "Splash Logo")

        StartTextAnimation()
    }
}

@Composable
fun StartTextAnimation() {
    val text = "Gospel of Neo Vedanta"
    var visibleCount by remember { mutableStateOf(0) }

    // Button to trigger the animation
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {

        // visibleCount = 0
        LaunchedEffect(Unit) {
            for (i in text.indices) {
                delay(120) // Delay between each letter appearing
                visibleCount = i + 1
            }
        }

        // Display the text with animated letters
        Row {

            text.take(visibleCount).forEachIndexed { index, char ->
                Text(
                    text = char.toString(),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 1.dp)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    NeoLightTheme {
        SplashScreen()
    }
}