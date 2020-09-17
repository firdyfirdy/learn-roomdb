package com.firdy.kalbeapps.view.customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.adapter.CustomerAdapter
import com.firdy.kalbeapps.model.data.Customer
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.CustomerRepository
import kotlinx.android.synthetic.main.activity_customer.*

class CustomerActivity : AppCompatActivity() {

    private lateinit var repository: CustomerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        /* Ambil data di database dan memasukannya ke recycler view */
        getData()

        btnCustomerAdd.setOnClickListener{
            startActivity(Intent (this, CustomerAddActivity::class.java))
        }
    }

    /* Ambil data di database dan memasukannya ke recycler view */
    private fun getData(){
        val dao = AppDatabase.getInstance(application).customerDao()
        repository = CustomerRepository(dao)

        val listItem = arrayListOf<Customer>()
        listItem.addAll(repository.allData)


        if (listItem.isNotEmpty()){
            txtCustomerEmpty.visibility = View.GONE

            /* Apply adapter Recycler View */
            setupRecyclerView(listItem)
        }
        else{
            txtCustomerEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(listData: ArrayList<Customer>){
        rcvCustomer.apply {
            adapter = CustomerAdapter(listData, object : CustomerAdapter.Listener {
                override fun onItemClicked(data: Customer) {
                    val intent = Intent(this@CustomerActivity, CustomerAddActivity::class.java)
                    intent.putExtra(CustomerAddActivity().edit_customer, data)
                    startActivity(intent)
//                    Toast.makeText(this@CustomerActivity, "Name: ${data.txtCustomerName}", Toast.LENGTH_SHORT).show()
                }
            })
            layoutManager = LinearLayoutManager(this@CustomerActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}