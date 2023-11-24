package com.example.movieslist.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieslist.R
import com.example.movieslist.ui.theme.MoviesListTheme

@Composable
fun ProfileScreen(
    modifier: Modifier
) {
    ProfileContent(modifier = modifier)
}

@Composable
fun ProfileContent(
    modifier: Modifier 
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(painter = painterResource(
            id = R.drawable.foto_saya),
            contentDescription = "profile picture",
            modifier = modifier
                .size(300.dp)
                .clip(CircleShape)
                .align(CenterHorizontally)
        )
        Text(
            text = "Reyhan Herdiyanto",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .align(CenterHorizontally)
        )
        Text(
            text = "reyhanherdiyanto180603@gmail.com",
            fontSize = 20.sp,
            modifier = modifier
                .align(CenterHorizontally)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    MoviesListTheme {
        ProfileContent(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MoviesListTheme {
        ProfileScreen(modifier = Modifier)
    }
}