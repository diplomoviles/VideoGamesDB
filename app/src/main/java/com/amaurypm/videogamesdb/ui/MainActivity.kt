package com.amaurypm.videogamesdb.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesdb.R
import com.amaurypm.videogamesdb.application.VideoGamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.ActivityMainBinding
import com.amaurypm.videogamesdb.databinding.MyCustomLayoutBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Para el listado de juegos a leer en la bd
    private var games: MutableList<GameEntity> = mutableListOf()
    //Para el repositorio
    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        repository = (application as VideoGamesDBApp).repository



        updateUI()
    }

    private fun updateUI(){
        lifecycleScope.launch(Dispatchers.IO) {
            //Obtenemos todos los juegos
            games = repository.getAllGames()

            withContext(Dispatchers.Main) {
                binding.tvSinRegistros.visibility =
                    if (games.isNotEmpty()) View.INVISIBLE else View.VISIBLE
            }
        }
    }

    fun click(view: View){
        val game = GameEntity(
            title = "Mario Kart 8",
            genre = "Carreras",
            developer = "Nintendo"
        )

        lifecycleScope.launch(Dispatchers.IO) {
            repository.insertGame(game)
        }
    }
}