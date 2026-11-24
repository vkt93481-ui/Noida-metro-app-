package com.noidametro.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat

object MetroAlarmHelper {

    private const val CHANNEL_ID = "noida_metro_arrival_alarm"
    private var activeRingtone: Ringtone? = null

    fun triggerArrivalAlarm(context: Context, destinationName: String) {
        // 1. Vibrate
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        val pattern = longArrayOf(0, 600, 300, 600, 300, 800)
        vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))

        // 2. Play alarm sound
        try {
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            activeRingtone?.stop()
            activeRingtone = RingtoneManager.getRingtone(context, alarmUri)
            activeRingtone?.play()
        } catch (_: Exception) {
        }

        // 3. Post notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Arrival Alarms",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Alerts when you approach your Noida Metro destination"
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Approaching $destinationName!")
            .setContentText("Get ready to deboard the Noida Metro Aqua Line.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }

    fun stopAlarm() {
        try {
            activeRingtone?.stop()
            activeRingtone = null
        } catch (_: Exception) {
        }
    }
}
