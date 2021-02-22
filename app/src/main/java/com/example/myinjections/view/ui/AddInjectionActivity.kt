package com.example.myinjections.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myinjections.R
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.view.dialogs.MissingNameDialogFragment
import com.example.myinjections.viewmodel.InjectionsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_injection.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.concurrent.schedule

class AddInjectionActivity : AppCompatActivity() {

    companion object{
        // UI items tags
        const val INSERT_BUTTON_TAG = "insert_button_pressed"

        // SnackBar times
        const val snackbarDuration = 1000
        const val totalTimeToWait = snackbarDuration + 300
    }

    private lateinit var injectionsViewModel: InjectionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

        //Setting toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.add_injection_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        //ViewModel
        injectionsViewModel = getViewModel()

        //UI items settings
        setYearPickerValues()
        setDoseSliderLabel()

        //insert button pressed
        insert_button.setOnClickListener(listener)
    }


    @SuppressLint("WrongConstant")
    private val listener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.insert_button -> {
                Log.d(INSERT_BUTTON_TAG, "Insert button was pressed.")

                // check if user typed name
                val nameField = name_textView.text.trim()
                val illnessField = illness_textView.text.trim()

                if(TextUtils.isEmpty(nameField) || TextUtils.isEmpty(illnessField)){
                    //display dialog about missing information
                    val dialog = MissingNameDialogFragment()
                    dialog.show(supportFragmentManager,"MissingNameDialogFragment")
                    Log.d(INSERT_BUTTON_TAG, "Alert dialog displayed.")
                } else {
                    // get data defined by user
                    val injectionName = nameField.toString()
                    val illnessInformation = illnessField.toString()
                    val injectionYear = year_picker.value.toString()
                        .toInt()
                    val injectionDose = dose_slider.value.toString()
                        .format("%.2f")
                        .toDouble()
                    val isInjectionObligatory: Boolean = getRadioButtonChoice()

                    //create new InjectionInfo object and pass it to database with use of viewmodel
                    injectionsViewModel.insertInjectionInfo(
                        InjectionInfo(0, injectionName, injectionYear, injectionDose,
                            isInjectionObligatory, illnessInformation)
                    )

                    //display success information
                    Snackbar.make(
                        findViewById(R.id.add_injection_layout),
                        getString(R.string.snackbarText),
                        Snackbar.LENGTH_INDEFINITE)
                        .setDuration(snackbarDuration)
                        .show()

                    //wait until Snackbar disappears and then finish activity
                    Timer().schedule(totalTimeToWait.toLong()){
                        finish()
                        Log.d(INSERT_BUTTON_TAG, "Data passed to another activity.")
                    }
                }
            }
        }
    }


    private fun setDoseSliderLabel(){
        dose_slider.setLabelFormatter { value: Float ->
            return@setLabelFormatter "$value ml"
        }
    }


    private fun setYearPickerValues(){
        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        val gapBetweenYears = 120

        year_picker.maxValue = currentYear
        year_picker.minValue = currentYear - gapBetweenYears
        year_picker.value = currentYear
    }


    @SuppressLint("ResourceType")
    private fun getRadioButtonChoice(): Boolean {
        when(findViewById<RadioButton>(obligatory_radio_group.checkedRadioButtonId).text.toString()) {
            resources.getString(R.string.radioButtonPos) -> return true
            resources.getString(R.string.radioButtonNeg) -> return false
        }
        return false
    }
}