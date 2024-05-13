package com.example.gemstore

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONException

class womensFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    private lateinit var textView8: TextView
    private lateinit var cartbtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var imageView1: String
    private var url: String? = null
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_womens, container, false)
        imageView = view.findViewById(R.id.image123)
        textView1 = view.findViewById(R.id.name123)
        textView2 = view.findViewById(R.id.description123)
        textView3 = view.findViewById(R.id.savings123)
        textView4 = view.findViewById(R.id.rating123)
        textView5 = view.findViewById(R.id.ratingg123)
        textView6 = view.findViewById(R.id.mrp123)
        textView7 = view.findViewById(R.id.price123)
        textView8 = view.findViewById(R.id.company123)
        cartbtn = view.findViewById(R.id.addtocartbtn1)


        sharedPreferences =
            requireActivity().getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")

        sharedPreferences = requireActivity().getSharedPreferences("sid", AppCompatActivity.MODE_PRIVATE)
        val sid = sharedPreferences.getString("sid", "")

        if (!sid.isNullOrEmpty()) {
            url = "https://pradeepk-p.online/productsubapi.php?sid=$sid"
            requestQueue = Volley.newRequestQueue(requireContext())

            url?.let { safeUrl ->
                val stringRequest = object : StringRequest(
                    Request.Method.POST, safeUrl,
                    Response.Listener { response ->
                        try {
                            val jsonArray = JSONArray(response)
                            if (jsonArray.length() > 0) {
                                val jsonObject = jsonArray.getJSONObject(0)
                                val name = jsonObject.getString("sname")
                                val imageurl = "https://pradeepk-p.online/" + jsonObject.getString("simage")
                                val description = jsonObject.getString("description")
                                val saving = jsonObject.getString("saving")
                                val rating = jsonObject.getString("rating")
                                val ratingg = jsonObject.getString("ratingg")
                                val mrp = jsonObject.getString("mrp")
                                val price = jsonObject.getString("price")
                                val company = jsonObject.getString("company")

                                textView1.text = name
                                textView2.text = description
                                textView3.text = saving
                                textView4.text = rating
                                textView5.text = ratingg
                                textView6.text = mrp
                                textView7.text = price
                                textView8.text = company

                                Glide.with(requireContext()).load(imageurl).into(imageView)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        // Handle error
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        // Add any additional parameters here if needed
                        params["sid"] = sharedPreferences.getString("sid", "").toString()
                        return params
                    }
                }
                requestQueue.add(stringRequest)
            }
        } else {
            Toast.makeText(requireContext(), "SID is empty!", Toast.LENGTH_SHORT).show()
        }
        cartbtn.setOnClickListener {
            val url = "https://www.pradeepk-p.online/addtocartapi.php"

            Log.d("CartButton", "Clicked!") // Add log statement to check if button click is registered

            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    // Handle the response here
                    Log.d("CartButton", "Response: $response") // Log the response
                    Toast.makeText(requireContext(), "Item added to cart", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Log.e("CartButton", "Error: ${error.message}") // Log the error
                    // Handle errors
                }) {
                // Override getParams() if you need to send parameters in your request
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["pid"] = sid.toString()
                    params["id"] = id.toString()
                    params["price"] = textView7.text.toString()
                    params["name"] = textView1.text.toString()
                    params["image"] = imageView1
                    params["mrp"] = textView6.text.toString()
                    params["saving"] = textView3.text.toString()
                    params["company"] = textView8.text.toString()

                    Log.d("CartButton", "Params: $params") // Log the parameters

                    return params
                }
            }

            // Add the request to the RequestQueue
            requestQueue.add(stringRequest)
        }

        return view
    }
}
