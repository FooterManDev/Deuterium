package io.github.footermandev.deuterium

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.swing.SwingUtilities

fun later(block: suspend CoroutineScope.() -> Unit): Job = if(SwingUtilities.isEventDispatchThread()) {
    CoroutineScope(Dispatchers.Main.immediate).launch(block = block)
} else CoroutineScope(Dispatchers.Main).launch(block = block)

suspend fun laterAwait(block: suspend CoroutineScope.() -> Unit) = withContext(Dispatchers.Main) { block() }

fun laterOrNow(block: () -> Unit) = if(SwingUtilities.isEventDispatchThread()) block() else SwingUtilities.invokeLater(block)

fun laterWithDelay(delayMs: Long, block: suspend CoroutineScope.() -> Unit): Job =
    CoroutineScope(Dispatchers.Main).launch { delay(delayMs); block() }