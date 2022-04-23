package com.example.basalasa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.example.basalasa.R
import com.example.basalasa.model.body.UpdateCartBody
import com.example.basalasa.model.entity.BooksInCart
import com.example.basalasa.model.reponse.GetUpdateResponse
import com.example.basalasa.utils.Cache
import com.example.basalasa.utils.MyAPI
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAdapter(private val arrCartBook: ArrayList<BooksInCart>, private var hiddenView:TextView,private var TotalView:TextView): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var onItemClick:((BooksInCart, Int) -> Unit)? = null
    var onCheckClick:((BooksInCart,Int,CheckBox)->Unit)?=null
    var removeCheck:((BooksInCart,Int)->Unit)?=null
    lateinit var context: Context

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var bookImage: ImageView
        var bookName: TextView
        var Price: TextView
        var number_button:ElegantNumberButton
        var del_btn:ImageView
        var check_btn:CheckBox

        init {
            bookImage = itemView.findViewById(R.id.CartimageView)
            bookName = itemView.findViewById(R.id.bookName)
            Price = itemView.findViewById(R.id.bookPrice)
            number_button=itemView.findViewById(R.id.numberButton)
            del_btn=itemView.findViewById(R.id.delete)
            check_btn = itemView.findViewById(R.id.CartcheckBox)
            del_btn.setOnClickListener {
                onItemClick?.invoke(arrCartBook[adapterPosition],adapterPosition) }
            check_btn.setOnClickListener{
                if(check_btn.isChecked)
                    onCheckClick?.invoke(arrCartBook[adapterPosition],adapterPosition,check_btn)
                else
                    removeCheck?.invoke(arrCartBook[adapterPosition],adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_cart_recyclerview_item,parent,false)
        context=parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = arrCartBook[position]

        Picasso.get().load(book.img).into(holder.bookImage)
        holder.bookName.text = book.name
        holder.Price.text = (book.price*book.quantity).toString()
        holder.number_button.number = book.quantity.toString()

        holder.number_button.setOnValueChangeListener { _, oldValue, newValue ->
            arrCartBook[position].quantity = newValue
            holder.Price.text = (arrCartBook[position].price*arrCartBook[position].quantity).toString()
            if(holder.check_btn.isChecked) {
                hiddenView.text =
                    (Integer.parseInt(hiddenView.text.toString()) + (newValue - oldValue) * arrCartBook[position].price).toString()
                TotalView.text = hiddenView.text
            }
            updateData(arrCartBook[position].id,arrCartBook[position].quantity)
        }
    }


    private fun updateData(id:Int, quantity:Int){
        val token = Cache.getToken(context)
        val response = MyAPI.getAPI().updateCart(token.toString(),UpdateCartBody(id,quantity))
        response.enqueue(object : Callback<GetUpdateResponse> {
            override fun onResponse(call: Call<GetUpdateResponse>, response: Response<GetUpdateResponse>) {
                if (response.isSuccessful) {
                    println("SUCCESS")
                }
            }

            override fun onFailure(call: Call<GetUpdateResponse>, t: Throwable) {
                Toast.makeText(context, "Fail connection to server", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }
        })
    }

    override fun getItemCount(): Int {
        return arrCartBook.size
    }

}