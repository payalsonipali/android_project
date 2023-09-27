package com.example.todolistinkotlin.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.todolistinkotlin.AnalyticsTracker
import com.example.todolistinkotlin.MainActivity
import com.example.todolistinkotlin.R
import com.example.todolistinkotlin.database.ToDoListDatabase
import java.util.*


/**
 *   Created by Sundar Pichai on 26/8/19.
 */
class AlarmReceiver : BroadcastReceiver() {


    private val GROUP_MESSAGE: String = "TODOLIST"

    var toDoListDatabase: ToDoListDatabase? = null

    @SuppressLint("WrongConstant")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { initiateDatabase(it) }
        val notificationManager: NotificationManager =
            context?.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        var isShow = intent?.getIntExtra("isShow", 0) ?: 0
        val dbId = intent?.getLongExtra("id", -1) ?: -1
        val title = intent?.getStringExtra("title") ?: ""
        val time = intent?.getStringExtra("date")?:""

        val icon = R.drawable.ic_launcher_background


        val myIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("Remainder", "My Notifications", NotificationManager.IMPORTANCE_MAX)
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description")
            notificationChannel.enableLights(true)
            notificationChannel.setLightColor(Color.RED)
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(context, "Remainder")
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(time)
            .setPriority(NotificationCompat.VISIBILITY_PUBLIC)
            .setColor(Color.RED)
            .setGroup(GROUP_MESSAGE)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .build()

        val analyticsTracker = AnalyticsTracker.getInstance(context)

        try {
            notificationManager.notify(getNumber(), notification)

            toDoListDatabase?.toDoListDao()?.isShownUpdate(id = dbId, isShow = 1)

            val list = toDoListDatabase?.toDoListDao()?.get(dbId)

            val eventProperties = mapOf(
                "action" to "Notification sent",
            )
            analyticsTracker.trackEvent("SendNotification", eventProperties)
        }catch (e:Exception){
            analyticsTracker.handleException(e)
        }

    }

    private fun initiateDatabase(context: Context) {
        if (toDoListDatabase == null)
            toDoListDatabase = ToDoListDatabase.getInstance(context)
    }

    // to show multiple number of notification , there is need of unique number
    fun getNumber(): Int = (Date().time / 1000L % Integer.MAX_VALUE).toInt()
    /*var randomNumber = Random()
    var m = randomNumber.nextInt(9999 - 1000) + 1000
    */

}
