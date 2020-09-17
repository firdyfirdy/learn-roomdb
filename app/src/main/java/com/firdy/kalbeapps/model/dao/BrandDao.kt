package com.firdy.kalbeapps.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firdy.kalbeapps.model.data.Brand

@Dao
interface BrandDao {

    @Query("SELECT * FROM Brand")
    fun getAll() : List<Brand>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(data: Brand)

    @Query("SELECT * FROM Brand WHERE intBrandID = :id")
    fun getBrandById(id: Int) : Brand
}