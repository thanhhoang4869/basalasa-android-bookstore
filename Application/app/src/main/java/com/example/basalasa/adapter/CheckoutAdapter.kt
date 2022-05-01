package com.example.basalasa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.BooksInCart
import com.squareup.picasso.Picasso

class CheckoutAdapter(private val arrCartBook: HashMap<String,BooksInCart>): RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {
    lateinit var context: Context
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var bookName: TextView = itemView.findViewById(R.id.checkoutItemName)
        var Price: TextView = itemView.findViewById(R.id.price)
        var Quantity: TextView = itemView.findViewById(R.id.quantity)
        var picture:ImageView = itemView.findViewById(R.id.picture)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_checkout_recyclerview_item,parent,false)
        context=parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        val keyByIndex = arrCartBook.keys.elementAt(position) // Get key by index.
        val valueOfElement = arrCartBook.getValue(keyByIndex)
        holder.bookName.text = valueOfElement.name
        holder.Price.text = valueOfElement.price.toString()
        Picasso.get().load(valueOfElement.img).into(holder.picture)
        holder.Quantity.text = valueOfElement.quantity.toString()
    }

    override fun getItemCount(): Int {
        return arrCartBook.size
    }


}