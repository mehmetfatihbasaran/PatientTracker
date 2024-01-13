package com.example.patienttracker.presentation.patient_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.patienttracker.domain.model.Patient

@Composable
fun PatientItem(
    patient: Patient,
    onItemClicked: () -> Unit,
    ondDeleteConfirm: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog){
        DeleteDialog(
            title = "Delete Patient",
            message = "Are you sure you want to delete ${patient.name}?",
            onDialogDismiss = { showDialog = false },
            onConfirmButtonClick = {
                ondDeleteConfirm()
                showDialog = false
            }
        )
    }

    Card(
        modifier = Modifier.clickable { onItemClicked()  },
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(9f)
            ) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Assigned Doctor: ${patient.doctorAssigned}",
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
            }
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }

}