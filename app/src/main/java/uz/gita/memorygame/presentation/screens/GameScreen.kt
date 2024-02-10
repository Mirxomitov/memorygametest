package uz.gita.memorygame.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memorygame.R
import uz.gita.memorygame.data.ImagesMapper
import uz.gita.memorygame.databinding.ScreenGameBinding
import uz.gita.memorygame.utils.myApply

class GameScreen : Fragment(R.layout.screen_game) {
    private val binding by viewBinding(ScreenGameBinding::bind)
    private val navArgs by navArgs<GameScreenArgs>()
    private var views = ArrayList<ImageView>()
    private var countX: Int = 0
    private var countY: Int = 0
    private var cardWidth: Float = 0f
    private var cardHeight: Float = 0f
    private var firstImageIndex = -1
    private var secondImageIndex = -1
    private var openedImages = ArrayList<Boolean>()
    private var imagesID = ArrayList<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        super.onViewCreated(view, savedInstanceState)

        binding.container.post {
            countY = navArgs.verticalCardCount
            countX = navArgs.horizontalCardCount

            cardHeight = container.height.toFloat() / countY
            cardWidth = container.width.toFloat() / countX

            loadImagesID()
            fillListOpenedImages()
            setImages()
            addClickEvent()
        }
    }

    private fun loadImagesID() {
        repeat(2) {
            for (i in 0 until countY * countX / 2) {
                imagesID.add(ImagesMapper.map[i]!!)
            }
        }

        imagesID.shuffle()
    }

    private fun fillListOpenedImages() {
        repeat(countX * countY) {
            openedImages.add(false)
        }
    }

    private fun addClickEvent() {
        views.forEachIndexed { index, image ->
            image.setOnClickListener {
                if (openedImages[index]) {
                    if (firstImageIndex != -1) {
                        firstImageIndex = -1
                    } else if (secondImageIndex != -1) {
                        secondImageIndex = -1
                    } else {
                        return@setOnClickListener
                    }

                    image.animate()
                        .setDuration(500)
                        .rotationY(-89f)
                        .withEndAction {
                            image.rotationY = 89f
                            image.setImageResource(R.drawable.image_back)
                            image.animate()
                                .setDuration(1000)
                                .rotationY(0f)
                                .start()
                        }
                        .start()
                    openedImages[index] = false

                } else {
                    if (firstImageIndex == -1) {
                        firstImageIndex = index
                    } else if (secondImageIndex == -1) {
                        secondImageIndex = index
                    } else {
                        return@setOnClickListener
                    }

                    image.animate()
                        .setDuration(500)
                        .rotationY(89f)
                        .withEndAction {
                            image.rotationY = -89f  // 271
                            image.setImageResource(imagesID[index])

                            image.animate()
                                .setDuration(1000)
                                .rotationY(0f)
                                .start()
                        }
                        .start()
                    openedImages[index] = true
                }

                if (firstImageIndex != -1 && secondImageIndex != -1) {
                    if (imagesID[firstImageIndex] == imagesID[secondImageIndex]) {
                        lifecycleScope.launch {
                            delay(1000)
                            views[firstImageIndex].animate()
                                .setDuration(1000)
                                .alpha(0.1f)
                                .start()
                            views[secondImageIndex].animate()
                                .setDuration(1000)
                                .alpha(0.1f)
                                .start()

                            firstImageIndex = -1
                            secondImageIndex = -1
                        }
                    } else {

                    }
                }
            }
        }
    }

    private fun setImages() {
        for (i in 0 until countY) {
            for (j in 0 until countX) {
                val img = ImageView(requireContext())
                img.animate()
                    .setDuration(500)
                    .x(j * cardWidth)
                    .y(i * cardHeight)
                    .start()

                img.setImageResource(R.drawable.image_back)
                binding.container.addView(img)

                img.layoutParams.apply {
                    height = cardHeight.toInt()
                    width = cardWidth.toInt()
                }

                views.add(img)
            }
        }
    }
}
