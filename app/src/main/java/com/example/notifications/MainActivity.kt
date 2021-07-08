package com.example.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Simple Notification
        SimpleText_Notification_btn.setOnClickListener {
            //To open the Main Activity we use intent
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("My notification")                                   // set the notification title
                .setContentText("This is the Notification Content")                   // and message
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)    //when user click on the notification our Main Activity will open
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC)

            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that must be defined
                notify(0, builder.build())
                createNotificationChannel()
            }
        }
        //Notification with Image
        Image_Notification_btn.setOnClickListener {
            val myBitmap = (getDrawable(R.drawable.youtube) as BitmapDrawable).bitmap
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("My notification")                      // set the notification title
                .setContentText("Tis is the Notification Content")       //  and  message
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(myBitmap)
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null)
                )
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that must be defined
                notify(0, builder.build())
                createNotificationChannel()
            }
        }
        //Notification with URL
        WithUrl_Notification_btn.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Click Here to open Website")
                .setContentText("This is the Notification Content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that must be defined
                notify(0, builder.build())
                createNotificationChannel()
            }
        }
        // Notification with Button
        WithButton_Notification_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            // Button on click Action
            val intent_2 = Intent(this, SecondActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent_2: PendingIntent = PendingIntent.getActivity(this, 0, intent_2, 0)
            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("My notification")
                .setContentText("This is the Notification with Button")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(
                    R.mipmap.ic_launcher_round,
                    getString(R.string.button_name),
                    pendingIntent_2
                )
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that must be defined
                notify(0, builder.build())
                createNotificationChannel()
            }
        }
        // Notification With Downloading ProgressBar
        ProgressBar_Notification_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("My notification")
                .setContentText(" Notification with ProgressBar")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                val PROGRESS_MAX =
                    100                                                              // This will
                val PROGRESS_CURRENT =
                    0                                                           // create a
                builder.setProgress(
                    PROGRESS_MAX,
                    PROGRESS_CURRENT,
                    true
                )               // progressBar
                // notificationId is a unique int for each notification that must be defined
                notify(0, builder.build())
                //time count down
                object : CountDownTimer(10000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {
                        builder.setContentText("Download Completed")
                            .setProgress(0, 0, false)
                        notify(0, builder.build())
                    }
                }.start()
                createNotificationChannel()
            }
        }
    }
        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.channel_name)
                val descriptionText = getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
