package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.SellerPendingOrder
import com.squareup.picasso.Picasso

class SellerProcessingOrderAdapter(private val orders:ArrayList<SellerPendingOrder>):RecyclerView.Adapter<SellerProcessingOrderAdapter.ViewHolder>() {
    var onItemClick: ((SellerPendingOrder) -> Unit)? = null
    var onDone:((SellerPendingOrder)->Unit)?=null
    var onDeliver: ((SellerPendingOrder) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellerProcessingOrderAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.fragment_seller_order_processing_rv_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SellerProcessingOrderAdapter.ViewHolder, position: Int) {
        val order = orders[position]

        Picasso.get().load(order.product[0].picture).into(holder.image)
        holder.date.text = order.date
        holder.name.text = order.product[0].name
        holder.quantity.text = "x" + order.product[0].quantity.toString()
        holder.total.text = order.total.toString()
        holder.receiver.text=order.receiver
        if(order.status=="Preparing"){
            holder.done.visibility=View.GONE
        }else{
            holder.deliver.visibility=View.GONE
        }
    }

    override fun getItemCount(): Int {
       return orders.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.customerOrderImage)
        var date: TextView = itemView.findViewById(R.id.customerOrderDate)
        var name: TextView = itemView.findViewById(R.id.customerOrderName)
        var quantity: TextView = itemView.findViewById(R.id.customerOrderQuantity)
        var total: TextView = itemView.findViewById(R.id.customerOrderTotalMoney)
        var deliver:TextView=itemView.findViewById(R.id.btnDeliver)
        var done:TextView=itemView.findViewById(R.id.btnDone)
        var receiver:TextView=itemView.findViewById(R.id.tvReceiver)

        init{
            itemView.setOnClickListener {
                onItemClick?.invoke(orders[adapterPosition])
            }

            deliver.setOnClickListener {
                onDeliver?.invoke(orders[adapterPosition])
            }

            done.setOnClickListener {
                onDone?.invoke(orders[adapterPosition])
            }
        }
    }
}