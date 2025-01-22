package com.example.todoa9.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoa9.database.ToDoDatabaseHelper
import com.example.todoa9.model.ToDo


/**
 * Haupt-Dashboard-Screen der ToDo-App.
 * Zeigt aktive und erledigte ToDos an und ermöglicht das Hinzufügen, Bearbeiten und Löschen von ToDos.
 */
@Composable
fun DashboardScreen(dbHelper: ToDoDatabaseHelper) {
    val todos = remember { mutableStateOf(emptyList<ToDo>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<ToDo?>(null) }
    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        todos.value = dbHelper.getAllToDos()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add ToDo")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                ) {
                    Text(
                        "Aktive ToDos",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                ) {
                    Text(
                        "Erledigte ToDos",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            val filteredTodos = if (selectedTab == 0) {
                todos.value.filter { !it.status }
            } else {
                todos.value.filter { it.status }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredTodos) { todo ->
                    ToDoCard(
                        todo = todo,
                        onEdit = { showEditDialog = todo },
                        onDelete = {
                            todo.id?.let { dbHelper.deleteToDo(it) }
                            todos.value = dbHelper.getAllToDos()
                        },
                        onMarkAsDone = {
                            todo.id?.let { dbHelper.markToDoAsDone(it) }
                            todos.value = dbHelper.getAllToDos()
                        }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddToDoDialog(
            dbHelper = dbHelper,
            onClose = {
                showAddDialog = false
                todos.value = dbHelper.getAllToDos()
            }
        )
    }

    if (showEditDialog != null) {
        EditToDoDialog(
            dbHelper = dbHelper,
            todo = showEditDialog!!,
            onClose = {
                showEditDialog = null
                todos.value = dbHelper.getAllToDos()
            }
        )
    }
}

