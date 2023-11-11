package com.dicoding.myplantsubmission.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.myplantsubmission.R
import com.dicoding.myplantsubmission.ui.component.AddFavButton
import com.dicoding.myplantsubmission.utils.Injection
import com.dicoding.myplantsubmission.utils.ViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.myplantsubmission.ui.state.UiState

@Composable
fun DetailScreen(
    plantId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit

    ) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlantById(plantId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.id,
                    data.imageUrl,
                    data.name,
                    data.description,
                    data.favorited,
                    onBackClick = navigateBack,
                    onAddFavorite = { id ->
                        viewModel.addToFavorite(plantId = id)
                    },
                    navigateToFav = navigateToFavorite
                )

            }

            is UiState.Error -> {
                val context = LocalContext.current
                Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show()
            }
        }
    }

}

@Composable
fun DetailContent(
    id: Long,
    imageUrl: String,
    name: String,
    description: String,
    favorited: Boolean,
    onBackClick: () -> Unit,
    onAddFavorite: (id: Long) -> Unit,
    navigateToFav: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp)
                        .clickable { onBackClick() },
                    tint = MaterialTheme.colorScheme.onSurface
                    )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            AddFavButton(
                text = if (favorited) stringResource(R.string.unadd_favorite) else stringResource(R.string.add_favorite),
                onClick = {
                    onAddFavorite(id)
                    navigateToFav()
                }
            )
        }
    }

}