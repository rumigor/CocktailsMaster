package ru.rumigor.cocktailsmaster.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val date: String = "",
    val description: String = "",
    val rating: Float = 0f,
    val recipe: String = "",
    val title: String = "",
    val type: String = "",
    val imageUrl : String

) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Drink

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}