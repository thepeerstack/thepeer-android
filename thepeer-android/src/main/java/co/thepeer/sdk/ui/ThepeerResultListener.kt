package co.thepeer.sdk.ui

interface ThepeerResultListener {
    fun onSuccess(response: String)

    fun onError(error: Throwable)

    fun onCancelled()
}
