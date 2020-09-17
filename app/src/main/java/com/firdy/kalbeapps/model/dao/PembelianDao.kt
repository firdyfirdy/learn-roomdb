package com.firdy.kalbeapps.model.dao

import androidx.room.*
import com.firdy.kalbeapps.model.data.Pembelian

@Dao
interface PembelianDao {

    @Query("SELECT * FROM Pembelian")
    fun getAll() : List<Pembelian>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Pembelian)

    @Update
    fun update(data: Pembelian)

    @Delete
    fun delete(data: Pembelian)
}