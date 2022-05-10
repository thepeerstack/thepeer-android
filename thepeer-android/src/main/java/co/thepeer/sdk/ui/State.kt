package co.thepeer.sdk.ui

data class ThePeerState(
    val Loading: Boolean = false,
    val errorMessage: String? = null,
    val Success: Boolean = false
)

data class Event<T>(val state: ThePeerState, val data: T? = null)