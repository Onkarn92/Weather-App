package com.onkarnene.synerzip.weather.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.onkarnene.synerzip.weather.R
import com.onkarnene.synerzip.weather.databinding.ActivityMainBinding
import com.onkarnene.synerzip.weather.di.DaggerWeatherComponent
import com.onkarnene.synerzip.weather.hideKeyboard
import com.onkarnene.synerzip.weather.isValidInput
import com.onkarnene.synerzip.weather.models.Weather
import com.onkarnene.synerzip.weather.utilities.HECTO_PASCALS
import com.onkarnene.synerzip.weather.utilities.MM_PER_HOUR
import com.onkarnene.synerzip.weather.utilities.PERCENT
import com.onkarnene.synerzip.weather.utilities.PreferencesUtils
import com.onkarnene.synerzip.weather.utilities.Unit.IMPERIAL
import com.onkarnene.synerzip.weather.utilities.Unit.METRIC
import com.onkarnene.synerzip.weather.utilities.Unit.STANDARD
import com.onkarnene.synerzip.weather.utilities.Utils
import com.onkarnene.synerzip.weather.views.models.MainViewModel
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityMainBinding
	
	@Inject lateinit var viewModel: MainViewModel
	
	companion object {
		
		private const val KEY_QUERY = "key_query"
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		// Initialising the dagger component to inject all dependencies.
		DaggerWeatherComponent.builder().withOwner(this).build().injectMainActivity(this)
		setupView(savedInstanceState?.getString(KEY_QUERY))
		attachObservers()
	}
	
	override fun onSaveInstanceState(outState: Bundle) {
		// Saving current search query text to handle orientation change.
		outState.putString(KEY_QUERY, binding.searchEdit.text.toString())
		super.onSaveInstanceState(outState)
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
		R.id.action_unit_standard -> {
			if (PreferencesUtils.getUnit() != STANDARD) {
				PreferencesUtils.saveUnit(STANDARD)
				Toast.makeText(this, R.string.msg_save_standard, Toast.LENGTH_SHORT).show()
				binding.searchEdit.text?.let {searchCity(it.toString(), true)}
			}
			true
		}
		R.id.action_unit_metric -> {
			if (PreferencesUtils.getUnit() != METRIC) {
				PreferencesUtils.saveUnit(METRIC)
				Toast.makeText(this, R.string.msg_save_metric, Toast.LENGTH_SHORT).show()
				binding.searchEdit.text?.let {searchCity(it.toString(), true)}
			}
			true
		}
		R.id.action_unit_imperial -> {
			if (PreferencesUtils.getUnit() != IMPERIAL) {
				PreferencesUtils.saveUnit(IMPERIAL)
				Toast.makeText(this, R.string.msg_save_imperial, Toast.LENGTH_SHORT).show()
				binding.searchEdit.text?.let {searchCity(it.toString(), true)}
			}
			true
		}
		else -> super.onOptionsItemSelected(item)
	}
	
	override fun onStart() {
		super.onStart()
		viewModel.clearExpiredWeatherDetails()
	}
	
	private fun setupView(existingQuery: String?) {
		supportActionBar?.setDisplayShowTitleEnabled(true)
		supportActionBar?.title = Utils.getString(R.string.current_weather)
		with(binding) {
			searchEdit.setText(existingQuery)
			searchEdit.hideKeyboard()
			searchTextInput.setEndIconOnClickListener {
				// Trigger when search icon gets clicked.
				if (searchEdit.isValidInput(searchTextInput, Utils.getString(R.string.err_invalid_input))) {
					searchCity(searchEdit.text.toString())
					searchEdit.hideKeyboard()
				}
			}
		}
	}
	
	/**
	 * Observe all existing and new weather details and render it on UI, as well as observer corresponding errors.
	 */
	@SuppressLint("SetTextI18n")
	private fun attachObservers() {
		viewModel.getWeatherObserver().observe(this) {
			val weather: Weather? = if (it.weather.isNotEmpty()) it.weather.first() else null
			val iconUrl = Utils.getIconUrl(weather?.icon)
			with(binding) {
				detailsLayout.visibility = View.VISIBLE
				progress.visibility = View.GONE
				errorLayout.root.visibility = View.GONE
				with(binding.contentLayout) {
					Glide.with(this@MainActivity).load(iconUrl).error(R.drawable.ic_launcher_background).into(tempIcon)
					nameText.text = "${it.name}, ${it.sys.country}"
					unitText.text = Utils.getString(R.string.unit).format(PreferencesUtils.getUnit().getValue().capitalize(Locale.getDefault()))
					tempText.text = Utils.roundOffTemperature(it.main.temp)
					descriptionText.text = Utils.getString(R.string.feels_like)
							.format(Utils.roundOffTemperature(it.main.feelsLike), weather?.main, weather?.description)
					minTempText.text = Utils.getString(R.string.min_temp).format(Utils.roundOffTemperature(it.main.tempMin))
					maxTempText.text = Utils.getString(R.string.max_temp).format(Utils.roundOffTemperature(it.main.tempMax))
					pressureText.text = Utils.getString(R.string.pressure).format("${it.main.pressure} $HECTO_PASCALS")
					humidityText.text = Utils.getString(R.string.humidity).format("${it.main.humidity} $PERCENT")
					windSpeedText.text = Utils.getString(R.string.wind_speed).format(Utils.formatWindSpeed(it.wind.speed))
					cloudsText.text = Utils.getString(R.string.clouds).format("${it.clouds.all} $PERCENT")
					rainText.text = Utils.getString(R.string.rain).format("${it.rain.oneHour} $MM_PER_HOUR")
					visibilityText.text = Utils.getString(R.string.visibility).format(Utils.calculateVisibility(it.visibility))
				}
			}
		}
		
		viewModel.getErrorObserver().observe(this) {
			with(binding) {
				detailsLayout.visibility = View.GONE
				progress.visibility = View.GONE
				errorLayout.root.visibility = View.VISIBLE
				errorLayout.errorTitleText.text = it.cause
				errorLayout.errorMessageText.text = it.message
			}
		}
	}
	
	private fun searchCity(
			name: String,
			forceUpdate: Boolean = false
	) {
		binding.detailsLayout.visibility = View.GONE
		binding.progress.visibility = View.VISIBLE
		binding.errorLayout.root.visibility = View.GONE
		viewModel.searchWeatherDetails(name, forceUpdate)
	}
}