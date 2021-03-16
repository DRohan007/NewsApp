package com.intrn.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter( private  val listner: newsitemclick): RecyclerView.Adapter<newsviewholder>() {
    private val items: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {
        //Use to convert the xml view (item_news.xml) to viewholder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewholder = newsviewholder(view)
        view.setOnClickListener{
            listner.onitemclicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {
        val curr_item = items[position]
        holder.titleview.text = curr_item.title
        if (curr_item.author == "null")
        {
            holder.author.text = "Anonymous"
        }
        else {
            holder.author.text = curr_item.author
        }
        Glide.with(holder.itemView.context).load(curr_item.imageurl).into(holder.news_image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updatedNews(updatedNews: ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }
}

class newsviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleview: TextView = itemView.findViewById(R.id.textview_itemnews)
    val author = itemView.author_id
    val news_image = itemView.image
}
interface newsitemclick{
    fun onitemclicked(item: News)
}