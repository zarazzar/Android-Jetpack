package com.dicoding.myplantsubmission.ui.screen.favorite

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.myplantsubmission.utils.Injection
import com.dicoding.myplantsubmission.utils.ViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.myplantsubmission.R
import com.dicoding.myplantsubmission.data.Plant
import com.dicoding.myplantsubmission.ui.component.FavoriteItem
import com.dicoding.myplantsubmission.ui.state.UiState

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedFavorite()
            }

            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    NoData()
                } else {
                    FavoritedContent(
                        plants = uiState.data,
                        navigateToDetail = navigateToDetail,
                        favorited = { id ->
                            viewModel.addToFavorite(plantId = id)
                            viewModel.getAddedFavorite()
                        }
                    )
                }
            }

            is UiState.Error -> {
                val context = LocalContext.current
                Toast.makeText(
                    context,
                    stringResource(R.string.something_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun NoData(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().testTag("EmptyTag"), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.succulent), contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                )
            Text(text = stringResource(R.string.add_your_favorite))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritedContent(
    plants: List<Plant>,
    favorited: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.top_bar),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()


        ) {
            items(plants, key = { it.id }) { item ->
                FavoriteItem(
                    plantId = item.id,
                    imageUrl = item.imageUrl,
                    name = item.name,
                    onItemChanged = favorited,
                    modifier = Modifier
                        .clickable {
                            navigateToDetail(item.id)
                        }
                )
                Divider()
            }

        }
    }

}