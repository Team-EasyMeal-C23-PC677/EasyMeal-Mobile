package com.doanda.easymeal.data.source.remote

import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
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
        return loadFromJsonGeneralResponse(Application.appContext)
    }

    suspend fun login(
        email: String,
        password: String,
    ): LoginResponse {
        return loadFromJsonLoginResponse(Application.appContext)
    }

    suspend fun updateName(
        userId: Int,
        userName: String,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
    }

    // RECIPE
    suspend fun getAllRecipes(): ListRecipeResponse {
        return loadFromJsonListRecipeResponse(Application.appContext)
    }

    suspend fun getRecommendedRecipes(
        userId: Int,
    ): ListRecipeResponse {
        return loadFromJsonListRecommendedRecipeResponse(Application.appContext)
    }

    suspend fun getDetailRecipeById(
        recipeId: Int,
    ): DetailRecipeResponse {
        return loadFromJsonDetailRecipeResponse(Application.appContext)
    }

    // FAVORITE
    suspend fun getFavoriteRecipes(
        userId: Int
    ): ListRecipeResponse {
        return loadFromJsonListFavoriteResponse(Application.appContext)
    }

    suspend fun addFavoriteRecipe(
        userId: Int,
        recipeId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
    }

    suspend fun deleteFavoriteRecipe(
        userId: Int,
        recipeId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
    }



    // INGREDIENT & PANTRY

    suspend fun getAllIngredients(): ListIngredientResponse {
        return loadFromJsonListIngredientResponse(Application.appContext)
    }

    suspend fun getUserIngredients(
        userId: Int,
    ): ListIngredientResponse {
        return loadFromJsonListUserIngredientResponse(Application.appContext)
    }

    suspend fun addPantryIngredient(
        userId: Int,
        ingId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
    }

    suspend fun deletePantryIngredient(
        userId: Int,
        ingId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
    }

    // SHOPPING LIST
    suspend fun getShoppingList(
         userId: Int,
    ): ShoppingListResponse {
        return loadFromJsonShoppingListResponse(Application.appContext)
    }

    suspend fun addShoppingListItem(
        userId: Int,
        ingId: Int,
        qty: Float,
        unit: String,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
    }

    suspend fun deleteShoppingListItem(
        userId: Int,
        ingId: Int,
    ): GeneralResponse {
        return loadFromJsonGeneralResponse(Application.appContext)
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