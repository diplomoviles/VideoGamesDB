package com.amaurypm.videogamesdb.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.util.Constants

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = true
)
abstract class GameDatabase: RoomDatabase() {
    //Aquí va el DAO
    abstract fun gameDao(): GameDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}