package com.example.basalasa.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Book
import com.example.basalasa.model.entity.BooksInCart
import com.squareup.picasso.Picasso

class CartAdapter(private val arrCartBook: ArrayList<BooksInCart>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var onItemClick:((BooksInCart, Int) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var bookImage: ImageView
        var bookName: TextView
        var Price: TextView
        var quantity: TextView

        init {
            bookImage = itemView.findViewById(R.id.CartimageView)
            bookName = itemView.findViewById(R.id.bookName)
            Price = itemView.findViewById(R.id.bookPrice)
            quantity = itemView.findViewById(R.id.quantity)
            itemView.setOnClickListener {
                onItemClick?.invoke(arrCartBook[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_recyclerview_item,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var book = arrCartBook.get(position)

        Picasso.get().load(book.img).into(holder.bookImage);
        holder.bookName.text = book.name
        holder.Price.text = "$" + (book.price*book.quantity).toString()
        holder.quantity.text=book.quantity.toString()

    }

    override fun getItemCount(): Int {
        return arrCartBook.size;
    }
}