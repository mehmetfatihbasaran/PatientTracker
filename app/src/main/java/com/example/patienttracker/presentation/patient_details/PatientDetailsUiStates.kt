package com.example.patienttracker.presentation.patient_details

data class PatientDetailsUiStates (
    var name: String = "",
    val age: String = "",
    val gender: Int = 0,
    val doctorAssigned: String = "",
    val prescription: String = ""
)