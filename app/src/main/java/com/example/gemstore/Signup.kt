package com.example.gemstore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Signup : AppCompatActivity() {
    lateinit var txt33: TextView
    lateinit var btn: Button
    lateinit var tex1: TextView
    lateinit var tex2: TextView
    lateinit var tex3: TextView
    lateinit var tex4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        txt33 = findViewById(R.id.textView5)
        btn = findViewById(R.id.button2)
        tex1 = findViewById(R.id.edittext1)
        tex2 = findViewById(R.id.edittext2)
        tex3 = findViewById(R.id.edittext3)
        tex4 = findViewById(R.id.edittext4)

        txt33.setOnClickListener {
            val intent = Intent(this, loginin::class.java)
            startActivity(intent)
        }

        btn.setOnClickListener {
            val name = tex1.text.toString().trim()
            val email = tex3.text.toString().trim()
            val password = tex2.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate email format (you can use a more advanced email validation)
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate password strength (you can customize the criteria)
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = "https://pradeepk-p.online/registerapi.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    // Handle the response
                    Log.d("Response", response)
                    if (response.contains("success")) {
                        // Registration successful
                        val intent = Intent(this, loginin::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // Registration failed or other issues
                        Toast.makeText(this, "Registration failed: $response", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error: VolleyError ->
                    // Handle errors
                    Log.e("Error", "Error occurred: ${error.message}")
                    Toast.makeText(
                        this,
                        "Error occurred while connecting to the server",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                override fun getParams(): Map<String, String> {
                    // Set up the parameters
                    val params = HashMap<String, String>()
                    params["name"] = name
                    params["email"] = email
                    params["password"] = password
                    return params
                }
            }

            // Add the request to the RequestQueue
            Volley.newRequestQueue(this).add(stringRequest)
        }
    }
}
