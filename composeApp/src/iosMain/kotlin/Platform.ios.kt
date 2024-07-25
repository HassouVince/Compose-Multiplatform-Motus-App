import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSUserDefaults
import platform.Foundation.stringWithContentsOfURL

@OptIn(ExperimentalForeignApi::class)
actual suspend fun readTextFromUrl(url: String): String? = withContext(Dispatchers.IO) {
    try {
        val nsUrl = NSURL.URLWithString(url) ?: NSURL.fileURLWithPath("")
        NSString.stringWithContentsOfURL(nsUrl, NSUTF8StringEncoding, null).toString()
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}

actual suspend fun saveStringOnLocal(key: String, value: String) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
}
actual suspend fun getStringFromLocal(key: String): String? {
    return NSUserDefaults.standardUserDefaults.stringForKey(key)
}