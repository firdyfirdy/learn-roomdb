package com.firdy.kalbeapps.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Brand")
data class Brand(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="intBrandID")
    val intBrandID: Int = 0,

    @ColumnInfo(name ="txtBrandName")
    val txtBrandName: String,

    @ColumnInfo(name = "dtInserted")
    val dtInserted: Date
)