package com.onkarnene.synerzip.weather.interfaces

import androidx.annotation.MainThread
import com.onkarnene.synerzip.weather.models.Error

interface HttpEventTracker<T> {
	
	/**
	 * Callback function.
	 * Call when current HTTP request executes successfully.
	 *
	 * @param response contains respective response model.
	 */
	@MainThread
	fun onCallSuccess(response: T)
	
	/**
	 * Callback function.
	 * Call when current HTTP request fails or response code is not 200 (HTTP OK).
	 *
	 * @param error contains error body of response.
	 */
	@MainThread
	fun onCallFail(error: Error)
}