package ru.rumigor.cocktailsmaster.ui.viewmodel


import androidx.annotation.VisibleForTesting

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.rumigor.cocktailsmaster.model.Drink
import ru.rumigor.cocktailsmaster.model.DrinkResult
import ru.rumigor.cocktailsmaster.model.Repository
import ru.rumigor.cocktailsmaster.ui.base.BaseViewModel

class MainViewModel(private val repository: Repository) :
    BaseViewModel<List<Drink>?>() {

    private val drinksChannel by lazy { runBlocking { repository.subscribeToAllDrinks() } }
    private var drinks: List<Drink>? = null

    init {
        launch {
            drinksChannel.consumeEach { result ->
                when (result) {
                    is DrinkResult.Success<*> -> {
                        setData(result.data as? List<Drink>)
                        drinks = result.data as? List<Drink>
                    }
                    is DrinkResult.Error -> setError(result.error)
                }
            }
        }
    }


    @VisibleForTesting
    public override fun onCleared() {
        drinksChannel.cancel()
        super.onCleared()
    }

    fun removeNote(id: String) {
        launch {
            repository.removeDrink(id)
        }
    }


    fun deleteAllNotes() {
        launch {
            drinks?.let {
                for (drink in it) {
                    repository.removeDrink(drink.id)
                }
            }
        }
    }
}