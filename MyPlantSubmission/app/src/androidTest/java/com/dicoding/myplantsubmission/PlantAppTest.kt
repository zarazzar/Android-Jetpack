package com.dicoding.myplantsubmission

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.myplantsubmission.data.PlantsDataSource
import com.dicoding.myplantsubmission.ui.navigation.Screen
import com.dicoding.myplantsubmission.ui.theme.MyPlantSubmissionTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.notification.RunListener.ThreadSafe

class PlantAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyPlantSubmissionTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                PlantApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToDetailWithData() {
        composeTestRule.onNodeWithTag("PlantList").performScrollToIndex(15)
        composeTestRule.onNodeWithText(PlantsDataSource.dummyPlants[15].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlant.route)
        composeTestRule.onNodeWithText(PlantsDataSource.dummyPlants[15].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(PlantsDataSource.dummyPlants[15].description).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNav_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_user).performClick()
        navController.assertCurrentRouteName(Screen.User.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateBack() {
        composeTestRule.onNodeWithTag("PlantList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(PlantsDataSource.dummyPlants[15].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlant.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_emptyFavorite() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithTag("EmptyTag").assertIsDisplayed()
    }

    @Test
    fun navHost_addFavorite_UnfavoriteWithIcon() {
        composeTestRule.onNodeWithTag("PlantList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(PlantsDataSource.dummyPlants[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlant.route)
        composeTestRule.onNodeWithContentDescription("Favorite Button").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithContentDescription("Delete Icon").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
    }

    @Test
    fun navHost_addFavorite_UnfavoriteOnDetail() {
        composeTestRule.onNodeWithTag("PlantList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(PlantsDataSource.dummyPlants[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailPlant.route)
        composeTestRule.onNodeWithContentDescription("Favorite Button").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)

        composeTestRule.onNodeWithContentDescription("ItemRow").performClick()
        navController.assertCurrentRouteName(Screen.DetailPlant.route)
        composeTestRule.onNodeWithContentDescription("Favorite Button").performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
    }

    @Test
    fun navHost_insertQuery() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.search_hint)).performTextInput("aloe vera")
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithText("Aloe Vera").assertExists()
    }

}