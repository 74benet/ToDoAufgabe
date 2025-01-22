package com.example.restaufgabe

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

@Composable
fun LoggedInScreen(navController: NavController, firstName: String, lastName: String, udid: String) {
    val updatedFirstName = remember { mutableStateOf(firstName) }
    val updatedLastName = remember { mutableStateOf(lastName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "First Name: $firstName", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Last Name: $lastName", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "UDID: $udid", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = updatedFirstName.value,
            onValueChange = { updatedFirstName.value = it },
            label = { Text("Update First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = updatedLastName.value,
            onValueChange = { updatedLastName.value = it },
            label = { Text("Update Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val client = OkHttpClient()
                        val jsonBody = """
                    {
                        "firstName": "${updatedFirstName.value}",
                        "lastName": "${updatedLastName.value}"
                    }
                """.trimIndent()

                        val requestBody =
                            jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())

                        val request = Request.Builder()
                            .url("http://rest.alnoc.de:3001/users/$udid/update")
                            .patch(requestBody)
                            .build()

                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(
                                        navController.context,
                                        "Fehler: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            override fun onResponse(call: Call, response: Response) {
                                if (response.isSuccessful) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(
                                            navController.context,
                                            "Erfolgreich aktualisiert!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("login")
                                    }
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        when (response.code) {
                                            400 -> Toast.makeText(
                                                navController.context,
                                                "Benutzer existiert bereits",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            else -> Toast.makeText(
                                                navController.context,
                                                "Fehlercode: ${response.code}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        })
                    } catch (e: Exception) {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                navController.context,
                                "Ausnahme: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update and Logout")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val client = OkHttpClient()

                        val request = Request.Builder()
                            .url("http://rest.alnoc.de:3001/users/$udid/delete")
                            .delete()
                            .build()

                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(
                                        navController.context,
                                        "Fehler beim Löschen: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                            override fun onResponse(call: Call, response: Response) {
                                if (response.isSuccessful) {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(
                                            navController.context,
                                            "Benutzer erfolgreich gelöscht",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate("login")
                                    }
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        Toast.makeText(
                                            navController.context,
                                            "Fehlercode: ${response.code}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        })
                    } catch (e: Exception) {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                navController.context,
                                "Ausnahme: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete User")
        }
    }
}
