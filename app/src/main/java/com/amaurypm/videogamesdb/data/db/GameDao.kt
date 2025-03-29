package com.amaurypm.videogamesdb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.util.Constants

@Dao
interface GameDao {

    //Create
    @Insert
    suspend fun insertGame(game: GameEntity)

    @Insert
    suspend fun insertGame(games: MutableList<GameEntity>)

    //Read
    @Query("SELECT * FROM ${Constants.DATABASE_GAME_TABLE}")
    suspend fun getAllGames(): MutableList<GameEntity>

    @Query("SELECT * FROM ${Constants.DATABASE_GAME_TABLE} WHERE game_id = :gameId")
    suspend fun getGameById(gameId: Long): GameEntity?

    //Update
    @Update
    suspend fun updateGame(game: GameEntity)

    @Update
    suspend fun updateGame(games: MutableList<GameEntity>)

    //Delete
    @Delete
    suspend fun deleteGame(game: GameEntity)
}