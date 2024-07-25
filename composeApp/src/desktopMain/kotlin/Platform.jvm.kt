import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.prefs.Preferences


actual suspend fun readTextFromUrl(url: String): String? = withContext(Dispatchers.IO) {
    BufferedReader(
        InputStreamReader(
            URL(url).openStream()
        )
    ).let { reader ->
        val sb = StringBuilder()
        var cp: Int
        while (reader.read().also { cp = it } != -1) {
            sb.append(cp.toChar())
        }
        sb.toString()
    }
}

actual suspend fun saveStringOnLocal(key: String, value: String) {
    DesktopPreferences
        .getInstance()
        .storeStringData(key, value)
}

actual suspend fun getStringFromLocal(key: String): String? {
    return DesktopPreferences
        .getInstance()
        .getStringData(key)
}

private class DesktopPreferences {
    private val preferences: Preferences = Preferences.userRoot().node(this::class.java.name)
    fun storeStringData(key: String, value: String) = preferences.put(key, value)
    fun getStringData(key: String): String? = preferences.get(key, null)

    companion object {
        private var instance: DesktopPreferences? = null
        fun getInstance(): DesktopPreferences {
            if(instance == null)
                instance = DesktopPreferences()
            return instance!!
        }
    }
}