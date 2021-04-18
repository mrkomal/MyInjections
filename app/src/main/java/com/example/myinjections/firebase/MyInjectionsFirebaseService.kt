package com.example.myinjections.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.myinjections.R
import com.example.myinjections.view.ui.MenuActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val MESSAGES_CHANNEL_ID = "my_injections_firebase_messages_channel"

class MyInjectionsFirebaseService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        // values required for notification
        val intent = Intent(this, MenuActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        // create channel
        val channel = NotificationChannel(MESSAGES_CHANNEL_ID, "message_channel_1",
            IMPORTANCE_HIGH).apply {
            description = "Channel for firebase messaging service"
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)

        // design notification
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat
            .Builder(this, MESSAGES_CHANNEL_ID)
            .setContentTitle(p0.data["title"])
            .setContentText(p0.data["message"])
            .setSmallIcon(R.drawable.ic_baseline_local_hospital_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        // display notification
        notificationManager.notify(notificationID, notification)
    }
}