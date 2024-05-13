package com.example.gemstore

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class profilepage : Fragment() {

    lateinit var listView: ListView
    lateinit var imagelist: ArrayList<Int>
    lateinit var titlelist: ArrayList<String>
    lateinit var imagelist2: ArrayList<Int>
    lateinit var userTextView: TextView
    lateinit var userImageView: ImageView
    lateinit var userEmail: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var requestQueue: RequestQueue


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profilepage, container, false)

        userTextView = view.findViewById(R.id.text4658)
        userImageView = view.findViewById(R.id.image733)
        userEmail = view.findViewById(R.id.text4659)
        listView = view.findViewById(R.id.recyclerViewProfile)


        requestQueue = Volley.newRequestQueue(requireContext())
        imagelist = ArrayList()
        titlelist = ArrayList()
        imagelist2 = ArrayList()

        // Populate sample data
        imagelist.add(R.drawable.ic_locat_24)
        titlelist.add("Address")
        imagelist2.add(R.drawable.arrow)

        imagelist.add(R.drawable.card_24)
        titlelist.add("Payment Method")
        imagelist2.add(R.drawable.arrow)

        imagelist.add(R.drawable.voucher_24)
        titlelist.add("Voucher")
        imagelist2.add(R.drawable.arrow)

        imagelist.add(R.drawable.herat_24)
        titlelist.add("My Wishlist")
        imagelist2.add(R.drawable.arrow)

        imagelist.add(R.drawable.star_24)
        titlelist.add("Rate this app")
        imagelist2.add(R.drawable.arrow)

        imagelist.add(R.drawable.logout_24)
        titlelist.add("Log out")
        imagelist2.add(R.drawable.arrow)

        val customListAdapter = recyleradapter5(requireContext(), imagelist, titlelist, imagelist2)

        // Set the adapter to the ListView
        listView.adapter = customListAdapter

        // Set item click listener to the ListView
        listView.setOnItemClickListener { parent, view, position, id ->
            // Check if the clicked item is the "Log out" item
            when (titlelist[position]) {
                "Log out" -> showLogoutConfirmationDialog()
                "Address" -> openGoogleMaps()
            }
        }


        sharedPreferences =
            requireActivity().getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")

        val url = "https://www.pradeepk-p.online/profilepage.php?id=$id"
        Toast.makeText(requireContext(), "$id", Toast.LENGTH_SHORT).show()

        if (isNetworkAvailable()) {
            val stringRequest = StringRequest(
                Request.Method.POST, url,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val name = jsonObject.getString("name")
                        val email = jsonObject.getString("email")
                        val imageUrl =
                            "https://www.pradeepk-p.online/uploads/" + jsonObject.getString("image")

                        userTextView.text = name
                        userEmail.text = email

                        Picasso.get().load(imageUrl).into(userImageView)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e("ProfilePage", "Error parsing profile data: ${e.message}")
                        Toast.makeText(
                            requireContext(),
                            "Error parsing profile data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                { error ->
                    error.printStackTrace()
                    Log.e("ProfilePage", "${error.message}")
                    Toast.makeText(
                        requireContext(),
                        "Error fetching profile data",
                        Toast.LENGTH_SHORT
                    ).show()
                })

            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    // Function to show logout confirmation dialog
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes") { dialog, which ->
            // User clicked Yes, perform logout
            performLogout()
        }
        builder.setNegativeButton("No") { dialog, which ->
            // User clicked No, dismiss dialog
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    // Function to perform logout
    private fun performLogout() {
        // Clear user session or perform any logout operations here
        // For example, clear shared preferences
        sharedPreferences.edit().clear().apply()

        // Navigate to the login screen
        val intent = Intent(requireContext(), loginin::class.java)
        startActivity(intent)

        // Finish the current activity
        requireActivity().finish()
    }

    private fun openGoogleMaps() {
        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        // Assuming you want to open a specific address in Google Maps
        val address = "123 Main St, City, Country"

        // Create a Uri from the address string
        val mapUrl = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}"

        // Create an Intent to open the address in Google Maps with the user's current location
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$address&mode=d"))

        // Verify that the intent will resolve to an activity
        if (mapIntent.resolveActivity(requireContext().packageManager) != null) {
            // Start the browser activity
            startActivity(mapIntent)
        } else {
            // Handle case where no activity can handle the intent
            Toast.makeText(requireContext(), "No app can handle this action", Toast.LENGTH_SHORT).show()
        }
    }
}
