package com.torneicalcio.app.utils
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object AuthManager {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_LOGGED = "admin_logged"
    private const val KEY_PASSWORD = "admin_password"
    private const val DEFAULT_PASSWORD = "torneo2025"

    fun getMasterKey(context: Context) = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    fun getPrefs(context: Context) = EncryptedSharedPreferences.create(context, PREF_NAME, getMasterKey(context), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    fun isLoggedIn(context: Context) = getPrefs(context).getBoolean(KEY_LOGGED, false)
    fun login(context: Context, password: String): Boolean {
        val prefs = getPrefs(context)
        return if (password == prefs.getString(KEY_PASSWORD, DEFAULT_PASSWORD)) { prefs.edit().putBoolean(KEY_LOGGED, true).apply(); true } else false
    }
    fun logout(context: Context) = getPrefs(context).edit().putBoolean(KEY_LOGGED, false).apply()
}