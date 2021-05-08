package com.example.myinjections.tools

import android.app.Application

interface InternetConnectionState {

    // function to check if phone has internet connection (returns true or false)
    fun hasInternetConnection(app: Application): Boolean

}