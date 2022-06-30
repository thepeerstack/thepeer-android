package co.thepeer.sdk.ui

import co.thepeer.sdk.model.ThepeerTransaction

interface ThepeerResultListener {
    fun onSuccess(transaction: ThepeerTransaction)

    fun onError(error: Throwable)

    fun onCancelled()
}
