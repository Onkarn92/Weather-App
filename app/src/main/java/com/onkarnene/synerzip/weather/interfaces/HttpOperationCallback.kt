package com.onkarnene.synerzip.weather.interfaces

import com.onkarnene.synerzip.weather.models.Error
import retrofit2.Call

interface HttpOperationCallback<T> {
	
	/**
	 * Callback function for any type of response.
	 *
	 * @param call      instance of executed [Call].
	 * @param result    contains response body.
	 * @param error contains human readable error message and cause.
	 */
	fun onResponse(
			call: Call<T>,
			result: T? = null,
			error: Error
	)
}