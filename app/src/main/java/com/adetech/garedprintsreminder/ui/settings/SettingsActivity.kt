package com.adetech.garedprintsreminder.ui.settings

import android.content.Context
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.*
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.adetech.garedprintsreminder.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(fragmentManager.findFragmentById(android.R.id.content) == null){
            fragmentManager.beginTransaction().add(android.R.id.content, SettingsFragment()).commit()
        }
    }
    class SettingsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref)

            SettingsFragment.bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"))
            SettingsFragment.bindPreferenceSummaryToValue(findPreference("notifications_alarm_time"))
            SettingsFragment.bindPreferenceSummaryToValue(findPreference("number_of_orders"))
            SettingsFragment.bindPreferenceSummaryToValue(findPreference("price_of_job"))
        }

        companion object {

            /**
             * A pref value change listener that updates the pref's summary
             * to reflect its new value.
             */
            private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
                val stringValue = value.toString()

                if (preference is ListPreference) {
                    // For list preferences, look up the correct display value in
                    // the pref's 'entries' list.
                    val index = preference.findIndexOfValue(stringValue)

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            if (index >= 0)
                                preference.entries[index]
                            else
                                null)

                } else if (preference is RingtonePreference) {
                    // For ringtone preferences, look up the correct display value
                    // using RingtoneManager.
                    if (TextUtils.isEmpty(stringValue)) {
                        // Empty values correspond to 'silent' (no ringtone).
                        preference.setSummary(R.string.pref_ringtone_silent)

                    } else {
                        val ringtone = RingtoneManager.getRingtone(
                                preference.getContext(), Uri.parse(stringValue))

                        if (ringtone == null) {
                            // Clear the summary if there was a lookup error.
                            preference.setSummary(null)
                        } else {
                            // Set the summary to reflect the new ringtone display
                            // name.
                            val name = ringtone.getTitle(preference.getContext())
                            preference.setSummary(name)
                        }
                    }

                } else {
                    // For all other preferences, set the summary to the value's
                    // simple string representation.
                    preference.summary = stringValue
                }
                true
            }

            /**
             * Helper method to determine if the device has an extra-large screen. For
             * example, 10" tablets are extra-large.
             */
            private fun isXLargeTablet(context: Context): Boolean {
                return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
            }

            /**
             * Binds a pref's summary to its value. More specifically, when the
             * pref's value is changed, its summary (line of text below the
             * pref title) is updated to reflect the value. The summary is also
             * immediately updated upon calling this method. The exact display format is
             * dependent on the type of pref.

             * @see .sBindPreferenceSummaryToValueListener
             */
            private fun bindPreferenceSummaryToValue(preference: Preference) {
                // Set the listener to watch for value changes.
                preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

                // Trigger the listener immediately with the pref's
                // current value.
                sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                        PreferenceManager
                                .getDefaultSharedPreferences(preference.context)
                                .getString(preference.key, ""))
            }
        }
    }


}


