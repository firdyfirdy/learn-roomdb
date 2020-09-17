package com.firdy.kalbeapps.model.repository

import com.firdy.kalbeapps.model.dao.PembelianDao
import com.firdy.kalbeapps.model.data.Pembelian

class PembelianRepository(private val dao: PembelianDao) {
    val allData: List<Pembelian> = dao.getAll()

    fun insert(data: Pembelian){
        dao.insert(data)
    }

    fun update(data: Pembelian){
        dao.update(data)
    }

    fun delete(data: Pembelian){
        dao.delete(data)
    }
}