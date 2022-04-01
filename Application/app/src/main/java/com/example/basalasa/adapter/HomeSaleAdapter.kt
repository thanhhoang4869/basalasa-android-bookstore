package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Category

//class HomeSaleAdapter(private val arrBookOnSale: ArrayList<Category>): RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {
////    var onItemClick:((Category) -> Unit)? = null
////
////    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
////        var homeImage: ImageView
////        var homeTopic: TextView
////
////        init{
////            homeImage = itemView.findViewById(R.id.homeCategoryImage)
////            homeTopic = itemView.findViewById(R.id.homeCategoryTopic)
////
////            itemView.setOnClickListener {
////                onItemClick?.invoke(arrCategory[adapterPosition])
////            }
////        }
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryAdapter.ViewHolder {
////        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_catergory_list_items,parent,false)
////        return ViewHolder(v)
////    }
////
////    override fun onBindViewHolder(holder: HomeCategoryAdapter.ViewHolder, position: Int) {
////        var category = arrCategory.get(position)
////
//////        holder.homeImage.setImage(category.image)
////        holder.homeTopic.text = category.name
////    }
////
////    override fun getItemCount(): Int {
////        return arrCategory.size
////    }
//}