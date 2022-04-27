package com.example.basalasa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.SellerOrderBook
import com.squareup.picasso.Picasso


class SellerOrderDetailsAdapter(private val arrCartBook: ArrayList<SellerOrderBook>): RecyclerView.Adapter<SellerOrderDetailsAdapter.ViewHolder>() {

    lateinit var context: Context

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var bookImage: ImageView = itemView.findViewById(R.id.customerOrderImage)
        var bookName: TextView = itemView.findViewById(R.id.customerOrderName)
        var price: TextView = itemView.findViewById(R.id.customerOrderItemPrice)
        var quantity: TextView = itemView.findViewById(R.id.customerOrderQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellerOrderDetailsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_sheet_order_detail_recyclerview_item,parent,false)
        context=parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = arrCartBook[position]
        holder.bookName.text=book.name
        holder.quantity.text=book.quantity.toString()
        holder.price.text=book.price.toString()
        Picasso.get().load(book.picture).into(holder.bookImage)
    }

    override fun getItemCount(): Int {
        return arrCartBook.size
    }

}