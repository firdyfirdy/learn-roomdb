package com.firdy.kalbeapps.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.firdy.kalbeapps.model.dao.BrandDao
import com.firdy.kalbeapps.model.dao.CustomerDao
import com.firdy.kalbeapps.model.dao.PembelianDao
import com.firdy.kalbeapps.model.dao.ProductDao
import com.firdy.kalbeapps.model.data.Brand
import com.firdy.kalbeapps.model.data.Customer
import com.firdy.kalbeapps.model.data.Pembelian
import com.firdy.kalbeapps.model.data.Product

@Database(entities = [Brand::class, Customer::class, Pembelian::class, Product::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun pembelianDao(): PembelianDao
    abstract fun customerDao(): CustomerDao
    abstract fun brandDao(): BrandDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kalbe_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}