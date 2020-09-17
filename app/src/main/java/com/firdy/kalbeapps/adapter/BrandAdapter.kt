package com.firdy.kalbeapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.model.data.Brand
import java.text.SimpleDateFormat
import java.util.*

class BrandAdapter(
    private val listBrand: ArrayList<Brand>,
    private val listener: Listener
) : RecyclerView.Adapter<BrandAdapter.MyViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listBrand[position]
        holder.textViewTitle.text = data.txtBrandName
        holder.txtBody.visibility = View.GONE
        val formatedDate: String = SimpleDateFormat("dd-MM-yyyy").format(data.dtInserted)
        holder.txtFooter1.text = formatedDate
        holder.itemView.setOnClickListener {
            listener.onItemClick(data)
        }
    }

    override fun getItemCount(): Int {
        return listBrand.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        var txtFooter1: TextView = itemView.findViewById(R.id.text_view_footer1)
        var txtBody: TextView = itemView.findViewById(R.id.text_view_body)
    }

    interface Listener{
        fun onItemClick(brand: Brand)
    }
}