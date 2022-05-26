package com.doremifa.munhwajoa.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey
    val title: String,
    val codeName: String,
    val guName: String?,
    val date: String,
    val place: String,
    val target: String,
    val fee: String,
    val player: String?,
    val program: String?,
    val link: String,
    val image: String,
    val startDate: String,
    var favorite: Boolean = false
) {

    @JvmName("setFavorite1")
    fun setFavorite(setValue: Boolean) {
        this.favorite = setValue
    }
}
