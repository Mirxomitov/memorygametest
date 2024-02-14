package uz.gita.memorygame.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        super.onViewCreated(view, savedInstanceState)

        viewModel.reloadGameLiveData.observe(this@GameScreen, reloadGameObserver)
        viewModel.countLiveData.observe(this@GameScreen, countObserver)

        countY = navArgs.verticalCardCount
        countX = navArgs.horizontalCardCount

        binding.container.post {
            cardHeight = container.height.toFloat() / countY
            cardWidth = container.width.toFloat() / countX

            val cards = viewModel.getCardsByLevel(countX, countY)
            loadImages(cards)
            setCardsClick()
        }

        reload.setOnClickListener {
            viewModel.reloadGame()
        }
        menu.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val countObserver = Observer<Int> {
        binding.attempt.text = it.toString()
    }

    private val reloadGameObserver = Observer<Unit> {
        binding.reload.isClickable = false
        firstImageIndex = -1
        secondImageIndex = -1

        val cards = viewModel.getCardsByLevel(countX, countY)
        binding.container.children.forEachIndexed { index, view ->
            val img = view as ImageView
            img.tag = cards[index]

            img.isClickable = true

            img.layoutParams.apply {
                height = cardHeight.toInt()
                width = cardWidth.toInt()
            }

            img.animate()
                .setDuration(500)
                .rotationBy(180f)
                .scaleX(0f)
                .scaleY(0f)
                .withEndAction {
                    img.setImageResource(R.drawable.image_back)
                    img.rotation = 0f
                    img.animate()
                        .setDuration(500)
                        .scaleX(1f)
                        .scaleY(1f)
                        .rotationBy(360f)
                        .start()

                    lifecycleScope.launch {
                        delay(500)
                        binding.reload.isClickable = true
                    }
                }
                .start()
            views[index] = img
        }
    }

    private fun loadImages(images: List<Int>) {
        for (i in 0 until countY) {
            for (j in 0 until countX) {
                val img = ImageView(requireContext())
                img.isClickable = false
                img.setImageResource(R.drawable.image_back)

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
                viewModel.increaseCount()

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
