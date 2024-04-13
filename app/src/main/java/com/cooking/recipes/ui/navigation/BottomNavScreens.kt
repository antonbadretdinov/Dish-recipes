package com.cooking.recipes.ui.navigation

import com.cooking.recipes.R

sealed class BottomNavScreens(
    val route: String,
    val title: String,
    val icon: Int,
    val selectedIcon: Int
) {
    data object Categories: BottomNavScreens(
        route = "categories",
        title = "Categories",
        icon = R.drawable.ic_categories,
        selectedIcon = R.drawable.ic_categories_selected
    )

    data object Areas: BottomNavScreens(
        route = "areas",
        title = "Areas",
        icon = R.drawable.ic_areas,
        selectedIcon = R.drawable.ic_areas_selected
    )

    data object Settings: BottomNavScreens(
        route = "settings",
        title = "Settings",
        icon = R.drawable.ic_settings,
        selectedIcon = R.drawable.ic_settings_selected
    )
}