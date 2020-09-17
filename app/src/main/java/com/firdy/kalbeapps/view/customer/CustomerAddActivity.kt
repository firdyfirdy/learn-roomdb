package com.firdy.kalbeapps.view.customer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firdy.kalbeapps.R
import com.firdy.kalbeapps.databinding.ActivityCustomerAddBinding
import com.firdy.kalbeapps.model.data.Customer
import com.firdy.kalbeapps.model.db.AppDatabase
import com.firdy.kalbeapps.model.repository.CustomerRepository
import java.util.*

class CustomerAddActivity : AppCompatActivity() {

    val edit_customer = "edit_customer"
    private lateinit var binding: ActivityCustomerAddBinding
    private lateinit var repository: CustomerRepository
    private lateinit var data: Customer
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_add)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_add)

        val name = binding.txtCustName
        val address = binding.txtCustAddress

        val dao = AppDatabase.getInstance(application).customerDao()
        repository = CustomerRepository(dao)

        if (intent.getParcelableExtra<Customer>(edit_customer) != null) {
            isUpdate = true
            data = intent.getParcelableExtra(edit_customer)!!
            binding.btnCustSave.text = "UPDATE"

            name.setText(data.txtCustomerName)
            address.setText(data.txtCustomerAddress)
            if (data.bitGender) {
                binding.rdCustomerPria.isChecked = true
            } else {
                binding.rdCustomerWanita.isChecked = true
            }
        }

        binding.btnCustSave.setOnClickListener {
            val radioGroup = binding.radioGrupCustomer.checkedRadioButtonId
            when {
                name.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
                address.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Address tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
                radioGroup == -1 -> {
                    Toast.makeText(this, "Silahkan pilih gender!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    when {

                        /* Update */
                        isUpdate -> {
                            repository.update(
                                Customer(
                                    data.intCustomerID,
                                    name.text.toString(),
                                    address.text.toString(),
                                    binding.rdCustomerPria.isChecked,
                                    data.dtInserted
                                )
                            )
                        }
                        else -> {

                            val customer = Customer(
                                txtCustomerName = name.text.toString(),
                                txtCustomerAddress = address.text.toString(),
                                bitGender = binding.rdCustomerPria.isChecked,
                                dtInserted = Date()
                            )

                            /* Save */
                            repository.insert(customer)
                        }
                    }
//                    Toast.makeText(this, "Add Data Success!", Toast.LENGTH_SHORT).show()

                    finish()
                }
            }
        }
    }
}