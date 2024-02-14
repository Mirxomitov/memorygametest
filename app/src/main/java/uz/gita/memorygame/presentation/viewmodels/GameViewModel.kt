package uz.gita.memorygame.presentation.viewmodels

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


interface GameViewModel {
    val reloadGameLiveData : LiveData<Unit>
    val countLiveData : LiveData<Int>

    fun getCardsByLevel(countX: Int, countY: Int) : List<Int>
    fun userWin()
    fun reloadGame()
    fun increaseCount()
}