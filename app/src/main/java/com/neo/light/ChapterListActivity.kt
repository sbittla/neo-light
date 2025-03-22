package com.neo.light

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.neo.light.ui.theme.NeoLightTheme

class ChapterListActivity : ComponentActivity() {

    companion object {
        const val CHAPTER_LIST = "chapter_list"
        const val CHAPTER_TITLE = "chapter_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chapters = intent.getStringArrayListExtra(CHAPTER_LIST)
        val title = intent.getStringExtra(CHAPTER_TITLE) ?: resources.getString(R.string.topics_title)
        val chapterList = chapters?.toList()
            ?: resources.getStringArray(R.array.chapter_list).toList()

        setContent {
            NeoLightTheme(
                darkTheme = false
            ) {
                TopicsListView(chapterList, title)
            }
        }
    }
}

