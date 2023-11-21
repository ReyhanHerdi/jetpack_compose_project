package com.example.movieslist.ui.screen.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookmarkScreen(
    modifier: Modifier
) {
    BookmarkContent(modifier = modifier)
}

@Composable
fun BookmarkContent(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Text(text = "Halaman Bookmark")
    }
}
