package com.example.moodbloom.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodbloom.ui.screens.*
import com.example.moodbloom.ui.util.todayIso

@Composable
fun MoodNav(modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onCalendar = { nav.navigate("calendar") },
                onLogMood = { nav.navigate("chooseFamily?date=${todayIso()}") }
            )
        }

        composable("calendar") {
            CalendarScreen(
                onBack = { nav.popBackStack() },
                onPickDay = { iso ->
                    nav.navigate("chooseFamily?date=$iso")
                })
        }
        composable(
            route = "chooseFamily?date={dateIso}",
            arguments = listOf(navArgument("dateIso") { type = NavType.StringType; defaultValue = todayIso() })
        ) { backStack ->
            val dateIso = backStack.arguments?.getString("dateIso")!!
            ChooseFamilyScreen(
                dateIso = dateIso,
                onPickFamily = { fam ->
                    nav.navigate("chooseMood?family=$fam&date=$dateIso")
                },
                onBack = { nav.popBackStack() }
            )
        }
        composable(
            route = "chooseMood?family={family}&date={dateIso}",
            arguments = listOf(
                navArgument("family") { type = NavType.StringType },
                navArgument("dateIso") { type = NavType.StringType }
            )
        ) { args ->
            val fam = args.arguments?.getString("family")!!
            val dateIso = args.arguments?.getString("dateIso")!!
            ChooseMoodScreen(
                familyName = fam,
                dateIso = dateIso,
                onDone = { nav.navigate("home") { popUpTo("home") { inclusive = false } } },
                onBack = { nav.popBackStack() }
            )
        }
    }
}
