package ru.rumigor.cocktailsmaster

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.rumigor.cocktailsmaster.data.FireStoreProvider
import ru.rumigor.cocktailsmaster.data.RemoteDataProvider
import ru.rumigor.cocktailsmaster.model.Repository
import ru.rumigor.cocktailsmaster.ui.viewmodel.MainViewModel
import ru.rumigor.cocktailsmaster.ui.viewmodel.SplashViewModel

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) } }
//
//val noteModule = module {
//    viewModel { NoteViewModel(get()) } }
