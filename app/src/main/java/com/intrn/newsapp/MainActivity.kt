package com.intrn.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), newsitemclick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = fetch_data()
        val adapter: NewsListAdapter = NewsListAdapter(items, this)
        recyclerView.adapter = adapter
    //    RecyclerView.LayoutManager= LinearLayoutManager(this)
    }
    private fun fetch_data(): ArrayList<String>{
        val list = ArrayList<String>()
        for(i in 0 until 50)
        {
            list.add("Item $i")
        }
        return list
    }

    override fun onitemclicked(item: String) {
        Toast.makeText(this,"The clicked item is $item",Toast.LENGTH_LONG).show()
    }
}