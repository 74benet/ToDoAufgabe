package com.example.todoa9.viewmodel

import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.Calendar

/**
 * Zeigt einen Dialog zur Auswahl von Datum und Uhrzeit.
 *
 * @param context Kontext der aufrufenden Activity.
 * @param onDateTimeSelected Aktion bei erfolgreicher Auswahl von Datum und Uhrzeit.
 */
fun showDateTimePicker(context: FragmentActivity, onDateTimeSelected: (Long) -> Unit) {
    val currentTime = System.currentTimeMillis()

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("")
        .setSelection(currentTime)
        .build()

    datePicker.addOnPositiveButtonClickListener { selectedDate ->
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDate

        val timePicker = MaterialTimePicker.Builder()
            .setTitleText("Uhrzeit auswählen")
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .build()

        timePicker.addOnPositiveButtonClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)
            onDateTimeSelected(calendar.timeInMillis)
        }

        timePicker.show(context.supportFragmentManager, "timePicker")
    }

    datePicker.show(context.supportFragmentManager, "datePicker")
}