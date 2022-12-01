package dev.hnatiuk.uno_score.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val id: Int,
    val name: String,
    val loseCount: Int
) : Parcelable {

    companion object {

        fun createNew(name: String) = Player(
            id = PLAYER_NO_ID,
            name = name,
            loseCount = PLAYER_UNDEFINED_LOSE_COUNT
        )

        const val PLAYER_NO_ID = -1
        const val PLAYER_UNDEFINED_LOSE_COUNT = -1
    }
}