package uz.gita.memorygame.presentation.viewmodels


interface GameViewModel {
    fun getCardsByLevel(countX: Int, countY: Int) : List<Int>
    fun userWin()

}