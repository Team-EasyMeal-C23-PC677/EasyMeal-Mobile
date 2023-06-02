package com.doanda.easymeal.utils

import android.content.Context
import com.doanda.easymeal.R
import java.io.IOException

fun getJsonDataFromResource(context: Context, resource: Int): String? {
    val jsonString: String
    try {
        jsonString = context.resources.openRawResource(resource).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

