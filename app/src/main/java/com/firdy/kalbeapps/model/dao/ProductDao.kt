package com.firdy.kalbeapps.model.dao

import androidx.room.*
import com.firdy.kalbeapps.model.data.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAll() : List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(data: Product)

    @Update
    fun update(data: Product)

    @Query("SELECT * FROM Product WHERE intProductID = :id")
    fun getProductById(id: Int) : Product
}