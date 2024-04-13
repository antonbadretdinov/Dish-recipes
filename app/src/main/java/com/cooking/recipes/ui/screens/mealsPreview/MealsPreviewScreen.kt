package com.cooking.recipes.ui.screens.mealsPreview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cooking.recipes.R
import com.cooking.recipes.helpers.ARG_TYPE_CATEGORY
import com.cooking.recipes.helpers.utils.hasInternetConnection
import com.cooking.recipes.ui.screens.NoConnectionScreen
import com.cooking.recipes.ui.viewmodels.MealsPreviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsPreviewScreen(
    mealsPreviewViewModel: MealsPreviewViewModel = hiltViewModel(),
    argType: String,
    argument: String,
    onMealClicked: (id: String) -> Unit,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current

    var reloadPageState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(reloadPageState) {
        if (argType == ARG_TYPE_CATEGORY) {
            mealsPreviewViewModel.getMealsPreviewByCategory(category = argument)
        } else {
            mealsPreviewViewModel.getMealsPreviewByArea(area = argument)
        }

        reloadPageState = false
    }

    val mealsPreviewUIState by mealsPreviewViewModel.mealsPreviewFlow.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground
            ), title = {
                Text(
                    argument
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null
                    )
                }
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
            if (mealsPreviewUIState.meals.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 6.dp)
                        .padding(contentPadding),
                    columns = GridCells.Adaptive(minSize = 160.dp)
                ) {
                    items(mealsPreviewUIState.meals.size) { index ->
                        val mealPreview = mealsPreviewUIState.meals[index]

                        MealPreviewItem(
                            title = mealPreview.strMeal,
                            image = mealPreview.strMealThumb
                        ) {
                            onMealClicked(mealPreview.idMeal)
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