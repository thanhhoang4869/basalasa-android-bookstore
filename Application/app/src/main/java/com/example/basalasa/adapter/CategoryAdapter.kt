package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Book
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class CategoryAdapter(private val arrBook: ArrayList<Book>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.fragment_category_recyclerview_item,parent,false)
        return ViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        Picasso.get().load(arrBook[position].picture).into(holder.itemImg)
        holder.itemTitle.text=arrBook[position].name
        if(arrBook[position].star.toString()=="0"){
            holder.itemReview.text="No reviews"
            holder.itemRate.isInvisible=true
        }else{
            holder.itemRate.text=arrBook[position].star.toString()
            holder.itemReview.text=arrBook[position].comments?.size.toString()+ " Reviews"
        }
        val formatter: NumberFormat = DecimalFormat("#,###")
        if(arrBook[position].saleprice.toString()=="0"){
            holder.itemPrice.text=formatter.format(arrBook[position].price)
        }else{
            holder.itemPrice.text = formatter.format(arrBook[position].saleprice)
        }
    }

    override fun getItemCount(): Int {
        return arrBook.size
    }

    inner class ViewHolder(itemView: View,listener:onItemClickListener): RecyclerView.ViewHolder(itemView){
        var itemImg: ImageView = itemView.findViewById(R.id.iv_category_rv_img)
        var itemTitle: TextView = itemView.findViewById(R.id.tv_category_rv_title)
        var itemPrice: TextView = itemView.findViewById(R.id.tv_category_rv_price)
        var itemRate: TextView = itemView.findViewById(R.id.tv_category_rv_rate)
        var itemReview: TextView = itemView.findViewById(R.id.review)

        init{
            itemView.setOnClickListener {
                listener.onItemClick((adapterPosition))
            }
        }
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }
}