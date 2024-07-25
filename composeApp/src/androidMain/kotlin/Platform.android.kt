import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

const val SHARED_PREFS_KEY = "myappcmp_prefs"

private var applicationContext: android.content.Context? = null
val androidContext get() = applicationContext
    ?: error("Android context has not been set. Please call setContext in your Application's onCreate.")

fun setContext(context: android.content.Context) {
    applicationContext = context.applicationContext
}

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
    val prefs: SharedPreferences = androidContext.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE)
    prefs.edit().apply {
        putString(key,value)
        apply()
    }
}
actual suspend fun getStringFromLocal(key: String): String? {
    val prefs: SharedPreferences = androidContext.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE)
    return prefs.getString(key, null)
}