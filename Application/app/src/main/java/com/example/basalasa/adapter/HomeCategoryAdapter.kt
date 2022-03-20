package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R

data class item_book(val img:Int,val title:String,val price:String,val rate:String,val review:String)

class rvAdapter:RecyclerView.Adapter<rvAdapter.ViewHolder>() {
    var img= arrayListOf<Int>(R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background)
    var title= arrayListOf<String>("The silence of the lambs","Hannibal Lecter","123")
    var price= arrayListOf<String>("25","20","11")
    var rate= arrayListOf<String>("4.5","3.4","1.0")
    var review= arrayListOf<String>("123","432","324")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): rvAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.fragment_category_recyclerview_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: rvAdapter.ViewHolder, position: Int) {
        holder.itemImg.setImageResource(img[position])
        holder.itemTitle.text=title[position]
        holder.itemPrice.text=price[position]+"$"
        holder.itemRate.text=rate[position]
        holder.itemReview.text=review[position]+" Reviews"
    }

    override fun getItemCount(): Int {
        return img.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var itemImg:ImageView
        var itemTitle:TextView
        var itemPrice:TextView
        var itemRate:TextView
        var itemReview:TextView

        init{
            itemImg=itemView.findViewById(R.id.iv_category_rv_img)
            itemTitle=itemView.findViewById(R.id.tv_category_rv_title)
            itemPrice=itemView.findViewById(R.id.tv_category_rv_price)
            itemRate=itemView.findViewById(R.id.tv_category_rv_rate)
            itemReview=itemView.findViewById(R.id.tv_category_rv_review)
        }
    }
}

class HomeCategoryAdapter {
}