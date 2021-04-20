package com.example.myinjections.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myinjections.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = MenuActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Click listeners for menu buttons
        display_injections_button.setOnClickListener(this)
        add_injection_button.setOnClickListener(this)
        display_links_button.setOnClickListener(this)
        display_map_button.setOnClickListener(this)
        display_injection_amount.setOnClickListener(this)

        defineFirebaseToken()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.display_links_button -> {
                Intent(this, DisplayLinksActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.display_injections_button -> {
                Intent(this, DisplayInjectionActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.add_injection_button -> {
                Intent(this, AddInjectionActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.display_map_button -> {
                Intent(this, MapsActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.display_injection_amount -> {
                Intent(this, InjectionAmountActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


    private fun defineFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result ?: "No token"
            Log.d(TAG, token)
        })
    }
}