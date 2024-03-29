package uz.gita.memorygame.utils

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> T.myApply(block: T.() -> Unit) {
    block(this)
}

fun logger(message: String) {
    Log.d("TTT", message)
}

fun <T> Flow<T>.launchLifecycle(
    lifecycle: Lifecycle,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    block: (T) -> Unit
) {
    onEach { block(it) }
        .flowWithLifecycle(lifecycle)
        .launchIn(lifecycleCoroutineScope)
}