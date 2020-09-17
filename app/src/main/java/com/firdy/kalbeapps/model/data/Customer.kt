package com.firdy.kalbeapps.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "Customer")
@Parcelize
data class Customer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="intCustomerID")
    var intCustomerID: Int = 0,

    @ColumnInfo(name ="txtCustomerName")
    var txtCustomerName: String,

    @ColumnInfo(name ="txtCustomerAddress")
    var txtCustomerAddress: String,

    @ColumnInfo(name ="bitGender")
    var bitGender: Boolean,

    @ColumnInfo(name = "dtInserted")
    val dtInserted: Date
) : Parcelable