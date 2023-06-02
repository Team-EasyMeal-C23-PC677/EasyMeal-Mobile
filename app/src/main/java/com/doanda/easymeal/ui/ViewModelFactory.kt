package com.doanda.easymeal.ui

import androidx.lifecycle.ViewModelProvider
import com.doanda.easymeal.data.source.local.UserPreference

class ViewModelFactory(private val userPref: UserPreference)
    : ViewModelProvider.NewInstanceFactory() {

}