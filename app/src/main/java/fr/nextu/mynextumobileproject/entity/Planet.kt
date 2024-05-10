package fr.nextu.mynextumobileproject.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "planets")
data class Planet(
    @PrimaryKey val name: String,
    val climate: String,
    val terrain: String,
    val population: String,
    val url: String
)

data class Planets(var planets: List<Planet>)

data class PlanetResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Planet>
)
