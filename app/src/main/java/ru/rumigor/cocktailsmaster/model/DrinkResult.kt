package ru.rumigor.cocktailsmaster.model

sealed class DrinkResult{
    data class Success<out T>(val data: T) : DrinkResult()
    data class Error(val error: Throwable) : DrinkResult()
}
