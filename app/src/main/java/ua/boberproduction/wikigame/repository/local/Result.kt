package ua.boberproduction.wikigame.repository.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.boberproduction.wikigame.models.Result

@Entity(tableName = "results")
data class Result(@PrimaryKey(autoGenerate = true) val id: Long?,
                  @ColumnInfo(name = "level") val level: Int,
                  @ColumnInfo(name = "startPhrase") val startPhrase: String,
                  @ColumnInfo(name = "targetPhrase") val targetPhrase: String,
                  @ColumnInfo(name = "clicks") val clicks: Int,
                  @ColumnInfo(name = "seconds") val seconds: Int,
                  @ColumnInfo(name = "date") val date: Long) {

    constructor(result: Result) : this(null, result.level, result.startPhrase, result.targetPhrase, result.clicks, result.seconds, result.date)

    companion object {
        fun fromModel(resultModel: Result): ua.boberproduction.wikigame.repository.local.Result {
            return Result(resultModel)
        }
    }

    fun mapToModel(): Result {
        return Result(level, startPhrase, targetPhrase, clicks, seconds, date)
    }
}