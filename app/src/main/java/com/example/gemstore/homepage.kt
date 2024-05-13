package com.example.gemstore

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class homepage : Fragment(), RecyclerViewItemClickListener, Recyclerviewitemclicklistener2 {
    private lateinit var viewPager: ViewPager2
    private lateinit var requestQueue: RequestQueue
    private lateinit var sid: String
    private lateinit var dataList2: ArrayList<DataModel2>
    private lateinit var pid: String
    private lateinit var dataList3: ArrayList<datamodel3>
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerAdapter1: Recycleradapter
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerAdapter2: Recycleradapter2
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerAdapter3: Recycleradapter3
    private lateinit var recyclerView4: RecyclerView
    private lateinit var recyclerAdapter4: Recycleradapter4
    private lateinit var recyclerView8: RecyclerView
    private lateinit var recyclerAdapter8: RecyclerViewAdapter8
    private lateinit var recyclerView2023: RecyclerView
    private lateinit var indicatorLayout2023: LinearLayout
    private lateinit var indicatorViews2023: ArrayList<View>
    private lateinit var sharedPreferences: SharedPreferences
    private val imageUrlList2023 = mutableListOf<String>()
    private val url1 = "https://pradeepk-p.online/displaycatapi.php"
    private val url2 = "https://pradeepk-p.online/subcatapi.php"
    private val url3 = "https://pradeepk-p.online/productcatapi.php"
    private val url4 = "https://pradeepk-p.online/bannerapi.php"

    // Handler for auto-scrolling
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnable: Runnable
    private var currentItemPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

        sharedPreferences = requireContext().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)

        viewPager = view.findViewById(R.id.viewPager)
        requestQueue = Volley.newRequestQueue(requireContext())

        recyclerView1 = view.findViewById(R.id.recyclerview2)
        recyclerView2 = view.findViewById(R.id.recyclerView3)
        recyclerView3 = view.findViewById(R.id.recyclerView4)
        recyclerView4 = view.findViewById(R.id.recyclerview166)
        recyclerView8 = view.findViewById(R.id.recyclerView8)
        recyclerView2023 = view.findViewById(R.id.recyclerView2023)
        indicatorLayout2023 = view.findViewById(R.id.indicatorLayout2023)

        fetchDataForRecyclerView1()
        fetchDataForRecyclerView2()
        fetchDataForRecyclerView3()
        fetchDataForRecyclerView4()
        fetchDataForRecyclerView8()
        fetchImageUrls2023()

        return view
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    private fun startAutoScroll() {
        autoScrollRunnable = Runnable {
            viewPager.currentItem = currentItemPosition
            currentItemPosition++
            if (currentItemPosition == imageUrlList2023.size) {
                currentItemPosition = 0
            }
            handler.postDelayed(autoScrollRunnable, 3000) // Schedule next execution after 3 seconds
        }
        handler.post(autoScrollRunnable)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    private fun fetchDataForRecyclerView1() {
        val dataList1 = ArrayList<DataModel>()
        recyclerAdapter1 = Recycleradapter(requireContext(), dataList1)

        recyclerAdapter1.setOnItemClickListener(object : Recycleradapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString("key", dataList1[position].id.toString())

                val newFragment = main2activity()
                newFragment.arguments = bundle

                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        recyclerView1.adapter = recyclerAdapter1
        recyclerView1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val stringRequest1 = StringRequest(
            Request.Method.POST, url1,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("name")
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("image")
                        val id = jsonObject.getString("id")
                        val editor = sharedPreferences.edit()
                        editor.putString("id", "" + id)
                        editor.apply()

                        val model = DataModel(image, name,id)
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

    private fun fetchDataForRecyclerView2() {
        dataList2 = ArrayList<DataModel2>()
        recyclerAdapter2 = Recycleradapter2(requireContext(), dataList2,this)

        recyclerView2.adapter = recyclerAdapter2
        recyclerView2.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val stringRequest2 = StringRequest(
            Request.Method.POST, url2,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("sname")
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("simage")
                        sid = jsonObject.getString("sid")
                        val model = DataModel2(image, name, sid)
                        dataList2.add(model)
                    }
                    recyclerAdapter2.notifyDataSetChanged()
                } catch (e: JSONException) {
                    handleJsonException(e)
                }
            },
            { error -> handleRequestError(error) })

        requestQueue.add(stringRequest2)
    }

    private fun fetchDataForRecyclerView3() {
        dataList3 = ArrayList<datamodel3>()
        recyclerAdapter3 = Recycleradapter3(dataList3,this)
        recyclerView3.adapter = recyclerAdapter3
        recyclerView3.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val stringRequest3 = StringRequest(
            Request.Method.POST, url3,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("pname")
                        val price = jsonObject.getString("price")
                        val imageUrl = "https://pradeepk-p.online/" + jsonObject.getString("pimage")
                        pid = jsonObject.getString("pid")
                        val model = datamodel3(name, price, imageUrl,pid)
                        dataList3.add(model)
                    }
                    recyclerAdapter3.notifyDataSetChanged()
                } catch (e: JSONException) {
                    handleJsonException(e)
                }
            },
            { error -> handleRequestError(error) })

        requestQueue.add(stringRequest3)
    }

    private fun fetchDataForRecyclerView8() {
        val dataList8 = ArrayList<DataModel8>()
        recyclerAdapter8 = RecyclerViewAdapter8(requireContext(), dataList8)
        recyclerView8.adapter = recyclerAdapter8
        recyclerView8.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val stringRequest8 = StringRequest(
            Request.Method.POST, url4,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 3 until 4) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("image")

                        val model = DataModel8(image)
                        dataList8.add(model)
                    }
                    recyclerAdapter8.notifyDataSetChanged()
                } catch (e: JSONException) {
                    handleJsonException(e)
                }
            },
            { error -> handleRequestError(error) })

        requestQueue.add(stringRequest8)
    }
    private fun fetchDataForRecyclerView4() {
        val dataList4 = ArrayList<Datamodel4>()
        recyclerAdapter4 = Recycleradapter4(requireContext(), dataList4)
        recyclerView4.adapter = recyclerAdapter4
        recyclerView4.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val stringRequest4 = StringRequest(
            Request.Method.POST, url4,
            { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 8 until 10) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val image = "https://pradeepk-p.online/" + jsonObject.getString("image")

                        val model = Datamodel4(image)
                        dataList4.add(model)
                    }
                    recyclerAdapter4.notifyDataSetChanged()
                } catch (e: JSONException) {
                    handleJsonException(e)
                }
            },
            { error -> handleRequestError(error) })

        requestQueue.add(stringRequest4)
    }

    private fun fetchImageUrls2023() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView2023.layoutManager = layoutManager

        recyclerView2023.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                updateIndicators2023(firstVisibleItemPosition)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            try {
                val apiUrl = "https://www.pradeepk-p.online/bannerapi.php"
                val response = withContext(Dispatchers.IO) { makeApiCall(apiUrl) }

                if (response.isNotBlank()) {
                    val imageUrlList = parseJsonResponse(response)

                    val adapter = ImageAdapter121(imageUrlList)
                    recyclerView2023.adapter = adapter

                    indicatorViews2023 = ArrayList()
                    setupIndicators2023(adapter.itemCount)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Empty or blank response",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Error fetching image URLs",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private suspend fun makeApiCall(apiUrl: String): String {
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection
        return try {
            val reader = InputStreamReader(connection.inputStream)
            reader.readText()
        } finally {
            connection.disconnect()
        }
    }

    private fun parseJsonResponse(response: String): List<String> {
        val imageUrlList = mutableListOf<String>()
        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until 4) {
                val imageUrl =
                    "https://pradeepk-p.online/" + jsonArray.getJSONObject(i).getString("image")
                imageUrlList.add(imageUrl)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imageUrlList
    }

    private fun setupIndicators2023(numIndicators: Int) {
        indicatorLayout2023.removeAllViews()
        for (i in 0 until numIndicators) {
            val indicator = View(requireContext())
            val size = resources.getDimensionPixelSize(R.dimen.indicator_size)
            val margin = resources.getDimensionPixelSize(R.dimen.indicator_margin)
            val params = LinearLayout.LayoutParams(size, size)
            params.setMargins(margin, 0, margin, 0)
            indicator.layoutParams = params
            indicator.setBackgroundResource(R.drawable.indicator_bg_inactive)
            indicatorLayout2023.addView(indicator)
            indicatorViews2023.add(indicator)
        }
        if (indicatorViews2023.isNotEmpty()) {
            indicatorViews2023[0].setBackgroundResource(R.drawable.indicator_bg_active)
        }
    }

    private fun updateIndicators2023(currentPosition: Int) {
        for (i in indicatorViews2023.indices) {
            indicatorViews2023[i].setBackgroundResource(
                if (i == currentPosition) R.drawable.indicator_bg_active else R.drawable.indicator_bg_inactive
            )
        }
    }

    private fun handleJsonException(e: JSONException) {
        e.printStackTrace()
        Toast.makeText(
            requireContext(),
            "Error parsing JSON: ${e.message}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun handleRequestError(error: VolleyError) {
        error.printStackTrace()
        Toast.makeText(
            requireContext(),
            "Network error: ${error.message}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        requestQueue.cancelAll { true }
        requestQueue.stop()
    }

    override fun onItemClicked(position: Int) {
        sharedPreferences = requireContext().getSharedPreferences("sid", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("sid",dataList2[position].sid)
        editor.apply()
        Toast.makeText(requireContext(), dataList2[position].sid, Toast.LENGTH_SHORT).show()
        val newfragment = womensFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, newfragment)
        transaction.addToBackStack(null)
        transaction.commit()
        true
    }

    override fun onItemClicked2(position: Int) {
        sharedPreferences = requireContext().getSharedPreferences("pid", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("pid",dataList3[position].pid)
        editor.apply()
        Toast.makeText(requireContext(), dataList3[position].pid, Toast.LENGTH_SHORT).show()
        val newfragment = mensfragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, newfragment)
        transaction.addToBackStack(null)
        transaction.commit()
        true
    }
}
