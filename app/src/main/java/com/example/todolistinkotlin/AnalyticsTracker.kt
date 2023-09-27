package com.example.todolistinkotlin

import android.content.Context
import android.util.Log

/**
 * AnalyticsTracker is responsible for tracking user actions and sending analytics data to the server.
 *
 * Usage:
 * - Initialize an instance of AnalyticsTracker in your application.
 * - Use the provided methods to track specific events.
 *
 * @param context The application context.
 */

data class AnalyticsEvent(
    val event_name: String,
    val event_timestamp: Long,
    val event_properties: Map<String, Any>
)

class AnalyticsTracker(private val context: Context) {
    // Implement the logic to send analytics events to the server.
    // You can use an HTTP client library like Retrofit or firebase for this.
    private val TAG = "AnalyticsTracker"


    fun trackEvent(eventName: String, eventProperties: Map<String, Any>) {
        // Include event name and properties in the request.
        // Ensure you handle errors and logging appropriately.
        try {
            val analyticsEvent = AnalyticsEvent(
                event_name = eventName,
                event_timestamp = System.currentTimeMillis(),
                event_properties = eventProperties
            )
            sendAnalyticsDataToServer(analyticsEvent)
        } catch (e: Exception) {
            handleException(e)
        }

    }

    private fun sendAnalyticsDataToServer(event: AnalyticsEvent) {
        //assuming that the server infrastructure for receiving and processing the analytics data is already in place.
        // Implement the logic to send analytics data to the server.
        // You can use an HTTP client library to make a POST request to your server Or Retrofit.
        //On api call success you can send positve response on server
        //On api failure you can send error to server using logErrorToBackend
        try {
            // Implement the logic to send analytics data to the server.
        } catch (e: Exception) {
            handleException(e)
        }
    }

     fun handleException(e: Exception) {
        // Handle the error gracefully, log it, and optionally take further action.
        Log.e(TAG, "Error occurred during analytics tracking: ${e.message}", e)
        logErrorToBackend(e)
        // Optionally, notify the server or perform other actions based on the error.
    }

    private fun logErrorToBackend(exception: Exception) {
        // Implement the logic to send error details to the backend here
        // You can make another API call to send error logs to your server
        // Ensure you have an endpoint on your server to handle error logs
        Log.e(TAG, "Logging error to backend: $exception")
        // Example: You can make an API call here to log the error to the server.
    }

    companion object {
        private var instance: AnalyticsTracker? = null

        fun getInstance(context: Context): AnalyticsTracker {
            return instance ?: synchronized(this) {
                instance ?: AnalyticsTracker(context).also { instance = it }
            }
        }
    }

}