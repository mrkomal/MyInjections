package com.example.myinjections.view.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.example.myinjections.viewmodel.BaseActivity
import com.example.myinjections.R
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.view.dialogs.MissingNameDialogFragment
import kotlinx.android.synthetic.main.activity_add_injection.*
import java.util.*

class AddInjectionActivity : BaseActivity() {

    companion object{
        //UI items tags
        const val INSERT_BUTTON_TAG = "insert_button_pressed"
    }


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
                    val injectionDose = dose_slider.value.toString()
                        .format("%.2f")
                        .toDouble()
                    val isInjectionObligatory: String = getRadioButtonChoice()

                    //create new InjectionInfo object and pass it to database with use of viewmodel
                    injectionsViewModel.insertInjectionInfo(
                        InjectionInfo(0, injectionName, injectionYear, injectionDose,
                            isInjectionObligatory, illnessInformation)
                    )

                    //finish activity
                    finish()
                    Log.d(INSERT_BUTTON_TAG, "Data passed to another activity.")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

        //Setting action bar title
        supportActionBar?.title = "NEW INJECTION"

        //UI items settings
        setYearPickerValues()
        setDoseSliderLabel()

        //insert button pressed
        insert_button.setOnClickListener(listener)
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


    private fun getRadioButtonChoice(): String {
        return findViewById<RadioButton>(obligatory_radio_group.checkedRadioButtonId).text.toString()
    }
}