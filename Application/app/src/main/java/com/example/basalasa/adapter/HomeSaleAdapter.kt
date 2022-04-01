package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Book


class HomeSaleAdapter(private val arrBookOnSale: ArrayList<Book>): RecyclerView.Adapter<HomeSaleAdapter.ViewHolder>() {
    var onItemClick:((Book) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var homeSaleImage: ImageView
        var homeSaleName: TextView
        var homeSaleRootPrice: TextView
        var homeSaleSalePrice: TextView

        init{
            homeSaleImage = itemView.findViewById(R.id.homeSaleImage)
            homeSaleName = itemView.findViewById(R.id.homeSaleBookName)
            homeSaleRootPrice = itemView.findViewById(R.id.homeSaleRootPrice)
            homeSaleSalePrice = itemView.findViewById(R.id.homeSaleSalePrice)

            itemView.setOnClickListener {
                onItemClick?.invoke(arrBookOnSale[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSaleAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_sale_list_items,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeSaleAdapter.ViewHolder, position: Int) {
        var book = arrBookOnSale.get(position)

//        holder.homeSaleImage = book.picture
        holder.homeSaleName.text = book.name
        holder.homeSaleRootPrice.text = book.price.toString()
        holder.homeSaleSalePrice.text = book.saleprice.toString()
    }

    override fun getItemCount(): Int {
        return arrBookOnSale.size
    }
}