package ua.boberproduction.wikigame.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(val level: Int,
                  val startPhrase: String,
                  val targetPhrase: String,
                  val clicks: Int,
                  val seconds: Int,
                  val date: Long) : Parcelable