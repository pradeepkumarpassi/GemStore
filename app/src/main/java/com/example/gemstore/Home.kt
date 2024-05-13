package com.example.gemstore

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class Home : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var imageView: ImageView
    private lateinit var navigationView: NavigationView
    private lateinit var requestQueue: RequestQueue

    private val homeFragment = homepage()
    private val searchFragment = searchpage()
    private val addtocartFragment = Addtocart()
    private val profileFragment = profilepage()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView1)
        imageView = findViewById(R.id.nav)

        imageView.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.nav_notification -> {
                    replaceFragment(searchFragment)
                    true
                }
                R.id.nav_cart -> {
                    replaceFragment(addtocartFragment)
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(profileFragment)
                    true
                }
                R.id.nav_settings -> {
                    replaceFragment(profileFragment)
                    true
                }
                R.id.nav_support-> {
                    replaceFragment(profileFragment)
                    true
                }
                R.id.nav_aboutus -> {
                    replaceFragment(profileFragment)
                    true
                }
                // Add more cases for other menu items as needed

                else -> false
            }
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_homepage -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    replaceFragment(searchFragment)
                    true
                }
                R.id.navigation_cart -> {
                    replaceFragment(addtocartFragment)
                    true
                }
                R.id.navigation_profile -> {
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
        requestQueue = Volley.newRequestQueue(this)
        updateProfileInfo() // Call this function to update profile information initially
        replaceFragment(homeFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun updateProfileInfo() {
        val sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")

        val url = "https://www.pradeepk-p.online/profilepage.php?id=$id"

        if (isNetworkAvailable()) {
            val stringRequest = StringRequest(
                Request.Method.POST, url,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val name = jsonObject.getString("name")
                        val email = jsonObject.getString("email")
                        val imageUrl = "https://www.pradeepk-p.online/uploads/" + jsonObject.getString("image")

                        // Update the TextViews and ImageView with fetched data
                        val userTextView = navigationView.getHeaderView(0).findViewById<TextView>(R.id.userNameTxt)
                        val userEmail = navigationView.getHeaderView(0).findViewById<TextView>(R.id.emailTxt)
                        val userImageView = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.profileImg)


                        userTextView.text = name
                        userEmail.text = email

                        Picasso.get().load(imageUrl).into(userImageView as ImageView)

                        (R.id.profileImg)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e("ProfilePage", "Error parsing profile data: ${e.message}")
                        Toast.makeText(
                            applicationContext,
                            "Error parsing profile data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                { error ->
                    error.printStackTrace()
                    Log.e("ProfilePage", "${error.message}")
                    Toast.makeText(
                        applicationContext,
                        "Error fetching profile data",
                        Toast.LENGTH_SHORT
                    ).show()
                })

            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
