package com.intrn.newsapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), newsitemclick {
    private lateinit var mAdapter:  NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       swipetorefresh.setOnRefreshListener {
           fetchData()
       }
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }
    private fun fetchData() {
        swipetorefresh.isRefreshing = true
        val url = "http://newsapi.org/v2/top-headlines?country=in&apiKey=450781e3f9c24954b15c2ed26e55c716"
        val jsonObjectRequest = object :  JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    Log.d("temp","requesting method error")
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                swipetorefresh.isRefreshing = false
                mAdapter.updatedNews(newsArray)
            },
            {
                swipetorefresh.isRefreshing = false
                Toast.makeText(this,"Error Refreshing ",Toast.LENGTH_SHORT).show()
                Log.d("Error Api","Api Call failed ${it.localizedMessage}")
            }
        ) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }

        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onitemclicked(item: News) {
        //Toast.makeText(this,"The clicked item is $item",Toast.LENGTH_LONG).show()
    }
}