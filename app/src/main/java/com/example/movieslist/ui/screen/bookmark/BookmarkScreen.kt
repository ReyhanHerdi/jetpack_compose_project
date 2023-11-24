package com.example.movieslist.ui.screen.bookmark

import android.app.Application
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieslist.database.FavoriteMovie
import com.example.movieslist.di.Injection
import com.example.movieslist.ui.ViewModelFactory
import com.example.movieslist.ui.screen.detail.DetailViewModel
import com.example.movieslist.ui.screen.home.HomeContent
import com.example.movieslist.ui.screen.home.ScrolltoTopButton
import com.example.movieslist.ui.screen.home.SearchStoryBar
import com.example.movieslist.ui.theme.MoviesListTheme
import kotlinx.coroutines.launch

@Composable
fun BookmarkScreen(
    modifier: Modifier,
    favoriteMovie: FavoriteMovie,
    navigateToDetail: (String) -> Unit,
    application: Application,
    viewModel: BookmarkViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRespository(application)
        )
    )
) {
    Log.d("DARA", "${viewModel.getAllFavoriteMovie()}")
    val listFavoriteMovie by viewModel.getAllFavoriteMovie().observeAsState(initial = emptyList())
    Log.d("DAATA", listFavoriteMovie.toString())
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        val listState = rememberLazyListState()

        if (listFavoriteMovie.isEmpty()) {
            Column {
                Text(
                    text = "Daftar favorit kosong",
                    modifier = modifier
                )
            }
        }

        LazyColumn(
            state = listState
        ) {
            items(listFavoriteMovie, key = { it.id }) { movie ->
                BookmarkContent(
                    id = movie.id,
                    judul = movie.judul,
                    tahunRilis = movie.tanggalRilis,
                    posterUrl = movie.poster,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    application = application
                )
            }
        }
    }
}

@Composable
fun BookmarkContent(
    id: String,
    judul: String,
    tahunRilis: String,
    posterUrl: String,
    modifier: Modifier,
    navigateToDetail: (String) -> Unit,
    application: Application,
    viewModel: BookmarkViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRespository(application)
        )
    )
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            navigateToDetail(id)
        }
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            modifier = modifier
                .padding(8.dp)
                .size(130.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column {
            Text(
                text = judul,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = tahunRilis,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = modifier
            .weight(1f)
            .fillMaxWidth()
        )
        Image(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            modifier = modifier
                .padding(8.dp)
                .size(30.dp)
                .clickable {
                    viewModel.deleteFavoriteMovie(
                        FavoriteMovie(id = id)
                    )
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkContentPreview() {
    MoviesListTheme {
        BookmarkContent(
            id = "",
            judul = "Raid aaa",
            tahunRilis = "2022",
            posterUrl = "aa",
            modifier = Modifier,
            navigateToDetail = {},
            application = Application()
        )
    }
}
