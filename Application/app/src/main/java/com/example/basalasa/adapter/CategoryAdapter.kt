package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Book
import com.squareup.picasso.Picasso

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
        holder.itemPrice.text=arrBook[position].price.toString()
        holder.itemRate.text=arrBook[position].star.toString()
        holder.itemReview.text=arrBook[position].comments?.size.toString()+ " Reviews"
    }

    override fun getItemCount(): Int {
        return arrBook.size
    }

    inner class ViewHolder(itemView: View,listener:onItemClickListener): RecyclerView.ViewHolder(itemView){
        var itemImg: ImageView
        var itemTitle: TextView
        var itemPrice: TextView
        var itemRate: TextView
        var itemReview: TextView

        init{
            itemImg=itemView.findViewById(R.id.iv_category_rv_img)
            itemTitle=itemView.findViewById(R.id.tv_category_rv_title)
            itemPrice=itemView.findViewById(R.id.tv_category_rv_price)
            itemRate=itemView.findViewById(R.id.tv_category_rv_rate)
            itemReview=itemView.findViewById(R.id.review)

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