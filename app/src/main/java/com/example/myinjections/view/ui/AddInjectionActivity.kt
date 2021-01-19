package com.example.myinjections.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.myinjections.R
import com.example.myinjections.view.dialogs.MissingNameDialogFragment
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_add_injection.*
import org.w3c.dom.Text
import java.util.*

class AddInjectionActivity : AppCompatActivity() {

    companion object{
        //UI items tags
        const val INSERT_BUTTON_TAG = "insert_button_pressed"

        //keys for data that is passed between activities
        const val prefix = "myinjections/addinjectiondata"
        const val nameKey = "$prefix/name"
        const val yearKey = "$prefix/year"
        const val isObligatoryKey = "$prefix/isObligatory"
    }


    private val listener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.insert_button -> {
                Log.d(INSERT_BUTTON_TAG, "Insert button was pressed.")

                val replyIntent = Intent()
                val injectionName = name_textView.text.toString()
                val injectionYear = year_picker.value.toString()
                val injectionDose = dose_slider.value.toDouble()
                val isInjectionObligatory: String = getRadioButtonChoice()

                if(TextUtils.isEmpty(injectionName)){
                    val dialog = MissingNameDialogFragment()
                    dialog.show(supportFragmentManager,"MissingNameDialogFragment")
                    Log.d(INSERT_BUTTON_TAG, "Alert dialog displayed.")
                } else {

                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_injection)

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
        val gapBetweenYears: Int = 120

        year_picker.maxValue = currentYear
        year_picker.minValue = currentYear - gapBetweenYears
    }


    private fun getRadioButtonChoice(): String {
        return findViewById<RadioButton>(obligatory_radio_group.checkedRadioButtonId).text.toString()
    }
}