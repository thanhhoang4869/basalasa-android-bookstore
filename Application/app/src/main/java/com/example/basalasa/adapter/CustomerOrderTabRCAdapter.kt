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
import java.text.DecimalFormat
import java.text.NumberFormat

class CustomerOrderTabRCAdapter (private val arrHistory: ArrayList<CustomerHistory>, private val pending: Boolean): RecyclerView.Adapter<CustomerOrderTabRCAdapter.ViewHolder>() {
    var onItemClick: ((CustomerHistory) -> Unit)? = null
    var onCancelClick: ((CustomerHistory) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.customerOrderImage)
        var date: TextView = itemView.findViewById(R.id.customerOrderDate)
        var name: TextView = itemView.findViewById(R.id.customerOrderName)
        var quantity: TextView = itemView.findViewById(R.id.customerOrderQuantity)
        var total: TextView = itemView.findViewById(R.id.customerOrderTotalMoney)
        var cancel: TextView = itemView.findViewById(R.id.customerOrderCancelBtn)

        init {

            itemView.setOnClickListener {
                onItemClick?.invoke(arrHistory[adapterPosition])
            }

            cancel.setOnClickListener {
                onCancelClick?.invoke(arrHistory[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerOrderTabRCAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val customerOrderTabRCView =
            inflater.inflate(R.layout.fragment_customer_order_tab_rc_item, parent, false)
        return ViewHolder(customerOrderTabRCView)
    }

    override fun onBindViewHolder(holder: CustomerOrderTabRCAdapter.ViewHolder, position: Int) {
        var order = arrHistory[position]

        val formatter: NumberFormat = DecimalFormat("#,###")
        
        Picasso.get().load(order.product[0].picture).into(holder.image);
        holder.date.text = order.date
        holder.name.text = order.product[0].name
        holder.quantity.text = "x" + order.product?.get(0)?.quantity.toString()
        holder.total.text = order.total.toString()

        if(!pending) {
            holder.cancel.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return arrHistory.size
    }
}