package com.example.patienttracker.presentation.patient_details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun PatientDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    onSuccesfullySaveClicked: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val state = viewModel.state

    LaunchedEffect(key1 = Unit) {
        delay(500)
        focusRequester.requestFocus()
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is PatientDetailsViewModel.UiEvent.SaveNote -> {
                   onSuccesfullySaveClicked()
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                }
                is PatientDetailsViewModel.UiEvent.ShowSnackBar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(onBackClick = onBackClick)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = state.name,
                onValueChange ={ newName ->
                    viewModel.onEvent(PatientDetailsEvents.OnNameChange(newName))
                },
                label = {
                    Text(text = "Name")
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.age,
                    onValueChange = {  newAge ->
                        viewModel.onEvent(PatientDetailsEvents.OnAgeChange(newAge))
                    },
                    label = {Text(text = "Age")},
                    textStyle = MaterialTheme.typography.body1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))
                RadioGroup(text = "Male", selected = state.gender == 1, onClick = {viewModel.onEvent(PatientDetailsEvents.SelectedMale) })
                RadioGroup(text = "Female", selected = state.gender == 2, onClick = { viewModel.onEvent(PatientDetailsEvents.SelectedFemale) })
                Spacer(modifier = Modifier.height(10.dp))

            }

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.doctorAssigned,
                onValueChange ={ newDoctor ->
                    viewModel.onEvent(PatientDetailsEvents.OnDoctorAssignedChange(newDoctor))
                },
                label = {
                    Text(text = "Assigned Doctor")
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.body1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.prescription,
                onValueChange ={ newPrescription ->
                    viewModel.onEvent(PatientDetailsEvents.OnPrescriptionChange(newPrescription))
                },
                label = {
                    Text(text = "Prescription")
                },
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                onClick = { viewModel.onEvent(PatientDetailsEvents.OnSaveClick) }
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }

        }
    }

}

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
){
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun TopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Patient's Details Screen",
                style = MaterialTheme.typography.h5
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

// state: Anything that can change during the lifecycle of the screen and application
// event: Anything that can user do is an event
