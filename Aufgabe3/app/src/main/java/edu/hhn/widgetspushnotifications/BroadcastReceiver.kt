package edu.hhn.widgetspushnotifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CounterUpdateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val counter = intent.getIntExtra("counter", 0)

        // Update ViewModel
        val viewModel = ViewModelProvider(context as ComponentActivity)[CounterViewModel::class.java]
        viewModel._counter.value = counter

        // Update widget state
        val manager = GlanceAppWidgetManager(context)
        GlobalScope.launch {
            manager.getGlanceIds(BroadcastWidget::class.java).forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    glanceId = glanceId
                ) { prefs ->
                    prefs[countKey] = counter
                }
                BroadcastWidget().update(context, glanceId)
            }
        }
    }
}