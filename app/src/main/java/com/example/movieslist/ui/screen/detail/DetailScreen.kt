package com.example.movieslist.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieslist.common.UiState
import com.example.movieslist.di.Injection
import com.example.movieslist.ui.ViewModelFactory
import com.example.movieslist.ui.theme.MoviesListTheme

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRespository()
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
                    judul = data.judul,
                    sinopsis = data.sinopsis,
                    tahunRilis = data.tahunRilis,
                    posterUrl = data.poster
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    judul: String,
    sinopsis: String,
    tahunRilis: String,
    posterUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
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
                Text(
                    text = judul,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.align(CenterHorizontally),
                )
                Text(
                    text = "($tahunRilis)",
                    fontSize = 20.sp,
                    modifier = modifier.align(CenterHorizontally)
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text(
                    text = sinopsis
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    MoviesListTheme {
        DetailContent(
            judul = "The Raid",
            sinopsis = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet diam massa. " +
                    "Nullam enim ex, dictum elementum sodales sit amet, pretium non risus. In molestie dolor mi, " +
                    "et mollis ipsum convallis ut. Cras aliquet magna sed tortor pretium finibus. " +
                    "Curabitur arcu turpis, tristique id neque sed, sagittis venenatis urna. " +
                    "Curabitur diam nisi, luctus non neque sit amet, semper egestas velit. " +
                    "ed ullamcorper condimentum lacinia. Suspendisse quis porta magna. Pellentesque " +
                    "sollicitudin dictum hendrerit. Ut scelerisque tellus eget erat sodales, eu pulvinar metus rhoncus.",
            tahunRilis = "2013",
            posterUrl = "titanic"
        )
    }
}