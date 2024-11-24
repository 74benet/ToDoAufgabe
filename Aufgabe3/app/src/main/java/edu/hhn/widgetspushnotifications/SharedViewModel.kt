package edu.hhn.widgetspushnotifications

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CounterViewModel : ViewModel() {
    val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()
}