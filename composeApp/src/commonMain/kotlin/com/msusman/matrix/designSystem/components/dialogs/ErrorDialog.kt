package com.msusman.matrix.designSystem.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.action_ok
import matrixclientkmp.composeapp.generated.resources.dialog_title_error
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorDialog(
    content: String,
    title: String? = ErrorDialogDefaults.title,
    submitText: String = ErrorDialogDefaults.submitText,
    onDismiss: () -> Unit,
    canDismiss: Boolean = true,
) {

    AlertDialog(

        title = title?.let { { Text(text = title) } },
        text = {
            Text(text = content)
        },
        onDismissRequest = {
            if (canDismiss) {
                onDismiss()
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(submitText)
            }
        },
        dismissButton = null
    )


}


object ErrorDialogDefaults {
    val title: String @Composable get() = stringResource(Res.string.dialog_title_error)
    val submitText: String @Composable get() = stringResource(Res.string.action_ok)
}
