package uz.gita.memorygame.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memorygame.R
import uz.gita.memorygame.databinding.ScreenLevelBinding

class LevelScreen : Fragment(R.layout.screen_level) {
    private val binding by viewBinding(ScreenLevelBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            easy.setOnClickListener {
                startGame(2, 3)
            }
            medium.setOnClickListener {
                startGame(6, 8)
            }
            hard.setOnClickListener {
                startGame(8, 10)
            }
        }
    }

    private fun startGame(x: Int, y: Int) {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(x, y))
    }
}