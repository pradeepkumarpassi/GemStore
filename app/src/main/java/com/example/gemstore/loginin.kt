package com.example.gemstore

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class loginin : AppCompatActivity() {

    private lateinit var btn3: Button
    private lateinit var btn5: TextView
    private lateinit var txt1: EditText
    private lateinit var txt2: EditText
    private lateinit var btn6: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginin)

        sharedPreferences = getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)

        // Check if the user is already logged in
        if (isLoggedIn()) {
            redirectToHome()
            return
        }

        txt1 = findViewById(R.id.textView6)
        txt2 = findViewById(R.id.textView8)
        btn3 = findViewById(R.id.button5)
        btn5 = findViewById(R.id.textView15)
        btn6 = findViewById(R.id.textView11)

        btn5.setOnClickListener {
            redirectToHome()
        }
        btn6.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
        btn3.setOnClickListener {
            val email = txt1.text.toString().trim()
            val password = txt2.text.toString().trim()

            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check for internet connectivity
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = "https://www.pradeepk-p.online/loginpageapi.php"
            val queue: RequestQueue = Volley.newRequestQueue(this)

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    // Handle the API response on the main thread
                    runOnUiThread {
                        try {
                            val jsonResponse = JSONObject(response)
                            val name = jsonResponse.getString("success")
                            val userId = jsonResponse.getString("message")
                            val id=jsonResponse.getString("id");
                            val editor = sharedPreferences.edit()
                            editor.putString("id", id)
                            editor.apply()

                            Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
                            when (name) {
                                "true" -> {
                                    // Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                                    // Check if the login was successful
                                    if (userId == "Login successful.") {
                                        // Save login status
                                        saveLoginStatus(true)

                                        val intent = Intent(this, Home::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                                else -> {
                                    Toast.makeText(this, "Error: $userId", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this, "Exception: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                Response.ErrorListener { error ->
                    // Handle errors on the main thread
                    runOnUiThread {
                        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }

            // Add the request to the queue to execute it asynchronously
            queue.add(stringRequest)
        }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    private fun redirectToHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
