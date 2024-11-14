package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "settings_tbl")
data class Unit(
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "unit")
    val unit: String
)
