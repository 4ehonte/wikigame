package ua.boberproduction.wikigame.repository

interface PreferencesProvider {

    fun getToken(): String

    fun setToken(token: String)
}