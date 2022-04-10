package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.CustomerHistory
import com.squareup.picasso.Picasso

class CustomerOrderTabRCCancelAdapter (private val arrHistory: ArrayList<CustomerHistory>): RecyclerView.Adapter<CustomerOrderTabRCCancelAdapter.ViewHolder>() {
    var onItemClick: ((CustomerHistory) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var date: TextView
        var name: TextView
        var quantity: TextView
        var total: TextView
        val cancel: TextView

        init {
            image = itemView.findViewById(R.id.customerOrderCancelImage)
            date = itemView.findViewById(R.id.customerOrderCancelDate)
            name = itemView.findViewById(R.id.customerOrderCancelName)
            quantity = itemView.findViewById(R.id.customerOrderCancelQuantity)
            total = itemView.findViewById(R.id.customerOrderCancelTotalMoney)
            cancel = itemView.findViewById(R.id.customerOrderCancelBtn)

            itemView.setOnClickListener {
                onItemClick?.invoke(arrHistory[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerOrderTabRCCancelAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val customerOrderTabRCView =
            inflater.inflate(R.layout.fragment_customer_order_tab_rc_item_cancel, parent, false)
        return ViewHolder(customerOrderTabRCView)
    }

    override fun onBindViewHolder(holder: CustomerOrderTabRCCancelAdapter.ViewHolder, position: Int) {
        var order = arrHistory[position]

        Picasso.get().load(order.product?.get(0)?.picture).into(holder.image);
        holder.date.text = order.date
        holder.name.text = order.product?.get(0)?.name
        holder.quantity.text = "x" + order.product?.get(0)?.quantity.toString()
        holder.total.text = order.total.toString()
    }

    override fun getItemCount(): Int {
        return arrHistory.size
    }
}