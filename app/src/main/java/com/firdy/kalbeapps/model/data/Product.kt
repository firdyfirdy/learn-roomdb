package com.firdy.kalbeapps.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="intProductID")
    var intProductID: Int = 0,

    @ColumnInfo(name ="txtProductCode")
    var txtProductCode: String,

    @ColumnInfo(name ="txtProductName")
    var txtProductName: String,

    @ColumnInfo(name ="brand_intBrandID")
    var brand_intBrandID: Int,

    @ColumnInfo(name = "dtInserted")
    val dtInserted: Date
) : Parcelable