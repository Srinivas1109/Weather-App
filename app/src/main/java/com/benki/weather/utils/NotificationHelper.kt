package com.benki.weather.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.benki.weather.MainActivity
import com.benki.weather.R

object NotificationHelper {
    fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int
    ) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(
        context: Context,
        channelId: String,
        title: String,
        description: String
    ): Notification {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, channelId).apply {
            setContentTitle(title)
            setContentText(description)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(pendingIntent)
        }.build()
    }

    fun sendNotification(context: Context, notificationId: Int, notification: Notification) {
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, notification)
        }
    }
}