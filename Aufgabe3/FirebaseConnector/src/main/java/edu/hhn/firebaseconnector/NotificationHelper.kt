package edu.hhn.firebaseconnector

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NotificationHelper {
    fun sendNotification(
        title: String,
        message: String,
        callback: (Boolean, String) -> Unit
    ) {
        val request = NotificationRequest(title, message)
        NotificationApiClient.apiService.sendNotification(request).enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(call: Call<NotificationResponse>, response: Response<NotificationResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null && result.success) {
                        callback(true, "Notification sent successfully")
                    } else {
                        callback(false, "Failed to send notification: ${result?.message ?: "Unknown error"}")
                    }
                } else {
                    callback(false, "Failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                callback(false, "Request failed: ${t.message}")
            }
        })
    }
}
