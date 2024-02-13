package uz.gita.memorygame.utils

import android.widget.ImageView
import uz.gita.memorygame.R

fun ImageView.openImage() {
    this.isClickable = false

    animate()
        .setDuration(500)
        .rotationY(-89f)
        .withEndAction {
            setImageResource(this.tag as Int)
            rotationY = 89f

            animate()
                .setDuration(500)
                .rotationY(0f)
                .start()
        }
        .start()
}


fun ImageView.hideImage() {
    this.isClickable = false

    this.animate()
        .setDuration(500)
        .rotationY(89f)
        .withEndAction {
            setImageResource(R.drawable.image_back)
            rotationY = -89f

            animate()
                .setDuration(500)
                .rotationY(0f)
                .start()
        }
        .start()
}

fun ImageView.removeImage() {
    this.isClickable = false
    this.animate()
        .setDuration(500)
        .scaleX(0f)
        .scaleY(0f)
        .start()
}

fun ImageView.removeImage(block: () -> Unit) {
    this.isClickable = false
    this.animate()
        .setDuration(500)
        .scaleX(0f)
        .scaleY(0f)
        .withEndAction(block)
        .start()
}