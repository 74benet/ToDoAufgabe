package com.example.todoa9.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.todoa9.database.ToDoDatabaseHelper
import com.example.todoa9.model.formatTimestamp
import com.example.todoa9.model.priorityToString

/**
 * Dialog zum Hinzufügen eines neuen ToDo-Elements.
 *
 * @param dbHelper Instanz des Datenbank-Helpers.
 * @param onClose Aktion beim Schließen des Dialogs.
 */
@Composable
fun AddToDoDialog(dbHelper: ToDoDatabaseHelper, onClose: () -> Unit) {
    val context = LocalContext.current as FragmentActivity
    var name by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(1) }
    var description by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf(System.currentTimeMillis()) }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Neues ToDo hinzufügen", style = MaterialTheme.typography.headlineSmall) },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Beschreibung") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Priorität:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { priority = 1 },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFACA52)),
                        modifier = Modifier.weight(1f)
                    ) { Text("1") }
                    Button(
                        onClick = { priority = 2 },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA7952)),
                        modifier = Modifier.weight(1f)
                    ) { Text("2") }
                    Button(
                        onClick = { priority = 3 },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA5252)),
                        modifier = Modifier.weight(1f)
                    ) { Text("3") }
                }

                Text("Ausgewählte Priorität: ${priorityToString(priority)}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))

                Button(
                    onClick = {
                        showDateTimePicker(context) { selectedTime ->
                            endTime = selectedTime
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B3FB5)),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Endzeit auswählen")
                }
                Text("Endzeit: ${formatTimestamp(endTime)}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B3FB5)),
                ) {
                    Text("Abbrechen")
                }

                Button(
                    onClick = {
                        dbHelper.addToDo(
                            name = name,
                            priority = priority,
                            endTime = endTime,
                            description = description,
                            status = false
                        )
                        onClose()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                ) {
                    Text("Speichern")
                }
            }
        },
        dismissButton = null,
        containerColor = MaterialTheme.colorScheme.background
    )
}