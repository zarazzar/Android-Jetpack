package com.dicoding.myplantsubmission.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dicoding.myplantsubmission.ui.component.PlantItem
import com.dicoding.myplantsubmission.utils.Injection
import com.dicoding.myplantsubmission.utils.ViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.myplantsubmission.R
import com.dicoding.myplantsubmission.data.Plant
import com.dicoding.myplantsubmission.ui.component.AppSearchBar
import com.dicoding.myplantsubmission.ui.state.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    viewModel.searchPlant(query)

    Box(modifier = modifier) {
        AppSearchBar(query = query, onQueryChange = viewModel::searchPlant)

        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllPlants()
                }

                is UiState.Success -> {
                    HomeContent(
                        plants = uiState.data,
                        navigateToDetail = navigateToDetail
                    )
                }

                is UiState.Error -> {
                    val context = LocalContext.current
                    Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    plants: List<Plant>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 100.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("PlantList")
    ) {

        items(plants, key = { it.id }) { data ->
            PlantItem(
                imageUrl = data.imageUrl,
                name = data.name,
                description = data.description,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
        }
    }
}