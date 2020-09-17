package com.firdy.kalbeapps.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.view.brand.BrandActivity
import com.firdy.kalbeapps.view.customer.CustomerActivity
import com.firdy.kalbeapps.view.pembelian.PembelianActivity
import com.firdy.kalbeapps.view.product.ProductActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnProduct = findViewById<Button>(R.id.btnProduct)
        btnProduct.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }

        val btnCustomer = findViewById<Button>(R.id.btnCustomer)
        btnCustomer.setOnClickListener {
            val intent = Intent(this, CustomerActivity::class.java)
            startActivity(intent)
        }

        val btnPembelian = findViewById<Button>(R.id.btnPembelian)
        btnPembelian.setOnClickListener {
            val intent = Intent(this, PembelianActivity::class.java)
            startActivity(intent)
        }

        val btnBrand = findViewById<Button>(R.id.btnBrand)
        btnBrand.setOnClickListener {
            val intent = Intent(this, BrandActivity::class.java)
            startActivity(intent)
        }
    }
}