package com.example.gemstore

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class main2activity : Fragment(),RecyclerViewItemClickListener , RecyclerViewitemclicklistener3 {

    lateinit var recyclerView1: RecyclerView
    lateinit var recyclerView2: RecyclerView
    lateinit var recyclerAdapter1: Recycleradapter11
    lateinit var recyclerAdapter2: Recyclerviewadapter13
    lateinit var dataList1: ArrayList<Datamodel11>
    lateinit var dataList2: ArrayList<DataModel12>
    lateinit var requestQueue1: RequestQueue
    lateinit var requestQueue2: RequestQueue
    lateinit var pid :String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var userid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main2activity, container, false)

        // Retrieve data from the Intent
        val id = arguments?.getString("key")

        // Display the received data in the TextView
        /*val textView12 = view.findViewById<TextView>(R.id.textview88)*/
/*
        if (id != null) {
            textView12.text = "Received data: $id"
        } else {
            textView12.text = "No data received"
        }*/

        // RecyclerView setup for recyclerView1
        recyclerView1 = view.findViewById(R.id.recyclerview165)
        dataList1 = ArrayList()
        recyclerAdapter1 = Recycleradapter11(
            requireContext(),
            dataList1,
            { clickedData: String -> handleItemClick(clickedData) },
            this
        )
        recyclerView1.adapter = recyclerAdapter1
        recyclerView1.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        requestQueue1 = Volley.newRequestQueue(requireContext())

        sharedPreferences = requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        userid = sharedPreferences.getString("id", "0") ?: ""

        val stringRequest1 = object : StringRequest(
            Request.Method.POST, "https://www.pradeepk-p.online/womencatapi.php",
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("name")
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("image")
                        val model = Datamodel11(image, name)
                        dataList1.add(model)
                    }
                    recyclerAdapter1.notifyDataSetChanged()
                } catch (e: JSONException) {
                    // Handle JSON exception
                    e.printStackTrace()
                }
            },
            { error ->
                // Handle error
                error.printStackTrace()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["cid"] = id.toString()
                return params
            }
        }

        requestQueue1.add(stringRequest1)

        // RecyclerView setup for recyclerView2
        recyclerView2 = view.findViewById(R.id.recyclerview178)
        dataList2 = ArrayList()
        recyclerAdapter2 = Recyclerviewadapter13(requireContext(), dataList2, this)
        recyclerView2.adapter = recyclerAdapter2
        // Use GridLayoutManager instead of LinearLayoutManager for recyclerView2
        recyclerView2.layoutManager =
            GridLayoutManager(requireContext(), 2) // 2 columns grid layout
        requestQueue2 = Volley.newRequestQueue(requireContext())

        val stringRequest2 = object : StringRequest(
            Request.Method.POST, "https://www.pradeepk-p.online/menscatapi.php",
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("name")
                        val company = jsonObject.getString("company")
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("image")
                        // Fetch other required fields like description and price from jsonObject
                        val description = jsonObject.getString("saving")
                        val mrp = jsonObject.getString("mrp")
                        val price = jsonObject.getString("price")
                        val pid = jsonObject.getString("pid")
                        val model = DataModel12(image, name, company, description, mrp, price, pid)
                        dataList2.add(model)
                    }
                    recyclerAdapter2.notifyDataSetChanged()
                } catch (e: JSONException) {
                    // Handle JSON exception
                    e.printStackTrace()
                }
            },
            { error ->
                // Handle error
                error.printStackTrace()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["wid"] = id.toString()
                return params
            }
        }

        requestQueue2.add(stringRequest2)



        return view
    }

    fun handleItemClick(receivedData: String) {
        // Handle item click as needed, e.g., open a new activity with the receivedData
        val intent = Intent(requireContext(), main2activity::class.java)
        intent.putExtra("key", receivedData)
        startActivity(intent)
    }

    override fun onItemClicked(position: Int) {
        Toast.makeText(requireContext(), dataList1[position].image, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked3(position: Int) {
        sharedPreferences = requireActivity().getSharedPreferences("pid", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("pid", dataList2[position].pid)
        editor.apply()
        Toast.makeText(requireContext(), dataList2[position].pid, Toast.LENGTH_SHORT).show()
        val newfragment = pdetailspage()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        // Start a FragmentTransaction
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the new fragment
        transaction.replace(R.id.fragmentContainer, newfragment)

        // Add the transaction to the back stack
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
        true

    }
}
