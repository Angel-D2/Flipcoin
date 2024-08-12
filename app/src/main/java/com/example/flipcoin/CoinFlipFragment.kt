package com.example.flipcoin

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import kotlin.random.Random

class CoinFlipFragment : Fragment() {

    private val channelId = "flip_a_coin_notification"
    private val notificationId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coin_flip, container, false)

        // Initialize UI elements
        val coinImageView: ImageView = view.findViewById(R.id.coinImageView)
        val flipButton: Button = view.findViewById(R.id.flipButton)
        val resultTextView: TextView = view.findViewById(R.id.resultTextView)

        // Set up button click listener
        flipButton.setOnClickListener {
            // Simulate a coin flip
            val result = if (Random.nextBoolean()) "Heads" else "Tails"

            // Update the UI with the result
            resultTextView.text = result

            // Optionally update the ImageView based on the result
            val imageResource = if (result == "Heads") R.drawable.head else R.drawable.tail
            coinImageView.setImageResource(imageResource)

            // Show a notification with the result
            showNotification(result)
        }

        return view
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(result: String) {
        // Create notification channel for Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Flip a Coin Notification"
            val descriptionText = "Notification for coin flip results"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build and display the notification
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_coin) // Ensure this icon exists in your drawable folder
            .setContentTitle("Coin Flip Result")
            .setContentText("The result is $result")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())) {
            // Show the notification
            notify(notificationId, builder.build())
        }
    }
}
