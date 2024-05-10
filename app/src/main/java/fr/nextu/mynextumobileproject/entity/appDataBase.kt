package fr.nextu.mynextumobileproject.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Planet::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun planetDao(): PlanetDao
}
