package ru.rumigor.cocktailsmaster.ui.activity

import ru.rumigor.cocktailsmaster.model.Drink

data class Data(val isRemoved: Boolean = false, val drink: Drink? = null)
