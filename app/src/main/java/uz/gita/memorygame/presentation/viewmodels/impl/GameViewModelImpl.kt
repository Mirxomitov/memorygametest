package uz.gita.memorygame.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memorygame.domain.AppRepository
import uz.gita.memorygame.presentation.viewmodels.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl
@Inject constructor(
    private val repository: AppRepository
) : GameViewModel, ViewModel() {

    override fun getCardsByLevel(countX: Int, countY: Int) =
        repository.getCardsByLevel(countX, countY)

    override fun userWin() {

    }
}