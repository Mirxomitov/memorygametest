package uz.gita.memorygame.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.memorygame.presentation.viewmodels.GameViewModel

class GameViewModelImpl : GameViewModel, ViewModel() {
    override val initCards = MutableSharedFlow<Unit>()

    override fun initCards() {
        viewModelScope.launch {
            initCards.emit(Unit)
        }
    }
}