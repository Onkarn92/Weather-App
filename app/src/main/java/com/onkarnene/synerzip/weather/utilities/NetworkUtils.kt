package com.onkarnene.synerzip.weather.utilities

import android.content.Context
import android.net.ConnectivityManager
import com.onkarnene.synerzip.weather.App
import com.onkarnene.synerzip.weather.R
import com.onkarnene.synerzip.weather.models.Error
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit.*

object NetworkUtils {
	
	const val CODE_NO_INTERNET: Int = 0
	const val TYPE_JSON = "application/json"
	const val APP_ID = "e38e2a7bab29cdcb280e018f4f18c43d"
	const val ENDPOINT_WEATHER_BY_CITY_NAME = "data/2.5/weather"
	private const val BASE_URL = "http://api.openweathermap.org/"
	
	/**
	 * Creates single instance of retrofit.
	 *
	 * @return retrofit instance.
	 */
	val retrofit: Retrofit by lazy {
		val client = OkHttpClient().newBuilder().apply {
			connectTimeout(30, SECONDS)
			readTimeout(30, SECONDS)
		}.build()
		Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()
	}
	
	/**
	 * Check for response validity with following conditions:
	 * NotNull, Non-empty body, [Response.isSuccessful]
	 *
	 * @param response object to be examined.
	 *
	 * @return true if response is valid, otherwise false.
	 */
	fun isValidResponse(response: Response<*>): Boolean = response.isSuccessful && response.body() != null
	
	/**
	 * Identify failure reason based on HTTP status codes.
	 * If no status code was provided or in case of unidentified status code, It will return default message.
	 *
	 * @param code requires to identify exact reason of failure request.
	 * @param message provided by [Retrofit] errorBody.
	 */
	fun getRequestFailReason(
			code: Int = DEFAULT_INT,
			message: String = Utils.getString(R.string.err_msg_something_went_wrong)
	): Error = when (code) {
		CODE_NO_INTERNET -> {
			Error(cause = Utils.getString(R.string.err_request_fail), message = Utils.getString(R.string.err_no_internet))
		}
		HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_BAD_METHOD, HttpURLConnection.HTTP_UNSUPPORTED_TYPE -> {
			Error(cause = Utils.getString(R.string.err_bad_request), message = Utils.getString(R.string.err_msg_bad_request))
		}
		HttpURLConnection.HTTP_UNAUTHORIZED -> {
			Error(cause = Utils.getString(R.string.err_unauthorized), message = Utils.getString(R.string.err_msg_unauthorized))
		}
		HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_NOT_ACCEPTABLE -> {
			Error(cause = Utils.getString(R.string.err_page_not_found), message = Utils.getString(R.string.err_msg_page_not_found))
		}
		HttpURLConnection.HTTP_CLIENT_TIMEOUT, HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
			Error(cause = Utils.getString(R.string.err_timeout), message = Utils.getString(R.string.err_msg_timeout))
		}
		HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_UNAVAILABLE -> {
			Error(cause = Utils.getString(R.string.err_maintenance_break), message = Utils.getString(R.string.err_msg_maintenance_break))
		}
		else -> {
			Error(cause = Utils.getString(R.string.err_something_went_wrong), message = message)
		}
	}
	
	/**
	 * Check for internet connection availability.
	 *
	 * @return true if device is currently connected to the internet (WiFi or Mobile Data), otherwise false.
	 */
	@Suppress("DEPRECATION")
	fun isNetworkAvailable(): Boolean {
		val networkInfo = (App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.activeNetworkInfo
		return networkInfo != null && networkInfo.isConnected
	}
}