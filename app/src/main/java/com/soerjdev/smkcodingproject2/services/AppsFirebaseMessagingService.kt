package com.soerjdev.smkcodingproject2.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.soerjdev.smkcodingproject2.NotificationListActivity
import com.soerjdev.smkcodingproject2.R

class AppsFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        val TAG = AppsFirebaseMessagingService::class.java.name
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "From : "+p0.from)

        p0.notification?.let {
            Log.d(TAG, "Message notification body : "+it.body)
            Log.d(TAG, "Message title : "+it.title)
            sendNotification(it.body, it.title)
        }
    }

    private fun sendNotification(messageBody: String?, title: String?){
        val intent = Intent(this, NotificationListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_action_notification)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,
                "Notifikasi Umum",
                        NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onNewToken(p0: String) {
        Log.d(TAG, "onNewToken : $p0")
    }

}