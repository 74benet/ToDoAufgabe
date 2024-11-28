package edu.hhn.widgetspushnotifications

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

val countKey = intPreferencesKey("count")

class BroadcastWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Column(
                modifier = GlanceModifier
                    .background(Color(10, 10, 10, 85))
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
                verticalAlignment = Alignment.Vertical.CenterVertically
            ) {
                Text(
                    text = "Counter: ${currentState(countKey) ?: 0}",
                    modifier = GlanceModifier
                        .padding(bottom = 16.dp),
                    style = TextStyle(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = ColorProvider(Color.White)
                    )
                )

                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.Horizontal.CenterHorizontally
                ) {
                    Button(
                        text = "+",
                        onClick = actionRunCallback<IncrementAction>(),
                        modifier = GlanceModifier
                            .padding(start = 2.dp)
                            .height(50.dp)
                            .defaultWeight(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Button(
                        text = "-",
                        onClick = actionRunCallback<DecrementAction>(),
                        modifier = GlanceModifier
                            .padding(end = 2.dp)
                            .height(50.dp)
                            .defaultWeight(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Button(
                    text = "Send Update",
                    onClick = actionRunCallback<SendNotificationAction>(),
                    modifier = GlanceModifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),


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
            val intent = Intent("COUNTER_UPDATE_ACTION")
            intent.putExtra("counter", currentCount)
            context.sendBroadcast(intent)
        }
        BroadcastWidget().update(context, glanceId)
    }
}