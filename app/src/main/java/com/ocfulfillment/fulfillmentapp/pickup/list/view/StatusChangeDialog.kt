package com.ocfulfillment.fulfillmentapp.pickup.list.view

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object StatusChangeDialog {

    data class ClickActions(
        val onPositiveClickText: String? = null,
        val onNegativeClickText: String? = null,
        val onPositiveWithDialogClickAction: (DialogInterface) -> Unit = {},
        val onNegativeWithDialogClickAction: (DialogInterface) -> Unit = {},
    )

    fun buildAlertDialog(
        context: Context,
        title: String,
        description: String,
        clickActions: ClickActions
    ): AlertDialog.Builder =
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(description)
            .setPositiveButton(clickActions.onPositiveClickText) { dialogInterface, _ -> clickActions.onPositiveWithDialogClickAction(dialogInterface) }
            .setNegativeButton(clickActions.onNegativeClickText) { dialogInterface, _ -> clickActions.onNegativeWithDialogClickAction(dialogInterface)}
}