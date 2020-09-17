package com.firdy.kalbeapps.view.pembelian

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.model.data.Pembelian
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.CustomerRepository
import com.firdy.kalbeapps.model.repository.PembelianRepository
import com.firdy.kalbeapps.model.repository.ProductRepository
import kotlinx.android.synthetic.main.activity_pembelian_add.*
import java.util.*

class PembelianAddActivity : AppCompatActivity() {

    val editPembelian = "edit"
    lateinit var productRepo: ProductRepository
    lateinit var customerRepo: CustomerRepository
    lateinit var pembelianRepo: PembelianRepository
    var data: Pembelian? = null
    var isUpdate = false
    var selectedProduct: Int = -1
    var selectedCustomer: Int = -1
    var qty: Double? = null
    var custName : String? = null
    var prodName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembelian_add)

        val productDao = AppDatabase.getInstance(application).productDao()
        productRepo = ProductRepository(productDao)

        val customerDao = AppDatabase.getInstance(application).customerDao()
        customerRepo = CustomerRepository(customerDao)

        val dao = AppDatabase.getInstance(application).pembelianDao()
        pembelianRepo = PembelianRepository(dao)

        if (intent.getParcelableExtra<Pembelian>(editPembelian) != null){
            data = intent.getParcelableExtra(editPembelian)!!
            if (data != null){
                isUpdate = true

                custName = customerRepo.getCustomer(data!!.intCustomerID).txtCustomerName
                prodName = productRepo.getProduct(data!!.intProductID).txtProductName

                txtPembelianQty.setText(data!!.intQty.toInt().toString())
                btnAddPembelian.text = "UPDATE"
                btnDeletePembelian.visibility = View.VISIBLE
            }
        }

        /* Ambil data Product dan munculkan di spinner */
        getProduct(prodName)

        /* Produk yang terpilih disimpan ke variable */
        selectedProductListener()

        /* Ambil data Customer dan munculkan di spinner */
        getCustomer(custName)
        /* Customer yang terpilih disimpan ke variable */
        selectedCustomerListener()

        btnAddPembelian.setOnClickListener {
            qty = txtPembelianQty.text.toString().toDoubleOrNull()
            when {
                selectedProduct == -1 -> {
                    Toast.makeText(this@PembelianAddActivity, "Silahkan Pilih Produk", Toast.LENGTH_SHORT).show()
                }
                selectedCustomer == -1 -> {
                    Toast.makeText(this@PembelianAddActivity, "Silahkan Pilih Customer", Toast.LENGTH_SHORT).show()
                }
                qty == null -> {
                    Toast.makeText(this@PembelianAddActivity, "Silahkan Isi Quantity", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    if (isUpdate){

                        /* Mengupdate data pembelian ke Database */
                        pembelianRepo.update(
                            Pembelian(
                                data?.intSalesOrderID!!,
                                selectedCustomer,
                                selectedProduct,
                                qty!!,
                                data?.dtSalesOrder!!
                            )
                        )
                    }else{

                        /* Menyimpan data pembelian ke Database */
                        pembelianRepo.insert(
                            Pembelian(
                                intCustomerID = selectedCustomer,
                                intProductID = selectedProduct,
                                intQty = qty!!,
                                dtSalesOrder = Date()
                            )
                        )
                    }

                    Toast.makeText(this@PembelianAddActivity, "Pembelian berhasil!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btnDeletePembelian.setOnClickListener {
            if (data == null){

                Toast.makeText(this@PembelianAddActivity, "Hapus data gagal!", Toast.LENGTH_SHORT).show()
            }else{
                pembelianRepo.delete(data!!)

                Toast.makeText(this@PembelianAddActivity, "Hapus data berhasil!", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    /* Mengambil data product di database, dan store ke spinner (Dropdown List) */
    private fun getProduct(name: String? = null){

        val data = productRepo.allData
        val prodName = data.map { it -> it.txtProductName }
        val spinnerAdapter = ArrayAdapter(this, R.layout.txt_list, prodName)
        listProduct.adapter = spinnerAdapter

        /* men-select spinner berdasarkan data Product */
        if (!name.isNullOrEmpty()){
            val spinnerPosition = spinnerAdapter.getPosition(name)
            listProduct.setSelection(spinnerPosition)
        }
    }

    /* Listener untuk product yg dipilih */
    private fun selectedProductListener(){
        listProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                selectedProduct = productRepo.allData[p2].intProductID
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedProduct = -1
            }
        }
    }

    /* Mengambil data Customer di database, dan store ke spinner (Dropdown List) */
    private fun getCustomer(name: String? = null){
        val data = customerRepo.allData
        val custName = data.map { it -> it.txtCustomerName }
        val spinnerAdapter = ArrayAdapter(this, R.layout.txt_list, custName)
        listCustomer.adapter = spinnerAdapter

        /* men-select spinner berdasarkan data Customer */
        if (!name.isNullOrEmpty()){
            val spinnerPosition = spinnerAdapter.getPosition(name)
            listCustomer.setSelection(spinnerPosition)
        }
    }

    /* Listener untuk Customer yg dipilih */
    private fun selectedCustomerListener(){
        listCustomer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedCustomer = customerRepo.allData[p2].intCustomerID
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedCustomer = -1
            }
        }
    }
}