package ua.boberproduction.wikigame.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ResultsDao {

    @Query("SELECT * FROM results")
    fun getAll(): List<Result>

    @Insert(onConflict = REPLACE)
    fun insert(result: Result)

    @Query("DELETE from results")
    fun deleteAll()
}