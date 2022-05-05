package ru.rumigor.cocktailsmaster.data

import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.rumigor.cocktailsmaster.model.Drink
import ru.rumigor.cocktailsmaster.model.DrinkResult
import ru.rumigor.cocktailsmaster.model.User
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val COCKTAILS_COLLECTION = "cocktails"
private const val INGREDIENTS_COLLECTION = "ingredients"
private const val INGREDIENTS_TYPE_COLLECTION = "ingredients_type"
private const val UNITES_COLLECTION = "units"
private const val USERS_COLLECTION = "users"

class FireStoreProvider(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : RemoteDataProvider {

    companion object {
        private val TAG = "${FireStoreProvider::class.java.simpleName} :"
    }

    @ExperimentalCoroutinesApi
    override suspend fun subscribeToAllDrinks(): ReceiveChannel<DrinkResult> =
        Channel<DrinkResult>(Channel.CONFLATED).apply {
            var registration: ListenerRegistration? = null
            try {
                registration = db.collection(COCKTAILS_COLLECTION)
                    .addSnapshotListener { snapshot, e ->
                        val value = e?.let {
                            DrinkResult.Error(it)
                        } ?: snapshot?.let { querySnapshot ->
                            val notes = querySnapshot.documents.map { documentSnapshot ->
                                documentSnapshot.toObject(Drink::class.java)
                            }
                            DrinkResult.Success(notes.sortedByDescending { drink -> drink?.date })
                        }

                        value?.let { trySend(it).isSuccess }
                    }

            } catch (e: Throwable) {
                trySend(DrinkResult.Error(e)).isSuccess
            }
            invokeOnClose { registration?.remove() }
        }

    override suspend fun getDrinkById(id: String): Drink =
        suspendCoroutine { continuation ->
            try {
                db.collection(COCKTAILS_COLLECTION).document(id)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        continuation.resume(snapshot.toObject(Drink::class.java)!!)
                    }
                    .addOnFailureListener { exception ->
                        throw exception
                    }
            } catch (error: Throwable) {
                continuation.resumeWithException(error)
            }
        }

    override suspend fun saveDrink(drink: Drink): Drink =
        suspendCoroutine { continuation ->
            try {
                db.collection(COCKTAILS_COLLECTION).document(drink.id)
                    .set(drink).addOnSuccessListener {
                        continuation.resume(drink)
                    }
                    .addOnFailureListener {
                        OnFailureListener { exception ->
                            throw exception
                        }
                    }
            } catch (error: Throwable) {
                continuation.resumeWithException(error)
            }

        }

    override suspend fun removeDrink(id: String): Drink? =
        suspendCoroutine { continuation ->
            try {
                db.collection(COCKTAILS_COLLECTION).document(id)
                    .delete()
                    .addOnSuccessListener {
                        continuation.resume(null)
                    }
                    .addOnFailureListener { throw it }
            } catch (error: Exception) {
                continuation.resumeWithException(error)
            }
        }

    private val currentUser
        get() = firebaseAuth.currentUser

//    private fun getUserDrinksCollection() = currentUser?.let {
//        db.collection(USERS_COLLECTION).document(it.uid).collection(COCKTAILS_COLLECTION)
//    } ?: throw NoAuthException()
//
//    override suspend fun getCurrentUser(): User? =
//        suspendCoroutine { continuation ->
//            currentUser?.let { firebaseUser ->
//                continuation.resume(User(firebaseUser.displayName ?: "", firebaseUser.email ?: ""))
//            } ?: continuation.resume(null)
//        }
}