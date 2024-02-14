package uz.gita.memorygame.presentation.viewmodels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import uz.gita.memorygame.domain.AppRepository
import uz.gita.memorygame.presentation.viewmodels.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : GameViewModel, ViewModel() {
    override val reloadGameLiveData = MutableLiveData<Unit>()
    override val countLiveData = MutableLiveData<Int>(0)

    override fun getCardsByLevel(countX: Int, countY: Int) =
        repository.getCardsByLevel(countX, countY)

    override fun userWin() {
        countLiveData.value = 0
    }

    override fun reloadGame() {
        reloadGameLiveData.value = Unit
        countLiveData.value = 0
    }

    override fun increaseCount() {
        countLiveData.value = countLiveData.value?.plus(1)
    }
}