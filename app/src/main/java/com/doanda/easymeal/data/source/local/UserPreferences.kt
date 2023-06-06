package com.doanda.easymeal.data.source.local

import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

class UserPreferences private constructor(
    private val dataStore: DataStore<Preferences>
) {



}