package com.onkarnene.synerzip.weather.helpers

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

/**
 * Singleton instance which provides different thread pool executors.
 */
object AppExecutors {
	
	val diskIO: Executor by lazy {Executors.newSingleThreadExecutor()}
	
	val mainThread: Executor by lazy {MainThreadExecutor()}
	
	val networkIO: Executor by lazy {Executors.newFixedThreadPool(3)}
	
	private class MainThreadExecutor : Executor {
		
		private val handler = Handler(Looper.getMainLooper())
		
		override fun execute(command: Runnable) {
			handler.post(command)
		}
	}
}