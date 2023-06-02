package com.doanda.easymeal.utils

import android.content.Context
import java.io.IOException

fun getJsonStringFromResource(context: Context, resource: Int): String? {
    val jsonString: String
    try {
        jsonString = context.resources.openRawResource(resource).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}


