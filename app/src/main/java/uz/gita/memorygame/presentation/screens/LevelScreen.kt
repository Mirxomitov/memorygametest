package uz.gita.memorygame.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygame.R
import uz.gita.memorygame.databinding.ScreenLevelBinding

@AndroidEntryPoint
class LevelScreen : Fragment(R.layout.screen_level) {
    private val binding by viewBinding(ScreenLevelBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.safariCar
            .animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1000)
            .start()

        binding.tableSafari.animate()
            .scaleXBy(0.1f)
            .scaleYBy(0.1f)
            .setDuration(500)
            .start()

        binding.mapSafari.animate()
            .scaleXBy(0.3f)
            .scaleYBy(0.3f)
            .setDuration(500)
            .start()

        binding.root.post {
            val width = binding.root.width
            val treeWidth = binding.treeSafari.width

            val treeX = (width - treeWidth).toFloat() / 2

            binding.treeSafari.animate()
                .scaleX(1f)
                .scaleY(1f)
                .rotation(1000f)
                .setDuration(1000)
                .x(treeX)
                .start()
        }

        binding.apply {
            easy.setOnClickListener {
                startGame(2, 3)
            }
            medium.setOnClickListener {
                startGame(3, 6)
            }
            hard.setOnClickListener {
                startGame(4, 6)
            }
        }
    }

    private fun startGame(x: Int, y: Int) {
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(x, y))
    }
}