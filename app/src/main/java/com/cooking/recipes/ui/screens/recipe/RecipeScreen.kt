package com.cooking.recipes.ui.screens.recipe

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cooking.recipes.R
import com.cooking.recipes.helpers.utils.hasInternetConnection
import com.cooking.recipes.ui.navigation.Screens
import com.cooking.recipes.ui.screens.NoConnectionScreen
import com.cooking.recipes.ui.theme.linkColor
import com.cooking.recipes.ui.viewmodels.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    id: String,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current

    var reloadPageState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(reloadPageState) {
        recipeViewModel.getMealsRecipeById(id = id)
        reloadPageState = false
    }

    val recipeUiState by recipeViewModel.recipeFlow.collectAsStateWithLifecycle()

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
                    Screens.Recipe.title
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
            if (recipeUiState.meals.isNotEmpty()) {
                Column(
                    Modifier
                        .padding(paddingValues = contentPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    val meal = recipeUiState.meals[0]
                    AsyncImage(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(meal.strMealThumb)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = meal.strMeal,
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                        lineHeight = 36.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(24.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_category_recipe
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = meal.strCategory,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(24.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_areas_selected
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = meal.strArea,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Divider()

                    Text(
                        text = stringResource(id = R.string.instruction),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    Text(
                        text = meal.strInstructions,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 4.dp,
                            bottom = 16.dp
                        )
                    )

                    Divider()


                    Text(
                        text = stringResource(id = R.string.ingredients),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    if (meal.strMeasure1?.isNotBlank() == true &&
                        meal.strIngredient1?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient1,
                            measure = meal.strMeasure1
                        )
                    }

                    if (meal.strMeasure2?.isNotBlank() == true &&
                        meal.strIngredient2?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient2,
                            measure = meal.strMeasure2
                        )
                    }

                    if (meal.strMeasure3?.isNotBlank() == true &&
                        meal.strIngredient3?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient3,
                            measure = meal.strMeasure3
                        )
                    }

                    if (meal.strMeasure4?.isNotBlank() == true &&
                        meal.strIngredient4?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient4,
                            measure = meal.strMeasure4
                        )
                    }

                    if (meal.strMeasure5?.isNotBlank() == true &&
                        meal.strIngredient5?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient5,
                            measure = meal.strMeasure5
                        )
                    }

                    if (meal.strMeasure6?.isNotBlank() == true &&
                        meal.strIngredient6?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient6,
                            measure = meal.strMeasure6
                        )
                    }

                    if (meal.strMeasure7?.isNotBlank() == true &&
                        meal.strIngredient7?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient7,
                            measure = meal.strMeasure7
                        )
                    }

                    if (meal.strMeasure8?.isNotBlank() == true &&
                        meal.strIngredient8?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient8,
                            measure = meal.strMeasure8
                        )
                    }

                    if (meal.strMeasure9?.isNotBlank() == true &&
                        meal.strIngredient9?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient9,
                            measure = meal.strMeasure9
                        )
                    }

                    if (meal.strMeasure10?.isNotBlank() == true &&
                        meal.strIngredient10?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient10,
                            measure = meal.strMeasure10
                        )
                    }

                    if (meal.strMeasure11?.isNotBlank() == true &&
                        meal.strIngredient11?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient11,
                            measure = meal.strMeasure11
                        )
                    }

                    if (meal.strMeasure12?.isNotBlank() == true &&
                        meal.strIngredient12?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient12,
                            measure = meal.strMeasure12
                        )
                    }

                    if (meal.strMeasure13?.isNotBlank() == true &&
                        meal.strIngredient13?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient13,
                            measure = meal.strMeasure13
                        )
                    }

                    if (meal.strMeasure14?.isNotBlank() == true &&
                        meal.strIngredient14?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient14,
                            measure = meal.strMeasure14
                        )
                    }

                    if (meal.strMeasure15?.isNotBlank() == true &&
                        meal.strIngredient15?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient15,
                            measure = meal.strMeasure15
                        )
                    }

                    if (meal.strMeasure16?.isNotBlank() == true &&
                        meal.strIngredient16?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient16,
                            measure = meal.strMeasure16
                        )
                    }

                    if (meal.strMeasure17?.isNotBlank() == true &&
                        meal.strIngredient17?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient17,
                            measure = meal.strMeasure17
                        )
                    }

                    if (meal.strMeasure18?.isNotBlank() == true &&
                        meal.strIngredient18?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient18,
                            measure = meal.strMeasure18
                        )
                    }

                    if (meal.strMeasure19?.isNotBlank() == true &&
                        meal.strIngredient19?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient19,
                            measure = meal.strMeasure19
                        )
                    }

                    if (meal.strMeasure20?.isNotBlank() == true &&
                        meal.strIngredient20?.isNotBlank() == true
                    ) {
                        RecipeIngredientItem(
                            ingredient = meal.strIngredient20,
                            measure = meal.strMeasure20
                        )
                    }

                    Divider(modifier = Modifier.padding(top = 12.dp))

                    Text(
                        text = stringResource(id = R.string.additionally),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        if (meal.strSource != null) {
                            Row(
                                modifier = Modifier.padding(bottom = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp)
                                        .size(24.dp),
                                    painter = painterResource(
                                        id = R.drawable.ic_source
                                    ),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = stringResource(id = R.string.source),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = linkColor,
                                    modifier = Modifier.clickable {
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(meal.strSource)
                                        context.startActivity(intent)
                                    }
                                )
                            }
                        }

                        if (meal.strYoutube != null) {
                            Row(
                                modifier = Modifier.padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp)
                                        .size(24.dp),
                                    painter = painterResource(
                                        id = R.drawable.ic_video
                                    ),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = stringResource(id = R.string.video),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = linkColor,
                                    modifier = Modifier.clickable {
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = Uri.parse(meal.strYoutube)
                                        context.startActivity(intent)
                                    }
                                )
                            }
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