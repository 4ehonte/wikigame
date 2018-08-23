package ua.boberproduction.wikigame.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Result::class), version = 1)
abstract class GameDataBase : RoomDatabase() {

    abstract fun resultsDao(): ResultsDao
}