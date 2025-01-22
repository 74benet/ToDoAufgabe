package com.example.todoa9

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.todoa9.database.ToDoDatabaseHelper
import com.example.todoa9.ui.theme.ToDoA9Theme
import com.example.todoa9.viewmodel.DashboardScreen

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ToDoDatabaseHelper(this)
        setContent {
            ToDoA9Theme {
                DashboardScreen(dbHelper = dbHelper)
            }
        }
    }
}
