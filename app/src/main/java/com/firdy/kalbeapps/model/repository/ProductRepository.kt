package com.firdy.kalbeapps.model.repository

import com.firdy.kalbeapps.model.dao.ProductDao
import com.firdy.kalbeapps.model.data.Product

class ProductRepository(private val productDao: ProductDao){

    val allData: List<Product> = productDao.getAll()

    fun insert(data: Product){
        productDao.insertOrUpdate(data)
    }

    fun update(data: Product){
        productDao.update(data)
    }

    fun getProduct(id: Int) : Product {
        return productDao.getProductById(id)
    }
}