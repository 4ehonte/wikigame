package ua.boberproduction.wikigame.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ResultsDao {

    @Query("SELECT * FROM results")
    fun getAll(): Single<List<Result>>

    @Insert(onConflict = REPLACE)
    fun insert(result: Result)

    @Query("DELETE from results")
    fun deleteAll()

    @Query("SELECT sum(clicks) from results")
    fun getTotalClicks(): Single<Int>

    @Query("SELECT sum(seconds) from results")
    fun getTotalTime(): Single<Int>
}