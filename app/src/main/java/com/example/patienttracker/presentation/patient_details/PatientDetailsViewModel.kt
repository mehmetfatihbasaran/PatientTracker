package com.example.patienttracker.presentation.patient_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.domain.repository.PatientRepository
import com.example.patienttracker.util.Constraints.PATIENT_DETAILS_ARG_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val repository: PatientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(PatientDetailsUiStates())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPatientId: Int? = null

    init {
        fetchPatientDetails()
    }

    fun onEvent(event: PatientDetailsEvents) {
        when (event) {
            is PatientDetailsEvents.OnNameChange -> {
                state = state.copy(name = event.newName)
            }

            is PatientDetailsEvents.OnAgeChange -> {
                state = state.copy(age = event.newAge)
            }

            is PatientDetailsEvents.OnDoctorAssignedChange -> {
                state = state.copy(doctorAssigned = event.newDoctorAssigned)
            }

            is PatientDetailsEvents.OnPrescriptionChange -> {
                state = state.copy(prescription = event.newPrescription)
            }

            is PatientDetailsEvents.OnSaveClick -> {
                viewModelScope.launch {
                    try {
                        savePatient()
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Unknown Error"
                            )
                        )
                    }
                }
            }

            is PatientDetailsEvents.OnDeleteClick -> {
                // TODO
            }

            is PatientDetailsEvents.OnBackClick -> {
                // TODO
            }
            is PatientDetailsEvents.SelectedMale -> {
                state = state.copy(gender = 1)
            }
            is PatientDetailsEvents.SelectedFemale -> {
                state = state.copy(gender = 2)
            }

        }
    }

    private fun savePatient() {
        val age = state.age.toIntOrNull()
        when {
            state.name.isEmpty() -> {
                throw TextFieldException("Name can't be empty")
            }
            state.age.isEmpty() -> {
                throw TextFieldException("Age can't be empty")
            }
            state.gender == 0 -> {
                throw TextFieldException("Gender can't be empty")
            }
            state.doctorAssigned.isEmpty() -> {
                throw TextFieldException("Doctor Assigned can't be empty")
            }
            state.prescription.isEmpty() -> {
                throw TextFieldException("Prescription can't be empty")
            }
            age == null || age < 0 || age > 120 -> {
                throw TextFieldException("Age must be between 0 and 120")
            }
        }
        viewModelScope.launch {
            repository.addOrUpdatePatient(
                patient = Patient(
                    name = state.name.trim(),
                    age = state.age,
                    doctorAssigned = state.doctorAssigned.trim(),
                    prescription = state.prescription,
                    gender = state.gender,
                    patientId = currentPatientId
                    )
                )
        }
    }

    private fun fetchPatientDetails() {
        savedStateHandle.get<Int>(PATIENT_DETAILS_ARG_KEY)?.let {patientId ->
            if (patientId != -1) {
                viewModelScope.launch {
                    repository.getPatientById(patientId)?.apply {
                        state = state.copy(
                            name = name,
                            age = age,
                            doctorAssigned = doctorAssigned,
                            prescription = prescription,
                            gender = gender
                        )
                        currentPatientId = patientId
                    }
                }
            }
        }
    }


    sealed class UiEvent{
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }

}

class TextFieldException (message: String?): Exception(message)