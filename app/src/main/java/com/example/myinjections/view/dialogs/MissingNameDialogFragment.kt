package com.example.myinjections.view.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myinjections.R

class MissingNameDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.missing_name_alert_dialog_fragment, null))
            builder.create()
//            AlertDialog.Builder(it)
//                .setTitle("Error")
//                .setMessage("Injection\'s name is required.")
//                .setIcon(R.drawable.sharp_error_black_36dp)
//                .create()
        }
    }
}