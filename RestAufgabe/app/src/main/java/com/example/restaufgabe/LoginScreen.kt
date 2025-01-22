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
import androidx.compose.ui.platform.LocalContext
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
import org.json.JSONObject
import java.io.IOException

@Composable
fun LoginScreen(navController: NavController) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val client = OkHttpClient()
                    val jsonBody = """
                        {
                            "firstName": "${firstName.value}",
                            "lastName": "${lastName.value}"
                        }
                    """.trimIndent()

                    val requestBody =
                        jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())

                    val request = Request.Builder()
                        .url("http://rest.alnoc.de:3001/login")
                        .post(requestBody)
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Fehler: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onResponse(call: Call, response: Response) {
                            CoroutineScope(Dispatchers.Main).launch {
                                if (response.isSuccessful) {
                                    val responseBody = response.body?.string() ?: ""
                                    val jsonObject = JSONObject(responseBody)
                                    val firstNameValue = jsonObject.getString("firstName")
                                    val lastNameValue = jsonObject.getString("lastName")
                                    val udidValue = jsonObject.getString("udid")

                                    navController.navigate("logged_in/$firstNameValue/$lastNameValue/$udidValue")
                                } else {
                                    when (response.code) {
                                        401 -> Toast.makeText(context, "Benutzer nicht gefunden", Toast.LENGTH_LONG).show()
                                        else -> Toast.makeText(context, "Fehlercode: ${response.code}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    })
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}