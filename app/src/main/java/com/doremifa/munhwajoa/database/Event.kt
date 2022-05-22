package com.doremifa.munhwajoa.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val codeName: String,
    val guName: String?,
    val title: String,
    val date: String,
    val place: String,
    val target: String,
    val fee: String,
    val player: String?,
    val program: String?,
    val link: String,
    val image: String,
    val startDate: String,
    val favorite: Boolean = false
)
