package com.example.bookshelf

import BookShelfApp
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import com.example.bookshelf.ui.theme.BookShelfTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val notificationsEnabled = mutableStateOf(false)


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                notificationsEnabled.value = true
            } else {
                notificationsEnabled.value = false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationHelper.createNotificationChannel(this)

        setContent {
            BookShelfTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed) // DrawerState in MainActivity erstellen
                val coroutineScope = rememberCoroutineScope()
                val notificationsEnabled = remember { mutableStateOf(false) }

                BookShelfApp(
                    notificationsEnabled = notificationsEnabled,
                    onToggleNotifications = { isChecked ->
                        handleNotificationsToggle(isChecked)
                    },
                    onNotificationClick = {
                        if (notificationsEnabled.value) {
                            showNotification("New Book Alert", "Check out the latest book!")
                        }
                    },
                    onSettingsClick = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    drawerState = drawerState
                )
            }
        }
    }

    private fun handleNotificationsToggle(isChecked: Boolean) {
        if (isChecked) {
            checkAndRequestNotificationPermission()
        } else {
            notificationsEnabled.value = false
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                notificationsEnabled.value = true
            }
        } else {
            notificationsEnabled.value = true
        }
    }

    private fun showNotification(title: String, message: String) {
        NotificationHelper.showNotification(this, title, message)
    }
}
