package fr.nextu.mynextumobileproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class PlanetDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_planet_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val planetUrl = intent.extras?.getString("planet_url") ?: ""
        if (planetUrl.isNotEmpty()) {
            fetchPlanetDetails(planetUrl)
        }
    }


    private fun fetchPlanetDetails(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use {
                    if (it.isSuccessful) {
                        val jsonData = it.body?.string()
                        jsonData?.let { jsonString ->
                            val jsonObject = JSONObject(jsonString)
                            runOnUiThread {
                                updateUI(jsonObject)
                            }
                        }
                    }
                }
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun updateUI(planet: JSONObject) {
        findViewById<TextView>(R.id.tvName).text = "Name: " + planet.getString("name")
        findViewById<TextView>(R.id.tvRotationPeriod).text = "Rotation Period: " + planet.getString("rotation_period") + " hours"
        findViewById<TextView>(R.id.tvOrbitalPeriod).text = "Orbital Period: " + planet.getString("orbital_period") + " days"
        findViewById<TextView>(R.id.tvDiameter).text = "Diameter: " + planet.getString("diameter") + " km"
        findViewById<TextView>(R.id.tvClimat).text = "Climate: " + planet.getString("climate")
        findViewById<TextView>(R.id.tvGravity).text = "Gravity: " + planet.getString("gravity")
        findViewById<TextView>(R.id.tvTerrain).text = "Terrain: " + planet.getString("terrain")
        findViewById<TextView>(R.id.tvPopulation).text = "Population: " + planet.getString("population")
    }
}