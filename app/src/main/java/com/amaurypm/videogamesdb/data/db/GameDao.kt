package com.amaurypm.videogamesdb.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.util.Constants

@Dao
interface GameDao {

    //Create
    @Insert
    suspend fun insertGame(game: GameEntity)

    @Insert
    suspend fun insertGame(games: List<GameEntity>)

    //Read
    @Query("SELECT * FROM ${Constants.DATABASE_GAME_TABLE}")
    suspend fun getAllGames(): List<GameEntity>

    //Update
    @Update
    suspend fun updateGame(game: GameEntity)

    //Delete
    @Delete
    suspend fun deleteGame(game: GameEntity)

}