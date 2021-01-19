package com.example.myinjections.view.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.myinjections.R
import com.example.myinjections.view.ui.AddInjectionActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.missing_name_alert_dialog_fragment.*

class MissingNameDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;

            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.missing_name_alert_dialog_fragment, null))
                .setNegativeButton("OK") { dialog, which ->
                    dialog.cancel()
                }
                .create()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(AddInjectionActivity.INSERT_BUTTON_TAG, "Negative button pressed. Alert destroyed.")
    }
}