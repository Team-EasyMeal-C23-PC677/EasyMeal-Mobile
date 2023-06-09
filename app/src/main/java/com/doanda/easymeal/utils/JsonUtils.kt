package com.doanda.easymeal.utils

import android.content.Context
import android.util.Log
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListResponse
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

fun loadFromJsonListRecommendedRecipeResponse(
    context: Context,
): ListRecipeResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.recommended_recipe_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<ListRecipeResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonListFavoriteResponse(
    context: Context,
): ListRecipeResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.favorite_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<ListRecipeResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonListIngredientResponse(
    context: Context,
): ListIngredientResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.ingredient_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<ListIngredientResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonListUserIngredientResponse(
    context: Context,
): ListIngredientResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.user_ingredient_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<ListIngredientResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonDetailRecipeResponse(
    context: Context,
): DetailRecipeResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.detail_recipe_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<DetailRecipeResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonShoppingListResponse(
    context: Context
): ShoppingListResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.shopping_list_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<ShoppingListResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonLoginResponse(
    context: Context
): LoginResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.login_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<LoginResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonGeneralResponse(
    context: Context
): GeneralResponse {
    val jsonFileString = getJsonStringFromResource(context, R.raw.general_response)
    if (jsonFileString != null) {
        Log.i("JSON", jsonFileString)
    } else {
        Log.e("JSON", "FAILED TO LOAD JSON")
    }

    val gson = Gson()
    val result = object : TypeToken<GeneralResponse>() {}.type
    return gson.fromJson(jsonFileString, result)
}

fun loadFromJsonDetectionResponse(
    context: Context
) {

}
