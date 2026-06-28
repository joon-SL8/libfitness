package com.skjline.fitness.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// A generic wrapper to manage the flow subscription lifecycle
class FlowWrapper<T : Any>(private val flow: Flow<T>) {
    fun subscribe(
        scope: CoroutineScope,
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit
    ): DisposableHandle { // DisposableHandle for managing cancellation
        val job = scope.launch {
            try {
                flow.collect { onEach(it) }
                onComplete()
            } catch (e: Throwable) {
                onThrow(e)
            }
        }

        return DisposableHandle { job.cancel() }
    }
}
