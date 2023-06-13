package com.doanda.easymeal.data.source.remote

import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.favorite.ListFavoriteResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListResponse
import com.doanda.easymeal.utils.*

class DummyApiService(
//    private val context: Context
) {

    // USER
    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }

    suspend fun login(
        email: String,
        password: String,
    ): LoginResponse {
        return loadFromJsonLoginResponse(MainApplication.applicationContext())
    }

    suspend fun updateName(
        userId: Int,
        userName: String,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }

    // RECIPE
    suspend fun getAllRecipes(): ListRecipeResponse {
        return loadFromJsonListRecipeResponse(MainApplication.applicationContext())
    }

    suspend fun getRecommendedRecipes(
        userId: Int,
    ): ListRecipeResponse {
        return loadFromJsonListRecommendedRecipeResponse(MainApplication.applicationContext())
    }

    suspend fun getDetailRecipeById(
        recipeId: Int,
    ): DetailRecipeResponse {
        return loadFromJsonDetailRecipeResponse(MainApplication.applicationContext())
    }

    // FAVORITE
    suspend fun getFavoriteRecipes(
        userId: Int
    ): ListFavoriteResponse {
        return loadFromJsonListFavoriteResponse(MainApplication.applicationContext())
    }

    suspend fun addFavoriteRecipe(
        userId: Int,
        recipeId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }

    suspend fun deleteFavoriteRecipe(
        userId: Int,
        recipeId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }



    // INGREDIENT & PANTRY

    suspend fun getAllIngredients(): ListIngredientResponse {
        return loadFromJsonListIngredientResponse(MainApplication.applicationContext())
    }

    suspend fun getPantryIngredients(
        userId: Int,
    ): ListIngredientResponse {
        return loadFromJsonListUserIngredientResponse(MainApplication.applicationContext())
    }

    suspend fun addPantryIngredient(
        userId: Int,
        ingId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }

    suspend fun deletePantryIngredient(
        userId: Int,
        ingId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }

    // SHOPPING LIST
    suspend fun getShoppingList(
         userId: Int,
    ): ShoppingListResponse {
        return loadFromJsonShoppingListResponse(MainApplication.applicationContext())
    }

    suspend fun addShoppingListItem(
        userId: Int,
        ingId: Int,
        qty: Float,
        unit: String,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }

    suspend fun deleteShoppingListItem(
        userId: Int,
        ingId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(MainApplication.applicationContext())
    }


    companion object {
//        @Volatile
//        private var INSTANCE: DummyApiService? = null
//        fun getInstance(context: Context): DummyApiService {
//            return INSTANCE ?: synchronized(this) {
//                val instance = DummyApiService(context)
//                INSTANCE = instance
//                instance
//            }
//        }

    }
}