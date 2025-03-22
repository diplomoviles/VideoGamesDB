package com.amaurypm.videogamesdb.ui

import androidx.recyclerview.widget.RecyclerView
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.GameElementBinding

class GameViewHolder(
    private val binding: GameElementBinding
): RecyclerView.ViewHolder(binding.root)
{
    //Programamos nuestro ViewHolder

    fun bind(game: GameEntity){

        binding.apply {
            tvTitle.text = game.title
            tvGenre.text = game.genre
            tvDeveloper.text = game.developer
        }

    }
}