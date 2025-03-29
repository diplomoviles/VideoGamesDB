package com.amaurypm.videogamesdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.GameElementBinding

class GameAdapter(
    private val onGameClick: (GameEntity) -> Unit
): RecyclerView.Adapter<GameViewHolder>() {

    private var games: List<GameEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.bind(game)

        holder.itemView.setOnClickListener {
            //Aquí va el click para cada elemento
            onGameClick(game)
        }
    }

    //Actualizamos el adapter para los nuevos elementos actualizados
    fun updateList(list: MutableList<GameEntity>){
        games = list
        notifyDataSetChanged()
    }
}