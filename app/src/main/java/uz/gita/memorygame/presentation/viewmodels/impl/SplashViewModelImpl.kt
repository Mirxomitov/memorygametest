package uz.gita.memorygame.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import uz.gita.memorygame.domain.AppRepository
import uz.gita.memorygame.domain.impl.AppRepositoryImpl
import uz.gita.memorygame.presentation.viewmodels.SplashViewModel

class SplashViewModelImpl: ViewModel(), SplashViewModel {
    private val repository : AppRepository = AppRepositoryImpl()

}