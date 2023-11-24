package com.example.movieslist.ui.screen.detail

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieslist.common.UiState
import com.example.movieslist.database.FavoriteMovie
import com.example.movieslist.di.Injection
import com.example.movieslist.ui.ViewModelFactory
import com.example.movieslist.ui.theme.MoviesListTheme

@Composable
fun DetailScreen(
    id: String,
    application: Application,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRespository(application)
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getMovieById(id)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    id = data.id,
                    judul = data.judul,
                    sinopsis = data.sinopsis,
                    tahunRilis = data.tahunRilis,
                    posterUrl = data.poster,
                    application = application
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    id: String,
    judul: String,
    sinopsis: String,
    tahunRilis: String,
    posterUrl: String,
    modifier: Modifier = Modifier,
    application: Application,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRespository(application)
        )
    ),
) {
    val listFavoriteMovie by viewModel.getFavMovieById(id).observeAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box {
            Column {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = null,
                    modifier = modifier
                        .size(size = 400.dp)
                        .align(CenterHorizontally)
                )
                Spacer(modifier = modifier.padding(8.dp))
                Row {
                    Column {
                        Text(
                            text = judul,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "($tahunRilis)",
                            fontSize = 20.sp,
                        )
                    }
                    Spacer(modifier = modifier
                        .weight(1f)
                        .fillMaxHeight()
                    )

                    if (listFavoriteMovie.isEmpty()) {
                        Image(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = modifier
                                .size(50.dp)
                                .clickable {
                                    try {
                                        viewModel.insertFavoriteMovie(FavoriteMovie(
                                            id,
                                            judul,
                                            sinopsis,
                                            tahunRilis,
                                            posterUrl
                                        ))
                                        Log.d("INSERT", "Berhasil")
                                    } catch (e: Exception) {
                                        Log.d("INSERT", e.toString())
                                    }

                                }
                        )
                    } else {
                        Image(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = modifier
                                .size(50.dp)
                                .clickable {
                                    try {
                                        viewModel.deleteFavoriteMovie(FavoriteMovie(id = id))
                                        Log.d("INSERT", "Berhasil")
                                    } catch (e: Exception) {
                                        Log.d("INSERT", e.toString())
                                    }

                                }
                        )
                    }
                }
                Spacer(modifier = modifier.padding(8.dp))
                Text(
                    text = sinopsis
                )
            }

        }
    }
}
