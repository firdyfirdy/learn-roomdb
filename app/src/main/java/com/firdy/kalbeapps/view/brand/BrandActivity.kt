package com.firdy.kalbeapps.view.brand

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.adapter.BrandAdapter
import com.firdy.kalbeapps.model.data.Brand
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.BrandRepository
import kotlinx.android.synthetic.main.activity_brand.*
import java.util.*

class BrandActivity : AppCompatActivity() {

    private lateinit var repository: BrandRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)

        getData()

        btnBrandAdd.setOnClickListener {
            startActivity(Intent(this, BrandAddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        val dao = AppDatabase.getInstance(application).brandDao()
        repository = BrandRepository(dao)

        val listItem = arrayListOf<Brand>()
        listItem.addAll(repository.allData)

        if (listItem.isNotEmpty()){
            txtBrandEmpty.visibility = View.GONE

            /* Apply adapter Recycler View */
            setupRecyclerView(listItem)
        }
        else{
            txtBrandEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(listItem: ArrayList<Brand>) {
        rcvBrand.apply {
            adapter = BrandAdapter(listItem, object : BrandAdapter.Listener {
                override fun onItemClick(brand: Brand) {
                    Toast.makeText(this@BrandActivity, "Brand Name: ${brand.txtBrandName}", Toast.LENGTH_SHORT).show()
                }
            })
            layoutManager = LinearLayoutManager(this@BrandActivity)
        }
    }
}