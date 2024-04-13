package com.cooking.recipes.ui.screens.bottomNavigation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.cooking.recipes.ui.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    categoriesViewModel: CategoryViewModel = hiltViewModel(),
    onCategoryClicked: (category: String) -> Unit
) {

    val context = LocalContext.current

    var reloadPageState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(reloadPageState) {
        categoriesViewModel.getAllCategories()
        reloadPageState = false
    }

    val categoriesUIState by categoriesViewModel.categoriesFlow.collectAsStateWithLifecycle()

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
                    BottomNavScreens.Categories.title
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
            if (categoriesUIState.categories.isNotEmpty()) {
                LazyColumn(
                    content = {
                        items(categoriesUIState.categories.size) { index ->
                            val category = categoriesUIState.categories[index]
                            CategoryItem(
                                title = category.strCategory,
                                description = category.strCategoryDescription,
                                image = category.strCategoryThumb
                            ) {
                                onCategoryClicked(category.strCategory)
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(contentPadding)
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(horizontal = 2.dp)
                        .fillMaxSize(),
                )
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