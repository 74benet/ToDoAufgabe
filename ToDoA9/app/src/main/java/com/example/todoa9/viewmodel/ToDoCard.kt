package com.example.todoa9.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoa9.model.ToDo
import com.example.todoa9.model.formatTimestamp
import com.example.todoa9.model.priorityToString

/**
 * Karte für ein einzelnes ToDo-Element.
 *
 * @param todo Das anzuzeigende ToDo-Element.
 * @param onEdit Aktion für den Bearbeiten-Button.
 * @param onDelete Aktion für den Löschen-Button.
 * @param onMarkAsDone Aktion für den Erledigen-Button.
 */
@Composable
fun ToDoCard(todo: ToDo, onEdit: () -> Unit, onDelete: () -> Unit, onMarkAsDone: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = todo.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text("Priorität: ${priorityToString(todo.priority)}")
            Text("Deadline: ${formatTimestamp(todo.endTime)}")
            Text("Status: ${if (todo.status) "Erledigt" else "Offen"}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onMarkAsDone,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Erledigen")
                }

                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF525FFA)
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Bearbeiten")
                }

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Löschen")
                }
            }
        }
    }
}