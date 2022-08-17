package dev.hnatiuk.core.presentation.extensions

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getOrException(key: String): T {
    return get<T>(key) ?: throw IllegalStateException("No argument for key => $key")
}

fun <T> SavedStateHandle.getOrDefault(key: String, default: T): T {
    return get<T>(key) ?: default
}

