package com.example.myinjections.view.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myinjections.R

class OkDialogFragment(private val dialogLayoutNumber: Int) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(dialogLayoutNumber, null))
                .setNegativeButton("OK") { dialog, which ->
                    dialog.cancel()
                }
                .create()
        }
    }
}