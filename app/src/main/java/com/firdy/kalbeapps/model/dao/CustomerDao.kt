package com.firdy.kalbeapps.model.dao

import androidx.room.*
import com.firdy.kalbeapps.model.data.Customer

@Dao
interface CustomerDao {

    @Query("SELECT * FROM Customer")
    fun getAll() : List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(data: Customer)

    @Update
    fun update(data: Customer)

    @Query("SELECT * FROM Customer WHERE intCustomerID = :id")
    fun getCustomerById(id: Int): Customer
}