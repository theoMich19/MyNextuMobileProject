package fr.nextu.mynextumobileproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import fr.nextu.mynextumobileproject.databinding.FragmentPlanetsBinding
import fr.nextu.mynextumobileproject.entity.AppDatabase
import fr.nextu.mynextumobileproject.entity.PlanetResponse
import fr.nextu.mynextumobileproject.entity.Planets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
class PlanetsFragment : Fragment() {
    private var _binding: FragmentPlanetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var planetAdapter: PlanetAdapter // Déclare l'adaptateur comme variable de classe

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.comeBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        planetAdapter = PlanetAdapter(Planets(emptyList()))
        binding.listPlanets.layoutManager = LinearLayoutManager(context)
        binding.listPlanets.adapter = planetAdapter

        context?.let {
            db = Room.databaseBuilder(it, AppDatabase::class.java, "planets_database.db").build()
            Log.d("tag2", db.planetDao().getAllPlanets().toString())
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        onUpdateViewFromDB()
        requestPlanetList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestPlanetList() = CoroutineScope(Dispatchers.IO).launch {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://swapi.py4e.com/api/planets")
            .get()
            .build()

        val response: Response = client.newCall(request).execute() // request web
        val data = response.body?.string() ?: ""

        val gson = Gson()
        val planetResponse = gson.fromJson(data, PlanetResponse::class.java)
        Log.d("API Data", "Data fetched: ${planetResponse.results.size} planets")

        if (planetResponse.results != null) {
            db.runInTransaction {
                db.planetDao().insertAll(planetResponse.results)
            }
        }

    }

    fun onUpdateViewFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            val flow = db.planetDao().getFlowData()
            flow.collect { planets ->
                Log.d("Database Data", "Data from DB: ${planets.size} planets")
                CoroutineScope(Dispatchers.Main).launch {
                    planetAdapter.updatePlanets(planets)
                }
            }
        }
    }
}