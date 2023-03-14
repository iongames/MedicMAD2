package com.example.medicmad2.common

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.medicmad2.R
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/*
Описание: Класс отправки уведомлений пользователю
Дата создания: 14.03.2023 15:30
Автор: Георгий Хасанов
*/
class NotificationManager(val context: Context, val workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val channel = NotificationChannel("c1", "name", android.app.NotificationManager.IMPORTANCE_DEFAULT)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        manager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, "c1")
            .setSmallIcon(R.drawable.ic_medicmad_foreground)
            .setContentTitle("Анализы")
            .setContentText("30 минут до начала сдачи анализов!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }

        return Result.success()
    }
}

/*
Описание: Метод отправки отложенных уведомлений
Дата создания: 14.03.2023 15:30
Автор: Георгий Хасанов
*/
fun scheduleNotification(context: Context, date: LocalDateTime) {
    val currentDate = LocalDateTime.now()

    val duration = Duration.between(currentDate, date)
    val work = OneTimeWorkRequestBuilder<NotificationManager>()
        .setInitialDelay(duration.seconds, TimeUnit.SECONDS)
        .addTag("NOTIFY").build()

    WorkManager.getInstance(context).enqueue(work)
}