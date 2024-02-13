package uz.gita.memorygame.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memorygame.R
import uz.gita.memorygame.databinding.ScreenGameBinding
import uz.gita.memorygame.presentation.viewmodels.GameViewModel
import uz.gita.memorygame.presentation.viewmodels.impl.GameViewModelImpl
import uz.gita.memorygame.utils.hideImage
import uz.gita.memorygame.utils.myApply
import uz.gita.memorygame.utils.openImage
import uz.gita.memorygame.utils.removeImage

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val navArgs by navArgs<GameScreenArgs>()
    private var views = ArrayList<ImageView>()
    private var countX: Int = 0
    private var countY: Int = 0
    private var cardWidth: Float = 0f
    private var cardHeight: Float = 0f
    private var firstImageIndex = -1
    private var secondImageIndex = -1
    private var findCardsCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        super.onViewCreated(view, savedInstanceState)

        countY = navArgs.verticalCardCount
        countX = navArgs.horizontalCardCount

        binding.container.post {
            cardHeight = container.height.toFloat() / countY
            cardWidth = container.width.toFloat() / countX

            val ls = viewModel.getCardsByLevel(countX, countY)

            loadImages(ls)
            lifecycleScope.launch {
                delay(1000) // 1 soniya kartalarni ochiq ushlab turadi,
                hideCards()
                delay(1000) // animatsiya bilan yopilishi uchun 1 soniya ketadi
                setCardsClick()
            }
        }

        reload.setOnClickListener {

        }
        menu.setOnClickListener { }
    }

    private fun hideCards() {
        views.forEach {
            it.hideImage()
        }
    }

    private fun loadImages(images: List<Int>) {
        for (i in 0 until countY) {
            for (j in 0 until countX) {
                val img = ImageView(requireContext())
                img.isClickable = false
                img.setImageResource(images[i * countX + j])

                img.x = j * cardWidth
                img.y = i * cardHeight
                img.tag = images[i * countX + j]

                binding.container.addView(img)

                img.layoutParams.apply {
                    height = cardHeight.toInt()
                    width = cardWidth.toInt()
                }

                views.add(img)
            }
        }
    }

    private fun setCardsClick() {
        views.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                if (firstImageIndex != -1 && secondImageIndex != -1) return@setOnClickListener
                imageView.openImage()

                if (firstImageIndex == -1) {
                    firstImageIndex = index
                } else {
                    secondImageIndex = index
                    check()
                }
            }
        }
    }

    private fun check() {
        val first = views[firstImageIndex]
        val second = views[secondImageIndex]

        if (first.tag == second.tag) {
            lifecycleScope.launch {
                delay(1000)

                first.removeImage()
                second.removeImage {
                    firstImageIndex = -1
                    secondImageIndex = -1
                }
            }
        } else {
            lifecycleScope.launch {
                delay(1000)
                first.hideImage()
                second.hideImage()

                delay(1000) // animatsiya tugashi uchun 1 soniya ushlab turadi
                first.isClickable = true
                second.isClickable = true

                firstImageIndex = -1
                secondImageIndex = -1
                findCardsCount += 2

                if (findCardsCount == countX * countY) {
                    viewModel.userWin()
                }
            }
        }
    }
}
