package ru.rumigor.cocktailsmaster.ui.activity

import ru.rumigor.cocktailsmaster.databinding.ActivitySplashBinding
import ru.rumigor.cocktailsmaster.ui.base.BaseActivity
import ru.rumigor.cocktailsmaster.ui.viewmodel.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

private const val START_DELAY = 1000L

class SplashActivity : BaseActivity<Boolean>() {
    override val viewModel: SplashViewModel by viewModel()
    override val ui: ActivitySplashBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }


//    override fun onResume() {
//        super.onResume()
//        Handler(Looper.getMainLooper()).postDelayed({ viewModel.requestUser() }, START_DELAY)
//    }

    override fun renderData(data: Boolean) {
        if (data) startMainActivity()
    }


    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}