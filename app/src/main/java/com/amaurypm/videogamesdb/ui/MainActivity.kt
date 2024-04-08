package com.amaurypm.videogamesdb.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogamesdb.R
import com.amaurypm.videogamesdb.application.VideoGamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var games: List<GameEntity> = emptyList()
    private lateinit var repository: GameRepository

    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as VideoGamesDBApp).repository
        //val url = Constants.BASE_URL
        //Log.d(Constants.LOGTAG, "Hola")

        gameAdapter = GameAdapter() { selectedGame ->
            //Click para actualizar o borrar un elemento

            val dialog = GameDialog(
                newGame = false,
                game = selectedGame,
                updateUI = {
                    updateUI()
                }, message = { action ->
                    message(action)
                })

            dialog.show(supportFragmentManager, "updateDialog")

        }

        binding.rvGames.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }


        updateUI()

    }

    private fun updateUI() {
        lifecycleScope.launch {
            games = repository.getAllGames()

            binding.tvSinRegistros.visibility =
                if (games.isEmpty()) View.VISIBLE else View.INVISIBLE

            gameAdapter.updateList(games)

        }
    }

    //Click del floating action button para añadir un registro
    fun click(view: View) {
        //Manejo el click del FAB
        val dialog = GameDialog(
            updateUI = {
                updateUI()
            }, message = { action ->
                message(action)
            })
        dialog.show(supportFragmentManager, "insertDialog")
    }

    private fun message(text: String) {
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        Snackbar.make(binding.cl, text, Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(Color.parseColor("#9E1734"))
            .show()

    }

}