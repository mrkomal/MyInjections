package com.example.myinjections.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.myinjections.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class InjectionAmountViewModel(private val app: Application) : AndroidViewModel(app) {

    private val imageState0 = R.drawable.pic_injection_state_0
    private val imageState1 = R.drawable.pic_injection_state_1

    private val sharedPref = app.getSharedPreferences("MyInjectionsStates", 0)
    private val iconStateFromSharedPref = sharedPref.getInt(sharedPreferencesCurrInjectionIconKey, -1)
    val lastAppUpdateFromSharedPref = sharedPref.getString(sharedPreferencesLastAppUpdateDate, null)

    @RequiresApi(Build.VERSION_CODES.O)
    var imageToDisplay = getIconToDisplay()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getIconToDisplay(): Int {
        // check if:
        // - key already exists (if not -> create it)
        // - app already has a date of last injection (if not add it)
        // - last icon update was yesterday
        if(iconStateFromSharedPref == -1 || lastAppUpdateFromSharedPref == null ||
            getCurrentDate() > lastAppUpdateFromSharedPref.toString()) {
                modifyIconStateSharedPref(0)
                modifyLastAppRefresh()
        }
        return when(iconStateFromSharedPref){
            1 -> imageState1
            else -> imageState0
        }
    }

    fun updateIconOnClick(){
        if(iconStateFromSharedPref == 0) {
            modifyIconStateSharedPref(1)
            imageToDisplay = imageState1
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun modifyLastAppRefresh() {
        sharedPref.edit()
            .putString(sharedPreferencesLastAppUpdateDate, getCurrentDate())
            .apply()
    }

    private fun modifyIconStateSharedPref(state: Int) {
        sharedPref.edit()
            .putInt(sharedPreferencesCurrInjectionIconKey, state)
            .apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDate() = LocalDateTime.now()
        .format(DateTimeFormatter.ISO_DATE)

    companion object {
        private const val sharedPreferencesCurrInjectionIconKey = "CurrentInjectionIconState"
        private const val sharedPreferencesLastAppUpdateDate = "LastAppUpdateDate"
    }
}