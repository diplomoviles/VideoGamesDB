package com.amaurypm.videogamesdb.data

import com.amaurypm.videogamesdb.data.db.GameDao
import com.amaurypm.videogamesdb.data.db.model.GameEntity

class GameRepository(
    private val gameDao: GameDao
) {

    suspend fun insertGame(game: GameEntity){
        gameDao.insertGame(game)
    }

    suspend fun getAllGames(): MutableList<GameEntity> =
        gameDao.getAllGames()

    suspend fun updateGame(game: GameEntity){
        gameDao.updateGame(game)
    }

    suspend fun deleteGame(game: GameEntity){
        gameDao.deleteGame(game)
    }

}