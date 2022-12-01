package dev.hnatiuk.core.presentation.utils;

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class StringResource(
    @StringRes val resId: Int,
    val args: List<String> = emptyList()
) : Parcelable

val Int.asStringRes get() = StringResource(this)