package com.firdy.kalbeapps.model.repository

import com.firdy.kalbeapps.model.dao.BrandDao
import com.firdy.kalbeapps.model.data.Brand

class BrandRepository(private val dao: BrandDao) {

    val allData: List<Brand> = dao.getAll()

    fun insert(data: Brand){
        dao.insertOrUpdate(data)
    }

    fun getBrand(id: Int) : Brand {
        return dao.getBrandById(id)
    }
}