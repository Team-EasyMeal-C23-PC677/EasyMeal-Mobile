package com.doanda.easymeal.utils

import android.content.Context
import android.util.Log
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.ListRecipeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

fun loadFromJsonListRecipeResponse(
    context: Context,
): ListRecipeResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.recipe_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<ListRecipeResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}


