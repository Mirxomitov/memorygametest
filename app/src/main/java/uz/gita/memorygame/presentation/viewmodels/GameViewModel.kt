package uz.gita.memorygame.presentation.viewmodels

import kotlinx.coroutines.flow.Flow


interface GameViewModel {
    val initCards : Flow<Unit>
    fun initCards()
}