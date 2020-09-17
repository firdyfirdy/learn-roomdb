package com.firdy.kalbeapps.view.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.adapter.ProductAdapter
import com.firdy.kalbeapps.model.data.Product
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.ProductRepository
import kotlinx.android.synthetic.main.activity_product.*
import java.util.*

class ProductActivity : AppCompatActivity() {

    private lateinit var repository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        getData()

        btnProductAdd.setOnClickListener{
            startActivity(Intent(this, ProductAddActivity::class.java))
        }
    }

    private fun getData(){
        val dao = AppDatabase.getInstance(application).productDao()
        repository = ProductRepository(dao)

        val listItem = arrayListOf<Product>()
        listItem.addAll(repository.allData)

        if (listItem.isNotEmpty()){
            txtProductEmpty.visibility = View.GONE

            /* Apply adapter Recycler View */
            setupRecyclerView(listItem)
        }
        else{
            txtProductEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(listItem: ArrayList<Product>) {
        rcvProduct.apply {
            adapter = ProductAdapter(listItem, object : ProductAdapter.Listener {
                override fun OnItemClicked(product: Product) {
                    val intent = Intent(this@ProductActivity, ProductAddActivity::class.java)
                    intent.putExtra(ProductAddActivity().edit_product, product)
                    startActivity(intent)
                }
            })
            layoutManager = LinearLayoutManager(this@ProductActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}