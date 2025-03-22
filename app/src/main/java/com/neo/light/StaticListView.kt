package com.neo.light

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neo.light.ChapterListActivity.Companion.CHAPTER_LIST
import com.neo.light.ChapterListActivity.Companion.CHAPTER_TITLE
import com.neo.light.ui.theme.NeoLightTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicsListView(items: List<String>, title: String) {
    var bottomSheetState by remember { mutableStateOf(false) }
    Column {
        val context = LocalContext.current
        if (title !=  context.resources.getString(R.string.topics_title)) {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.top_bar_color),
                    titleContentColor = colorResource(R.color.white)

                ),
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? Activity)?.finish()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        } else {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    )
                },
                actions = {
                    IconButton(onClick = {
                        bottomSheetState = true
                    }) {
                        Icon(tint = colorResource(R.color.white),
                            painter = painterResource(id = R.drawable.ic_info_button),
                            contentDescription = "Information"
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.top_bar_color),
                    titleContentColor = colorResource(R.color.white)

                )
            )
        }

        if (bottomSheetState) {
            ModalBottomSheet(
                onDismissRequest = { bottomSheetState = false }
            ) {
                BottomSheetContent()
            }
        }

        // LazyColumn to display list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {
            items(items.size) { index ->
                val backgroundColor =
                    if (index % 2 == 0) colorResource(R.color.list_item_color_one) else
                        colorResource(R.color.list_item_color_two)
                TopicsItemView(item = items[index], backgroundColor)
            }
        }
    }

}

@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            val listItems = listOf("Dedication", "Preface")
            items(listItems.size) { index ->
                val backgroundColor =
                    if (index % 2 == 0) colorResource(R.color.list_item_color_one) else
                        colorResource(R.color.list_item_color_two)
                TopicsItemView(item = listItems[index], backgroundColor)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}

@Composable
fun TopicsItemView(item: String, backgroundColor: Color) {
    val context = LocalContext.current
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .clickable {
                    if (item == "Beloved Perceptions" || item == "The Cosmic Void") {
                        val list : Array<String> = if (item == "Beloved Perceptions")
                            context.resources.getStringArray(R.array.chapter_list_beloved_perceptions)
                        else
                            context.resources.getStringArray(R.array.chapter_list_the_cosmic_void)

                        context.startActivity(
                            Intent(
                                context,
                                ChapterListActivity::class.java
                            ).apply {
                                putStringArrayListExtra(CHAPTER_LIST, list.toCollection(ArrayList()))
                                putExtra(CHAPTER_TITLE, item)
                            })
                    } else {
                        val intent = Intent(context, PDFViewerActivity::class.java)
                        intent.putExtra(PDFViewerActivity.PDF_NAME, item)
                        context.startActivity(intent)
                    }


                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(0.9f),
                style = MaterialTheme.typography.bodyLarge
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow",
                modifier = Modifier
                    .size(24.dp)
                    .weight(0.1f),
                tint = Color.Gray
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NeoLightTheme {
        TopicsListView(
            listOf(
                "Mindful Meditation",
                "List of 10 Chapters:",
                "Just in a Few Words",
                "For Valiant Spiritual Travelers",
                "The Cosmic Tune",
                "Origin of the Sustained Motion",
                "Who is Awake while Asleep",
                "The Ultimate Service",
                "The Cosmic Void",
                "Divine Guidelines"
            ), "TITLE"
        )
    }
}