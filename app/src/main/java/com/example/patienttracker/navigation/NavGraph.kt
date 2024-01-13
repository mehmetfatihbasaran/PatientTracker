package com.example.patienttracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patienttracker.presentation.patient_details.PatientDetailsScreen
import com.example.patienttracker.presentation.patient_list.PatientListScreen
import com.example.patienttracker.util.Constraints.PATIENT_DETAILS_ARG_KEY
import com.example.patienttracker.util.Constraints.PATIENT_DETAILS_SCREEN
import com.example.patienttracker.util.Constraints.PATIENT_LIST_SCREEN

sealed class Screen(val route: String) {
    object PatientListScreen: Screen(PATIENT_LIST_SCREEN)
    object PatientDetailsScreen: Screen("$PATIENT_DETAILS_SCREEN" +
            "?" +
            "$PATIENT_DETAILS_ARG_KEY" +
            "={$PATIENT_DETAILS_ARG_KEY}"){
        fun passPatientId(patientId: Int? = null): String {
            return "$PATIENT_DETAILS_SCREEN?$PATIENT_DETAILS_ARG_KEY=$patientId"
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.PatientListScreen.route,
    ){
        composable(route = Screen.PatientListScreen.route){
            PatientListScreen(
                onFabClicked = {
                    navController.navigate(Screen.PatientDetailsScreen.passPatientId())
                },
                onItemClicked = {patientId ->
                    navController.navigate(Screen.PatientDetailsScreen.passPatientId(patientId))
                }
            )
        }
        composable(
            route = Screen.PatientDetailsScreen.route,
            arguments = listOf(
                navArgument(
                    name = PATIENT_DETAILS_ARG_KEY
                ){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            PatientDetailsScreen(
                onBackClick = { navController.navigateUp() },
                onSuccesfullySaveClicked = {navController.navigateUp()}
            )
        }
    }
}