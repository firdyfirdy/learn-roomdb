package com.firdy.kalbeapps.view.product

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.databinding.ActivityProductAddBinding
import com.firdy.kalbeapps.model.data.Product
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.BrandRepository
import com.firdy.kalbeapps.model.repository.ProductRepository
import kotlinx.android.synthetic.main.activity_product_add.*
import java.util.*

class ProductAddActivity : AppCompatActivity() {

    val edit_product = "edit_product"
    private var isUpdate = false
    private lateinit var binding: ActivityProductAddBinding
    private lateinit var repository: ProductRepository
    private lateinit var brandRepo: BrandRepository
    private lateinit var data: Product
    private var brandName: String? = null
    var selectedBrand: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add)
        val code = binding.txtProductCode
        val name = binding.txtProductName

        val dao = AppDatabase.getInstance(application).productDao()
        repository = ProductRepository(dao)

        val brandDao = AppDatabase.getInstance(application).brandDao()
        brandRepo = BrandRepository(brandDao)

        if (intent.getParcelableExtra<Product>(edit_product) != null){
            isUpdate = true
            data = intent.getParcelableExtra(edit_product)!!
            code.setText(data.txtProductCode)
            name.setText(data.txtProductName)
            brandName = brandRepo.getBrand(data.brand_intBrandID).txtBrandName
            binding.btnProductSave.text = "UPDATE"
        }

        /* Ambil data Brand dari database dan set listener */
        getBrand(brandName)

        binding.btnProductSave.setOnClickListener {
            when {
                selectedBrand == -1 ->{
                    Toast.makeText(this, "Silahkan Pilih Brand!", Toast.LENGTH_SHORT).show()
                }
                code.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Code is empty", Toast.LENGTH_SHORT).show()
                }
                name.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Name is empty", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    if (isUpdate){
                        repository.update(
                            Product(
                                data.intProductID,
                                code.text.toString(),
                                name.text.toString(),
                                selectedBrand,
                                data.dtInserted
                            )
                        )
                    }else{
                        repository.insert(Product(
                            txtProductName = name.text.toString(),
                            txtProductCode = code.text.toString(),
                            brand_intBrandID = selectedBrand,
                            dtInserted = Date()
                        ))
                    }

                    finish()
                }
            }
        }
    }

    private fun getBrand(name: String? = null) {
        val data = brandRepo.allData
        if (data.isNotEmpty()){
            val brandName = data.map { it -> it.txtBrandName }
            val spinnerAdapter = ArrayAdapter(this, R.layout.txt_list, brandName)
            binding.listBrand.adapter = spinnerAdapter

            /* Set selection untuk spinner */
            if (!name.isNullOrEmpty()){
                val spinnerPosition = spinnerAdapter.getPosition(name)
                binding.listBrand.setSelection(spinnerPosition)
            }

            /* Brand yg terpilih disimpan ke variable */
            selectedBrandListener()
        }
    }

    private fun selectedBrandListener() {
        listBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedBrand = brandRepo.allData[p2].intBrandID
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedBrand = -1
            }
        }
    }
}