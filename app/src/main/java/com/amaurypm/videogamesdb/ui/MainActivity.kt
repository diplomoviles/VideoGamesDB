package com.amaurypm.videogamesdb.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogamesdb.R
import com.amaurypm.videogamesdb.application.VideoGamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Para el listado de juegos a leer en la bd
    private var games: MutableList<GameEntity> = mutableListOf()
    //Para el repositorio
    private lateinit var repository: GameRepository

    //Para el adapter del recycler view
    private lateinit var gameAdapter: GameAdapter

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

        //Instanciamos el GameAdapter
        gameAdapter = GameAdapter{ selectedGame ->
            //Click de un juego

            /*Toast.makeText(
                this,
                "Click en el juego ${selectedGame.title}",
                Toast.LENGTH_SHORT
            )
                .show()*/

            val dialog = GameDialog(newGame = false, game = selectedGame){
                updateUI()
            }

            dialog.show(supportFragmentManager, "dialog2")


        }

        binding.apply {
            rvGames.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGames.adapter = gameAdapter
        }

        updateUI()
    }

    private fun updateUI(){
        lifecycleScope.launch {
            //Obtenemos todos los juegos
            games = repository.getAllGames()


            binding.tvSinRegistros.visibility =
                if (games.isNotEmpty()) View.INVISIBLE else View.VISIBLE


            gameAdapter.updateList(games)
        }
    }

    fun click(view: View){
        /*val game = GameEntity(
            title = "Mario Kart 8",
            genre = "Carreras",
            developer = "Nintendo"
        )
        lifecycleScope.launch {
            repository.insertGame(game)
        }
        updateUI()*/

        //Mostramos el diálogo
        val dialog = GameDialog{
            //Aquí va el cuerpo de la lambda
            updateUI()
        }
        dialog.show(supportFragmentManager, "dialog1")
    }

    /*private fun updateUI(){
        lifecycleScope.launch {
            //Obtenemos todos los juegos
            games = repository.getAllGames()

            binding.tvSinRegistros.visibility =
                if (games.isNotEmpty()) View.INVISIBLE else View.VISIBLE

        }
    }*/

    /*fun click(view: View){
        val game = GameEntity(
            title = "Mario Kart 8",
            genre = "Carreras",
            developer = "Nintendo"
        )

        lifecycleScope.launch {
            repository.insertGame(game)
        }
    }*/
}