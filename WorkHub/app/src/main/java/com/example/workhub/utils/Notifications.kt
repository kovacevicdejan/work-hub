package com.example.workhub.utils

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.workhub.*

fun makeStatusNotification(context: Context, message: String) {
    val notificationChannel =
        NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
            .setName(CHANNEL_NAME)
            .setDescription(CHANNEL_DESCRIPTION).build()
    NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.programming).setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message).setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0)).build()
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
}