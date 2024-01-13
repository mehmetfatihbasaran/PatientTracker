package com.example.patienttracker.presentation.patient_list

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    onDialogDismiss : () -> Unit,
    onConfirmButtonClick : () -> Unit
){
    AlertDialog(
        title = {
            Text(title, style = MaterialTheme.typography.h6)
                },
        text = {
            Text(message, style = MaterialTheme.typography.body1)
        },
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDialogDismiss) {
                Text(text = "No")
            }
        },
        onDismissRequest = {onConfirmButtonClick()}
    )
}