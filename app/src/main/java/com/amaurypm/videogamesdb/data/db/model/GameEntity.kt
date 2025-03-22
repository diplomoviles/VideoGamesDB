package com.amaurypm.videogamesdb.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amaurypm.videogamesdb.util.Constants

@Entity(tableName = Constants.DATABASE_GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "game_id")
    var title: String,
    @ColumnInfo(name = "game_genre")
    var genre: String,
    @ColumnInfo(name = "game_developer")
    var developer: String
)
