package com.example.gemstore

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Addtocart : Fragment(), deletebutton {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: Recyclerviewadapter21
    private lateinit var requestQueue: RequestQueue
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var totalTextView: TextView
    private lateinit var dataList: ArrayList<Datamodle21>
    private lateinit var cartid: String
    private var totalPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addtocart, container, false)

        // Reset totalPrice to zero when the view is created
        totalPrice = 0

        requestQueue = Volley.newRequestQueue(requireContext())

        sharedPreferences =
            requireActivity().getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")

        val url = "https://pradeepk-p.online/mycart.php?id=$id"

        recyclerView = view.findViewById(R.id.recyclerView2024)
        totalTextView = view.findViewById(R.id.totalTextView)

        try {
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            recyclerAdapter = Recyclerviewadapter21(requireContext(), ArrayList(), this)
            recyclerView.adapter = recyclerAdapter

            val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val itemsArray = jsonObject.getJSONArray("items")
                        dataList = ArrayList()

                        for (i in 0 until itemsArray.length()) {
                            val itemObject = itemsArray.getJSONObject(i)
                            val name = itemObject.getString("name")
                            val company = itemObject.getString("company")
                            val image = "https://pradeepk-p.online/" + itemObject.getString("image")
                            val mrp = itemObject.getString("mrp")
                            val saving = itemObject.getString("saving")
                            val priceString = itemObject.getString("price")

                            // Remove currency symbol and commas before converting to integer
                            val priceWithoutCurrency = priceString.trim().replace("₹", "").replace(",", "").toInt()

                            cartid = itemObject.getString("cartid")
                            totalPrice += priceWithoutCurrency

                            val model =
                                Datamodle21(name, company, image, saving, mrp, priceString, cartid)
                            dataList.add(model)
                        }

                        requireActivity().runOnUiThread {
                            recyclerAdapter.setData(dataList)
                            totalTextView.text = "₹${totalPrice}"
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    error.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Volley Error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                })

            requestQueue.add(stringRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onItemClicked4(position: Int) {
        val cartId = dataList[position].cartid
        val price = dataList[position].price.trim().replace("₹", "").replace(",", "").toInt() // Remove currency symbol

        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = object : StringRequest(
            Request.Method.GET,
            "https://pradeepk-p.online/deletecartapi.php?cartid=$cartId",
            Response.Listener { response ->
                // Check if the response indicates successful deletion
                if (response == "Deleted successfully") {
                    dataList.removeAt(position) // Remove item from the dataList
                    recyclerAdapter.notifyItemRemoved(position) // Notify adapter about the item removal
                    updateTotalPrice(price) // Update total price
                } else {
                    // Show a toast or handle the case where deletion failed
                    Toast.makeText(requireContext(), "Deletion failed", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // Handle error response
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {}

        queue.add(stringRequest)
    }



    private fun updateTotalPrice(price: Int) {
        totalPrice -= price
        totalTextView.text = "₹${totalPrice}"
    }
}
