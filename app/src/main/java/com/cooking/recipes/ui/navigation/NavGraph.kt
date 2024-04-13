package com.cooking.recipes.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cooking.recipes.helpers.ARG_TYPE_AREA
import com.cooking.recipes.helpers.ARG_TYPE_CATEGORY
import com.cooking.recipes.ui.screens.bottomNavigation.areas.AreasScreen
import com.cooking.recipes.ui.screens.bottomNavigation.categories.CategoriesScreen
import com.cooking.recipes.ui.screens.bottomNavigation.settings.SettingsScreen
import com.cooking.recipes.ui.screens.mealsPreview.MealsPreviewScreen
import com.cooking.recipes.ui.screens.recipe.RecipeScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    paddingValue: PaddingValues
) {
    NavHost(
        modifier = Modifier.padding(paddingValue),
        navController = navHostController,
        startDestination = BottomNavScreens.Categories.route
    ) {

        composable(route = BottomNavScreens.Categories.route) {
            CategoriesScreen(
                onCategoryClicked = { category ->
                    navHostController.navigate(
                        Screens.MealsPreview.route
                            .replace(
                                "{argType}",
                                ARG_TYPE_CATEGORY
                            )
                            .replace(
                                "{argument}",
                                category
                            )
                    )
                }
            )
        }

        composable(route = BottomNavScreens.Areas.route) {
            AreasScreen(onAreaClicked = { area ->
                navHostController.navigate(
                    Screens.MealsPreview.route
                        .replace(
                            "{argType}",
                            ARG_TYPE_AREA
                        ).replace(
                            "{argument}",
                            area
                        )
                )
            })
        }

        composable(route = BottomNavScreens.Settings.route) {
            SettingsScreen()
        }

        composable(route = Screens.MealsPreview.route) {
            val argType = it.arguments?.getString("argType")
            val argument = it.arguments?.getString("argument")
            if (argType != null && argument != null) {
                MealsPreviewScreen(
                    argType = argType,
                    argument = argument,
                    onMealClicked = { id ->
                        navHostController.navigate(
                            Screens.Recipe.route.replace(
                                "{id}",
                                id
                            )
                        )
                    },
                    onBackClicked = {
                        navHostController.navigateUp()
                    }
                )
            }
        }

        composable(route = Screens.Recipe.route) {
            val id = it.arguments?.getString("id")
            id?.let {
                RecipeScreen(
                    id = id
                ) {
                    navHostController.navigateUp()
                }
            }
        }
    }
}

sealed class Screens(
    val route: String,
    val title: String
) {

    data object MealsPreview : Screens(
        route = "meals/{argType}/{argument}",
        title = "Meals"
    )

    data object Recipe : Screens(
        route = "recipe/{id}",
        title = "Recipe"
    )

}