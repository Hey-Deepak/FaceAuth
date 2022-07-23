package com.dc.tast1.screen.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable


@Composable
fun ErrorDialog(
    isDialogShowing: Boolean,
    errorMessage: String,
    stateChanged:(Boolean)->Unit
) {

    if (isDialogShowing)
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = { stateChanged(false) })
                { Text(text = "OK") }
            },
            dismissButton = {},
            title = { Text(text = "Error!") },
            text = { Text(text = errorMessage) }
        )
}