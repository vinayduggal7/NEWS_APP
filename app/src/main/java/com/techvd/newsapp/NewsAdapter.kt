package com.techvd.newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NewsAdapter(private var list: List<Data>) : BaseAdapter() {
    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val context = parent?.context
        val inflater: LayoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var rowView = convertView

        if (rowView == null)
            rowView = inflater.inflate(R.layout.listitem,parent,false
            )
        val item= list[position]
        val title = rowView?.findViewById<TextView>(R.id.list_text)
        title?.text = item.webTitle

        title?.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.webUrl))
            context.startActivity(intent)
        }



        return rowView!!

    }
}