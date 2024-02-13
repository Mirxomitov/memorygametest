package uz.gita.memorygame.domain.impl

import uz.gita.memorygame.R
import uz.gita.memorygame.data.CardData
import uz.gita.memorygame.domain.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(

) : AppRepository {
    private val imageIDList = listOf<Int>(
        R.drawable.image_1,
        R.drawable.image_2,
        R.drawable.image_3,
        R.drawable.image_4,
        R.drawable.image_5,
        R.drawable.image_6,
        R.drawable.image_7,
        R.drawable.image_8,
        R.drawable.image_9,
        R.drawable.image_10,
        R.drawable.image_11,
        R.drawable.image_12,
        R.drawable.image_13,
        R.drawable.image_14,
        R.drawable.image_15,
        R.drawable.image_16,
        R.drawable.image_17,
        R.drawable.image_18,
        R.drawable.image_19,
        R.drawable.image_20,
        R.drawable.image_21,
        R.drawable.image_22,
        R.drawable.image_23,
        R.drawable.image_24,
        R.drawable.image_25,
        R.drawable.image_26,
        R.drawable.image_27,
        R.drawable.image_28,
        R.drawable.image_29,
        R.drawable.image_30,
        R.drawable.image_31,
        R.drawable.image_32,
        R.drawable.image_33,
        R.drawable.image_34,
        R.drawable.image_35,
        R.drawable.image_36,
        R.drawable.image_37,
        R.drawable.image_38,
        R.drawable.image_39,
        R.drawable.image_40,
        R.drawable.image_41,
        R.drawable.image_42,
        R.drawable.image_43,
        R.drawable.image_44,
        R.drawable.image_45,
        R.drawable.image_46,
        R.drawable.image_47,
        R.drawable.image_48,
        R.drawable.image_49,
        R.drawable.image_50,
        R.drawable.image_51,
        R.drawable.image_52,
        R.drawable.image_53,
        R.drawable.image_54
    )

    override fun getCardsByLevel(countX: Int, countY: Int): List<Int> {
        val result = ArrayList<Int>()

        val images = imageIDList.shuffled().subList(0, countX * countY / 2)
        repeat(2) {result.addAll(images)}

        return result.shuffled()
    }
}