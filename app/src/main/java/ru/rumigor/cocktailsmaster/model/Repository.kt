package ru.rumigor.cocktailsmaster.model

import kotlinx.coroutines.channels.ReceiveChannel
import ru.rumigor.cocktailsmaster.data.RemoteDataProvider

class Repository (private val remoteDataProvider: RemoteDataProvider){
    suspend fun subscribeToAllDrinks() = remoteDataProvider.subscribeToAllDrinks()
    suspend fun getDrinkById(id: String) = remoteDataProvider.getDrinkById(id)
    suspend fun saveDrink(drink: Drink) = remoteDataProvider.saveDrink(drink)
    suspend fun removeDrink(id :String) = remoteDataProvider.removeDrink(id)
}