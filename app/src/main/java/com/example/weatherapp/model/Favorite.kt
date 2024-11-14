package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_tbl")
data class Favorite(
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String

)
