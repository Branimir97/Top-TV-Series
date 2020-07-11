package branimirbutkovic.ferit.toptvseries.sharedprefs

import android.content.Context
import branimirbutkovic.ferit.toptvseries.models.MyApplication

class PreferenceManager {

    companion object {
        const val PREFS_FILE = "SharedPrefs"
        const val PREFS_KEY = "branimir"
    }

    fun saveId(id: String) {
        val sharedPreferences = MyApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )

        val editor = sharedPreferences.edit()
        editor.putString(PREFS_KEY, id)
        editor.apply()
    }

    fun retrieveId(): String? {
        val sharedPreferences = MyApplication.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_KEY, "")
    }
}