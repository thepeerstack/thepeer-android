package co.thepeer.sdk.ui

import co.thepeer.sdk.model.ThePeerTransaction

interface ThePeerResultListener {
    fun onSuccess(transaction: ThePeerTransaction)

    fun onError(error: Throwable)

    fun onCancelled()
}