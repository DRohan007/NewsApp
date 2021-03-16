package com.intrn.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), newsitemclick {
    private lateinit var mAdaptor:  NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetch_data()
        mAdaptor = NewsListAdapter(this)
        recyclerView.adapter = mAdaptor
    //    RecyclerView.LayoutManager= LinearLayoutManager(this)
    }
    private fun fetch_data(){
        val api = "http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=450781e3f9c24954b15c2ed26e55c716"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, api, null,
            { response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for( i in 0 until newsJsonArray.length())
                {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("imageToUrl")
                    )
                    newsArray.add(news)
                }
                mAdaptor.updatedNews(newsArray)
            },
            { error ->
                // TODO: Handle error
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onitemclicked(item: News) {
        //Toast.makeText(this,"The clicked item is $item",Toast.LENGTH_LONG).show()
    }
}