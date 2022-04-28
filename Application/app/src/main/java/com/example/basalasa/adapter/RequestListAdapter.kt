package com.example.basalasa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.basalasa.R
import com.example.basalasa.model.entity.Account

class RequestListAdapter(
    private val listRequest: ArrayList<Account>
) : RecyclerView.Adapter<RequestListAdapter.ViewHolder>() {
    var onAccButtonClick: ((Int) -> Unit)? = null
    var onDecButtonClick: ((Int) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val txtEmail: TextView = listItemView.findViewById(R.id.emailReq)
        val txtName: TextView = listItemView.findViewById(R.id.nameReq)

        val accBtn: ImageButton = listItemView.findViewById(R.id.btnAcc)
        val decBtn: ImageButton = listItemView.findViewById(R.id.btnDec)


        init {
            accBtn.setOnClickListener {
                onAccButtonClick?.invoke(adapterPosition)
            }

            decBtn.setOnClickListener {
                onDecButtonClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RequestListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.request_list_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return listRequest.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtEmail.text = listRequest[position].email
        holder.txtName.text = listRequest[position].fullName
    }
}