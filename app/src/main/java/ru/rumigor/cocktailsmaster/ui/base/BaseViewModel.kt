package ru.rumigor.cocktailsmaster.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.conflate
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<T> : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext by lazy{
        Dispatchers.Default + Job()
    }

    private val viewStateChannel = MutableSharedFlow<T>()
    private val errorChannel = Channel<Throwable>()

    fun getViewState(): SharedFlow<T> = viewStateChannel.asSharedFlow()
    fun getErrorChannel(): ReceiveChannel<Throwable> = errorChannel

    fun setError(e: Throwable){
        launch {
            errorChannel.send(e)
        }
    }

    fun setData(data: T){
        launch {
            viewStateChannel.emit(data)
        }
    }

    override fun onCleared() {
        errorChannel.close()
        coroutineContext.cancel()
        super.onCleared()
    }

}
