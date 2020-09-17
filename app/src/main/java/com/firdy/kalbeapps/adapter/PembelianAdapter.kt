package com.firdy.kalbeapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.model.data.Pembelian
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.CustomerRepository
import com.firdy.kalbeapps.model.repository.ProductRepository
import java.text.SimpleDateFormat

class PembelianAdapter(
    private val listPembelian: ArrayList<Pembelian>,
    private val listener: PembelianListener
) : RecyclerView.Adapter<PembelianAdapter.MyViewHolder>() {

    private lateinit var customerRepo: CustomerRepository
    private lateinit var productRepo: ProductRepository
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pembelian = listPembelian[position]

        val customerDao = AppDatabase.getInstance(context).customerDao()
        customerRepo = CustomerRepository(customerDao)
        val dataCustomer = customerRepo.getCustomer(pembelian.intCustomerID)

        val productDao = AppDatabase.getInstance(context).productDao()
        productRepo = ProductRepository(productDao)
        val dataProduct = productRepo.getProduct(pembelian.intProductID)

        holder.textViewTitle.text = "Order ID: #${pembelian.intSalesOrderID}"
        holder.textViewBody.text = "Product: ${dataProduct.txtProductName}"
        holder.textViewBody1.text = "Qty: ${pembelian.intQty.toInt()}"
        holder.textViewFooter.text = "Customer: ${dataCustomer.txtCustomerName}"
        val formatedDate: String = SimpleDateFormat("dd-MM-yyyy").format(pembelian.dtSalesOrder)
        holder.txtFooter1.text = formatedDate
        holder.itemView.setOnClickListener {
            listener.onItemClicked(pembelian)
        }
    }

    override fun getItemCount(): Int {
        return listPembelian.size
    }
    interface PembelianListener{
        fun onItemClicked(data: Pembelian)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        var textViewBody: TextView = itemView.findViewById(R.id.text_view_body)
        var textViewBody1: TextView = itemView.findViewById(R.id.text_view_body1)
        var textViewFooter: TextView = itemView.findViewById(R.id.text_view_footer)
        var txtFooter1: TextView = itemView.findViewById(R.id.text_view_footer1)
    }
}