package com.firdy.kalbeapps.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "Pembelian")
@Parcelize
data class Pembelian(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="intSalesOrderID")
    var intSalesOrderID: Int = 0,

    @ColumnInfo(name ="intCustomerID")
    var intCustomerID: Int,

    @ColumnInfo(name ="intProductID")
    var intProductID: Int,

    @ColumnInfo(name ="intQty")
    var intQty: Double,

    @ColumnInfo(name = "dtSalesOrder")
    val dtSalesOrder: Date
) : Parcelable