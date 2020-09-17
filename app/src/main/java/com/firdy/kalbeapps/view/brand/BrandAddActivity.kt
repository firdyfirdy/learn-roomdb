package com.firdy.kalbeapps.view.brand

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.databinding.ActivityBrandAddBinding
import com.firdy.kalbeapps.model.data.Brand
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.BrandRepository
import java.util.*

class BrandAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrandAddBinding
    private lateinit var repository: BrandRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_add)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_brand_add)
        val name = binding.txtBrandName.text

        val dao = AppDatabase.getInstance(application).brandDao()
        repository = BrandRepository(dao)

        binding.btnBrandSave.setOnClickListener {
            when {
                name.isNullOrEmpty() -> {
                    Toast.makeText(this, "Brand Name is empty", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val brand = Brand(txtBrandName = name.toString(), dtInserted = Date())
                    repository.insert(brand)
                    Toast.makeText(this, "Add Data Success!", Toast.LENGTH_SHORT).show()

                    finish()
                }
            }
        }
    }
}