package com.example.basalasa.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Account

class AccountListAdapter(
    private val listAccount: ArrayList<Account>
) : RecyclerView.Adapter<AccountListAdapter.ViewHolder>() {
    var onItemClick: ((Int) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val txtEmail = listItemView.findViewById<TextView>(R.id.email)
        val txtRole = listItemView.findViewById<TextView>(R.id.role)
        val txtStatus = listItemView.findViewById<TextView>(R.id.status)
        val banBtn = listItemView.findViewById<TextView>(R.id.banBtn)

        init {
            listItemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AccountListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.account_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listAccount.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var role = ""
        var status = ""

        when (listAccount[position].role) {
            0 -> role = "Customer"
            1 -> role = "Seller"
            2 -> role = "Admin"
        }

        when (listAccount[position].status) {
            0 -> {
                status = "Banned"
                holder.txtStatus.setTextColor(Color.parseColor("#D86F6F"))
                holder.banBtn.setBackgroundColor(Color.parseColor("#0ACF83"))
                holder.banBtn.text="Unban"
            }
            1 -> {
                status = "Active"
                holder.txtStatus.setTextColor(Color.parseColor("#0ACF83"))
                holder.banBtn.setBackgroundColor(Color.parseColor("#D86F6F"))
                holder.banBtn.text="Ban"
            }
        }

        holder.txtEmail.text = listAccount[position].email
        holder.txtRole.text = role
        holder.txtStatus.text = status
    }
}