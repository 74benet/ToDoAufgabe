package edu.hhn.widgetspushnotifications

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import edu.hhn.firebaseconnector.NotificationHelper

val countKey = intPreferencesKey("count")

class BroadcastWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val count = currentState(countKey) ?: 0
                Text(text = "Counter: $count")
                Row {
                    Button(
                        text = "+",
                        onClick = actionRunCallback<IncrementAction>(),
                        modifier = GlanceModifier
                    )
                    Button(
                        text = "-",
                        onClick = actionRunCallback<DecrementAction>(),
                        modifier = GlanceModifier
                    )
                }
                Button(
                    text = "Send Update",
                    onClick = actionRunCallback<SendNotificationAction>(),
                    modifier = GlanceModifier
                )
            }
        }
    }
}

class IncrementAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(
            context = context,
            glanceId = glanceId
        ) { prefs ->
            val currentCount = prefs[countKey] ?: 0
            prefs[countKey] = currentCount + 1

            // Send broadcast to MainActivity/ViewModel
            val intent = Intent("COUNTER_UPDATE_ACTION")
            intent.putExtra("counter", currentCount + 1)
            context.sendBroadcast(intent)
        }

        BroadcastWidget().update(context, glanceId)
    }
}

class DecrementAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        updateAppWidgetState(
            context = context,
            glanceId = glanceId
        ) { prefs ->
            val currentCount = prefs[countKey] ?: 0
            prefs[countKey] = currentCount - 1

            // Send broadcast to MainActivity/ViewModel
            val intent = Intent("COUNTER_UPDATE_ACTION")
            intent.putExtra("counter", currentCount - 1)
            context.sendBroadcast(intent)
        }

        BroadcastWidget().update(context, glanceId)
    }
}


class SendNotificationAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(
            context = context,
            glanceId = glanceId
        ) { prefs ->
            val currentCount = prefs[countKey] ?: 0
            NotificationHelper.sendNotification(
                "Counter Update",
                "Current counter value: $currentCount"
            ) { success, message ->
                Log.d("SendNotificationAction", "Notification sent: $success, Message: $message")
            }
        }
        BroadcastWidget().update(context, glanceId)
    }
}