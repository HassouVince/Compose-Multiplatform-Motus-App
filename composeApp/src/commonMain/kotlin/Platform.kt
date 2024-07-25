expect suspend fun readTextFromUrl(url: String): String?
expect suspend fun saveStringOnLocal(key: String, value: String)
expect suspend fun getStringFromLocal(key: String): String?