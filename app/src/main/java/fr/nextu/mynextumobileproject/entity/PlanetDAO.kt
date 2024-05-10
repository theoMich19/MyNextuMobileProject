package fr.nextu.mynextumobileproject.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {
    @Query("SELECT * FROM planets")
    fun getAllPlanets(): LiveData<List<Planet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(planets: List<Planet>)

    @Query("SELECT * FROM planets")
    fun getFlowData(): Flow<List<Planet>>
}
