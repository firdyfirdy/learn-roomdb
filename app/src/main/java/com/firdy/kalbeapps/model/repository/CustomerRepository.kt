package com.firdy.kalbeapps.model.repository

import com.firdy.kalbeapps.model.dao.CustomerDao
import com.firdy.kalbeapps.model.data.Customer

class CustomerRepository(private val customerDao: CustomerDao){

    val allData: List<Customer> = customerDao.getAll()

    fun insert(data: Customer){
        customerDao.insertOrUpdate(data)
    }

    fun update(data: Customer){
        customerDao.update(data)
    }

    fun getCustomer(id: Int) : Customer {
        return customerDao.getCustomerById(id)
    }
}