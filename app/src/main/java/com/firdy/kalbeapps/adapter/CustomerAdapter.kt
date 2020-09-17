package com.firdy.kalbeapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.model.data.Customer
import java.text.SimpleDateFormat

class CustomerAdapter(
    private val data: ArrayList<Customer>,
    private val listener: Listener
) :RecyclerView.Adapter<CustomerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val customer = data[position]
        holder.txtName.text = customer.txtCustomerName
        holder.txtAddress.text = customer.txtCustomerAddress
        holder.txtGender.text =  if (customer.bitGender) "Laki-Laki" else "Perempuan"
        val formatedDate: String = SimpleDateFormat("dd-MM-yyyy").format(customer.dtInserted)
        holder.txtFooter1.text = formatedDate
        holder.itemView.setOnClickListener {
            listener.onItemClicked(customer)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var txtName: TextView = itemView.findViewById(R.id.text_view_title)
        var txtAddress: TextView = itemView.findViewById(R.id.text_view_body)
        var txtGender: TextView = itemView.findViewById(R.id.text_view_body1)
        var txtFooter1: TextView = itemView.findViewById(R.id.text_view_footer1)
    }

    interface Listener{
        fun onItemClicked(data: Customer)
    }
}