package fr.nextu.mynextumobileproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.nextu.mynextumobileproject.entity.Planet
import fr.nextu.mynextumobileproject.entity.Planets


class PlanetAdapter(val planets: Planets) : RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {

    override fun getItemCount() = planets.planets.size

    inner class PlanetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val climateTextView: TextView
        val terrainTextView: TextView
        val populationTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.name)
            climateTextView = view.findViewById(R.id.climate)
            terrainTextView = view.findViewById(R.id.terrain)
            populationTextView = view.findViewById(R.id.population)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.planet_item, parent, false)
        return PlanetViewHolder(view)
    }


    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val planet: Planet = planets.planets[position]
        val nameTextView: TextView = holder.itemView.findViewById(R.id.name)
        val climateTextView: TextView = holder.itemView.findViewById(R.id.climate)
        val terrainTextView: TextView = holder.itemView.findViewById(R.id.terrain)
        val populationTextView: TextView = holder.itemView.findViewById(R.id.population)

        nameTextView.text = buildString {
            append("Planet : ")
            append(planet.name)
        }
        climateTextView.text = buildString {
            append("Climat de la planet : ")
            append(planet.climate)
        }
        terrainTextView.text = planet.terrain
        populationTextView.text = buildString {
            append(planet.population)
            append(" hab")
        }

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, PlanetDetailActivity::class.java).apply {
                putExtra("planet_url", planet.url)
            }
            context.startActivity(intent)
        }
    }

    fun updatePlanets(newPlanets: List<Planet>) {
        this.planets.planets = newPlanets
        notifyDataSetChanged()
    }
}
