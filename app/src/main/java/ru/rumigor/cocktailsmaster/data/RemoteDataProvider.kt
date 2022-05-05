package ru.rumigor.cocktailsmaster.data

import kotlinx.coroutines.channels.ReceiveChannel
import ru.rumigor.cocktailsmaster.model.Drink
import ru.rumigor.cocktailsmaster.model.DrinkResult
import ru.rumigor.cocktailsmaster.model.User

interface RemoteDataProvider {
    suspend fun subscribeToAllDrinks(): ReceiveChannel<DrinkResult>
    suspend fun getDrinkById(id: String): Drink
    suspend fun saveDrink(drink: Drink) : Drink
    suspend fun removeDrink(id :String) : Drink?
//    suspend fun getCurrentUser(): User?
}