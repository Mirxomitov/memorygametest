package uz.gita.memorygame.domain

import uz.gita.memorygame.data.CardData

interface AppRepository {
    fun getCardsByLevel(countX: Int, countY: Int) : List<Int>
}