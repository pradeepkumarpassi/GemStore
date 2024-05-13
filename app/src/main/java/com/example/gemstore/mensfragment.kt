package com.example.gemstore

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONException

class mensfragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var url: String? = null
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mensfragment, container, false)
        imageView = view.findViewById(R.id.image1234)
        textView1 = view.findViewById(R.id.name1234)
        textView2 = view.findViewById(R.id.description1234)
        textView3 = view.findViewById(R.id.savings1234)
        textView4 = view.findViewById(R.id.rating1234)
        textView5 = view.findViewById(R.id.ratingg1234)
        textView6 = view.findViewById(R.id.mrp1234)
        textView7 = view.findViewById(R.id.price1234)

        sharedPreferences = requireActivity().getSharedPreferences("pid", AppCompatActivity.MODE_PRIVATE)
        val pid = sharedPreferences.getString("pid", "")

        if (!pid.isNullOrEmpty()) {
            url = "https://www.pradeepk-p.online/productcatapii.php?pid=$pid"
            requestQueue = Volley.newRequestQueue(requireContext())

            url?.let { safeUrl ->
                val stringRequest = object : StringRequest(
                    Request.Method.POST, safeUrl,
                    Response.Listener { response ->
                        try {
                            val jsonArray = JSONArray(response)
                            if (jsonArray.length() > 0) {
                                val jsonObject = jsonArray.getJSONObject(0)
                                val name = jsonObject.getString("pname")
                                val imageurl = "https://pradeepk-p.online/" + jsonObject.getString("pimage")
                                val description = jsonObject.getString("description")
                                val saving = jsonObject.getString("saving")
                                val rating = jsonObject.getString("rating")
                                val ratingg = jsonObject.getString("ratingg")
                                val mrp = jsonObject.getString("mrp")
                                val price = jsonObject.getString("price")

                                textView1.text = name
                                textView2.text = description
                                textView3.text = saving
                                textView4.text = rating
                                textView5.text = ratingg
                                textView6.text = mrp
                                textView7.text = price
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
                        params["pid"] = sharedPreferences.getString("pid", "").toString()
                        return params
                    }
                }
                requestQueue.add(stringRequest)
            }
        } else {
            Toast.makeText(requireContext(), "PID is empty!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}