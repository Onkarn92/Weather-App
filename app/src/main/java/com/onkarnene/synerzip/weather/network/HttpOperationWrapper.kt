package com.onkarnene.synerzip.weather.network

import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.onkarnene.synerzip.weather.R
import com.onkarnene.synerzip.weather.interfaces.HttpOperationCallback
import com.onkarnene.synerzip.weather.models.Error
import com.onkarnene.synerzip.weather.utilities.NetworkUtils
import com.onkarnene.synerzip.weather.utilities.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HttpOperationWrapper<T> {
	
	private lateinit var callback: HttpOperationCallback<T>
	
	/**
	 * Initialize HTTP call.
	 * Check for internet connection.
	 *
	 * @param call              to be execute.
	 * @param callback          of registered API wrapper.
	 * @param isSynchronousCall true if call is synchronous, default is false
	 */
	fun initCall(
			call: Call<T>,
			callback: HttpOperationCallback<T>,
			isSynchronousCall: Boolean = false
	) {
		this.callback = callback
		if (NetworkUtils.isNetworkAvailable()) {
			if (isSynchronousCall) executeRequest(call) else enqueueRequest(call)
		} else {
			this.callback.onResponse(call, null, NetworkUtils.getRequestFailReason(NetworkUtils.CODE_NO_INTERNET))
		}
	}
	
	/**
	 * Enqueue Http Request and return response/failure using registered callback function.
	 *
	 * @param call to be executed.
	 */
	private fun enqueueRequest(call: Call<T>) = call.enqueue(object : Callback<T> {
		
		override fun onResponse(
				call: Call<T>,
				response: Response<T>
		) {
			val error: Error = response.errorBody()?.string()?.let {
				val message = Gson().fromJson(it, Error::class.java).message
				NetworkUtils.getRequestFailReason(response.code(), message)
			} ?: NetworkUtils.getRequestFailReason(response.code())
			this@HttpOperationWrapper.callback.onResponse(call, if (NetworkUtils.isValidResponse(response)) response.body() else null, error)
		}
		
		override fun onFailure(
				call: Call<T>,
				t: Throwable
		) {
			this@HttpOperationWrapper.callback.onResponse(call = call,
			                                              error = NetworkUtils.getRequestFailReason(message = t.localizedMessage
					                                              ?: Utils.getString(R.string.err_msg_something_went_wrong)))
		}
	})
	
	/**
	 * Execute Http Request and return response/failure using registered callback function.
	 *
	 * @param call to be executed.
	 */
	@WorkerThread
	private fun executeRequest(call: Call<T>) = try {
		val response = call.execute()
		val error: Error = response.errorBody()?.string()?.let {
			val message = Gson().fromJson(it, Error::class.java).message
			NetworkUtils.getRequestFailReason(response.code(), message)
		} ?: NetworkUtils.getRequestFailReason(response.code())
		this@HttpOperationWrapper.callback.onResponse(call, if (NetworkUtils.isValidResponse(response)) response.body() else null, error)
	} catch (e: IOException) {
		e.printStackTrace()
		this@HttpOperationWrapper.callback.onResponse(call = call,
		                                              error = NetworkUtils.getRequestFailReason(message = e.localizedMessage
				                                              ?: Utils.getString(R.string.err_msg_something_went_wrong)))
	} catch (e: RuntimeException) {
		e.printStackTrace()
		this@HttpOperationWrapper.callback.onResponse(call = call,
		                                              error = NetworkUtils.getRequestFailReason(message = e.localizedMessage
				                                              ?: Utils.getString(R.string.err_msg_something_went_wrong)))
	}
}