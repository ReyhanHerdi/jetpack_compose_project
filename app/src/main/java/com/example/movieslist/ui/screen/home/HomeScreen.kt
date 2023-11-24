package com.example.movieslist.ui.screen.home

import android.app.Application
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieslist.R
import com.example.movieslist.di.Injection
import com.example.movieslist.ui.ViewModelFactory
import com.example.movieslist.ui.theme.MoviesListTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier,
    application: Application,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRespository(application))
    ),
    navigateToDetail: (String) -> Unit
) {
    val sortedMovies by viewModel.sortedMovies.collectAsState()
    val query by viewModel.query

    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                SearchStoryBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }

            sortedMovies.forEach { (initial,movies) ->
                items(movies, key = {it.id}) { movie ->
                    HomeContent(
                        id = movie.id,
                        name = movie.judul,
                        tahunRilis = movie.tahunRilis,
                        posterUrl = movie.poster,
                        modifier = Modifier.fillMaxWidth(),
                        navigateToDetail = navigateToDetail
                    )
                }
            }

        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 96.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
                .size(50.dp)
        ) {
            ScrolltoTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun HomeContent(
    id: String,
    name: String,
    tahunRilis: String,
    posterUrl: String,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            navigateToDetail(id)
            Log.d("NAME", name)
        }
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(130.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = tahunRilis,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ScrolltoTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScrolltoTopBottonPreview() {
    MoviesListTheme {
        ScrolltoTopButton(onClick = {  })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchStoryBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {

    }
}