package com.doremifa.munhwajoa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    abstract fun EventDao(): EventDao

    companion object {
        private var db: EventDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): EventDatabase {

            if (db == null) {

                synchronized(EventDatabase::class) {

                    db = Room.databaseBuilder(
                        context.applicationContext,
                        EventDatabase::class.java,
                        "event_database"
                    ).build()

                }
            }

            return db!!
        }

    }

}