package com.firdy.kalbeapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.model.data.Product
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.BrandRepository
import java.text.SimpleDateFormat

class ProductAdapter(
    private val listProduct: ArrayList<Product>,
    private val listener: Listener
) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>(){

    private lateinit var brandRepository: BrandRepository
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = listProduct[position]

        val brandDao = AppDatabase.getInstance(context).brandDao()
        brandRepository = BrandRepository(brandDao)
        val dataBrand = brandRepository.getBrand(product.brand_intBrandID)

        holder.textViewTitle.text = product.txtProductName
        holder.textViewBody.text = "Code: ${product.txtProductCode}"
        holder.txtBrand.text = "Brand: ${dataBrand.txtBrandName}"
        val formatedDate: String = SimpleDateFormat("dd-MM-yyyy").format(product.dtInserted)
        holder.txtFooter1.text = formatedDate
        holder.itemView.setOnClickListener {
            listener.OnItemClicked(product)
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    interface Listener{
        fun OnItemClicked(product: Product)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var textViewTitle = itemView.findViewById<TextView>(R.id.text_view_title)
        var textViewBody = itemView.findViewById<TextView>(R.id.text_view_body)
        var txtBrand: TextView = itemView.findViewById(R.id.text_view_body1)
        var txtFooter1: TextView = itemView.findViewById(R.id.text_view_footer1)
    }
}