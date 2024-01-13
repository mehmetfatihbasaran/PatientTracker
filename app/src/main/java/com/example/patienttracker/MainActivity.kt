package com.example.patienttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.patienttracker.navigation.NavGraph
import com.example.patienttracker.presentation.patient_details.PatientDetailsScreen
import com.example.patienttracker.presentation.patient_details.PatientDetailsViewModel
import com.example.patienttracker.presentation.patient_list.PatientListScreen
import com.example.patienttracker.presentation.theme.PatientTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatientTrackerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
