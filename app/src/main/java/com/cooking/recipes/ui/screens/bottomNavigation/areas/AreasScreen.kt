package com.cooking.recipes.ui.screens.bottomNavigation.areas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cooking.recipes.helpers.utils.hasInternetConnection
import com.cooking.recipes.ui.navigation.BottomNavScreens
import com.cooking.recipes.ui.screens.NoConnectionScreen
import com.cooking.recipes.ui.viewmodels.AreaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreasScreen(
    areaViewModel: AreaViewModel = hiltViewModel(),
    onAreaClicked: (area: String) -> Unit
) {
    val context = LocalContext.current

    var reloadPageState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(reloadPageState) {
        areaViewModel.getAllAreas()
        reloadPageState = false
    }
    val areasUIState by areaViewModel.areaFlow.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            title = {
                Text(
                    BottomNavScreens.Areas.title
                )
            },
            scrollBehavior = scrollBehavior
        )
    }) { contentPadding ->
        if (!context.hasInternetConnection()) {
            scrollBehavior.state.heightOffset = 0f

            NoConnectionScreen(
                paddingValues = contentPadding
            ) {
                reloadPageState = true
            }
        } else {
            if (areasUIState.meals.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .padding(contentPadding),
                    columns = GridCells.Adaptive(minSize = 100.dp)
                ) {
                    items(areasUIState.meals.size) { index ->
                        val area = areasUIState.meals[index]
                        AreaItem(
                            name = area.strArea,
                        ) {
                            onAreaClicked(area.strArea)
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}