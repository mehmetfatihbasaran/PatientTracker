package com.example.patienttracker.presentation.patient_details

sealed class PatientDetailsEvents {
    data class OnNameChange(val newName: String) : PatientDetailsEvents()
    data class OnAgeChange(val newAge: String) : PatientDetailsEvents()
    data class OnDoctorAssignedChange(val newDoctorAssigned: String) : PatientDetailsEvents()
    data class OnPrescriptionChange(val newPrescription: String) : PatientDetailsEvents()
    object SelectedMale : PatientDetailsEvents()
    object SelectedFemale : PatientDetailsEvents()
    object OnSaveClick : PatientDetailsEvents()

    object OnDeleteClick : PatientDetailsEvents()
    object OnBackClick : PatientDetailsEvents()

}
