package com.onkarnene.synerzip.weather.utilities

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.onkarnene.synerzip.weather.App

object PreferencesUtils {
	
	private const val KEY_UNIT = "key_unit"
	
	private fun getDefaultPreferences() = PreferenceManager.getDefaultSharedPreferences(App.getContext())
	
	fun saveUnit(unit: Unit) {
		getDefaultPreferences().edit {
			putString(KEY_UNIT, unit.getValue())
		}
	}
	
	fun getUnit(): Unit = Unit.findValue(getDefaultPreferences().getString(KEY_UNIT, null))
}