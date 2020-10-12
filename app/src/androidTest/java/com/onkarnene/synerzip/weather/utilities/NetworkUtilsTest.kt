package com.onkarnene.synerzip.weather.utilities

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.onkarnene.synerzip.weather.R
import com.onkarnene.synerzip.weather.models.WeatherDetails
import org.junit.*
import org.junit.runner.*
import retrofit2.Response
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class NetworkUtilsTest {
	
	private lateinit var context: Context
	
	@Before
	fun init() {
		context = ApplicationProvider.getApplicationContext()
	}
	
	@Test
	fun networkUtils_Retrofit_BaseUrl_ReturnsEquals() {
		val baseUrl = "http://api.openweathermap.org/"
		val retrofitBaseUrl = NetworkUtils.retrofit.baseUrl()
		Assert.assertEquals(baseUrl, retrofitBaseUrl.toString())
	}
	
	@Test
	fun networkUtils_DefaultParameters_ReturnEquals() {
		val error = NetworkUtils.getRequestFailReason()
		Assert.assertEquals(error.cause, context.getString(R.string.err_something_went_wrong))
		Assert.assertEquals(error.message, context.getString(R.string.err_msg_something_went_wrong))
	}
	
	@Test
	fun networkUtils_TestParameters_ReturnEquals() {
		val error = NetworkUtils.getRequestFailReason(HttpURLConnection.HTTP_BAD_REQUEST)
		Assert.assertEquals(error.cause, context.getString(R.string.err_bad_request))
		Assert.assertEquals(error.message, context.getString(R.string.err_msg_bad_request))
	}
	
	@Test
	fun networkUtils_UnknownParameters_ReturnEquals() {
		val error = NetworkUtils.getRequestFailReason(-10)
		Assert.assertEquals(error.cause, context.getString(R.string.err_something_went_wrong))
		Assert.assertEquals(error.message, context.getString(R.string.err_msg_something_went_wrong))
	}
	
	@Test
	fun networkUtils_Response_Success_ReturnsEquals() {
		val response = Response.success(WeatherDetails())
		Assert.assertEquals(true, NetworkUtils.isValidResponse(response))
	}
	
	@Test
	fun networkUtils_Response_Null_ReturnsEquals() {
		val response = Response.success(null)
		Assert.assertEquals(false, NetworkUtils.isValidResponse(response))
	}
	
	@Test
	fun networkUtils_Network_ReturnsEquals() {
		Assert.assertEquals(true, NetworkUtils.isNetworkAvailable())
	}
}