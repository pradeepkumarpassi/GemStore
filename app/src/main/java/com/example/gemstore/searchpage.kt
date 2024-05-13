package com.example.gemstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class searchpage : Fragment() {

    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerAdapter1: Recycleradapter7
    private lateinit var requestQueue: RequestQueue // Add this line to declare requestQueue

    private val url1 = "https://pradeepk-p.online/bannerapi.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_searchpage, container, false)

        // Initialize requestQueue
        requestQueue = Volley.newRequestQueue(requireContext())

        recyclerView1 = view.findViewById(R.id.bannerRecyclerView)
        fetchDataForRecyclerView1()

        return view
    }

    private fun fetchDataForRecyclerView1() {
        val dataList1 = ArrayList<datamodel7>()
        recyclerAdapter1 = Recycleradapter7(requireContext(), dataList1)
        recyclerView1.adapter = recyclerAdapter1
        recyclerView1.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val stringRequest1 = StringRequest(
            Request.Method.POST, url1,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 4 until 8) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("image")

                        val model = datamodel7(image)
                        dataList1.add(model)
                    }
                    recyclerAdapter1.notifyDataSetChanged()
                } catch (e: JSONException) {
                    handleJsonException(e)
                }
            },
            { error -> handleRequestError(error) })

        requestQueue.add(stringRequest1)
    }

    private fun handleJsonException(e: JSONException) {
        e.printStackTrace()
        // Handle JSON exception
    }

    private fun handleRequestError(error: VolleyError) {
        error.printStackTrace()
        // Handle request error
    }

    override fun onDestroy() {
        super.onDestroy()
        // Properly close the RequestQueue to prevent memory leaks
        requestQueue.cancelAll { true }
        requestQueue.stop()
    }
}
