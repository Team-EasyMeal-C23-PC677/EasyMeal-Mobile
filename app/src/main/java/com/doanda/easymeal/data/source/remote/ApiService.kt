package com.doanda.easymeal.data.source.remote

import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListResponse
import retrofit2.http.*

interface ApiService {

    // USER
    @POST("user/register")
    suspend fun register(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
    ): GeneralResponse

    @POST("user/login")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): LoginResponse

    @PUT("user/{id}")
    suspend fun updateName(
        @Path("user_id") userId: Int,
        @Query("user_name") userName: String,
    ): GeneralResponse

    // RECIPE
    @GET("recipe")
    suspend fun getAllRecipes(): ListRecipeResponse

    @GET("recipe/{userId}")
    suspend fun getRecommendedRecipes(
        @Path("user_id") userId: Int,
    ): ListRecipeResponse

    @GET("recipe/{recipeId}")
    suspend fun getDetailRecipeById(
        @Path("recipeId") recipeId: Int,
    ): DetailRecipeResponse

    // FAVORITE
    @GET("favorite/{userId}")
    suspend fun getFavoriteRecipes(
        @Path("userId") userId: Int,
    ): ListRecipeResponse

    @POST("favorite/{userId}/{recipeId}")
    suspend fun addFavoriteRecipe(
        @Path("userId") userId: Int,
        @Path("recipeId") recipeId: Int,
    ): GeneralResponse

    @DELETE("favorite/{userId}/{recipeId}")
    suspend fun deleteFavoriteRecipe(
        @Path("userId") userId: Int,
        @Path("recipeId") recipeId: Int,
    ): GeneralResponse

    // INGREDIENT & PANTRY
    @GET("ingredient")
    suspend fun getAllIngredients(): ListIngredientResponse

    @GET("pantry/{userId}")
    suspend fun getPantryIngredients(
        @Path("userId") userId: Int,
    ): ListIngredientResponse

    @POST("pantry/{userId}/{ingId}")
    suspend fun addPantryIngredient(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
    ): GeneralResponse

    @DELETE("pantry/{userId}/{ingId}")
    suspend fun deletePantryIngredient(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
    ): GeneralResponse

    // SHOPPING LIST
    @GET("shopping-list/{userId}")
    suspend fun getShoppingList(
        @Path("userId") userId: Int,
    ): ShoppingListResponse

    @POST("shopping-list/{userId}/{ingId}")
    suspend fun addShoppingListItem(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
        @Query("qty") qty: Float,
        @Query("unit") unit: String,
    ): GeneralResponse

    @DELETE("shopping-list/{userId}/{ingId}")
    suspend fun deleteShoppingListItem(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
    ): GeneralResponse
}
