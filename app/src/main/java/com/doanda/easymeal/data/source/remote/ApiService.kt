package com.doanda.easymeal.data.source.remote

import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.login.LoginResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.favorite.ListFavoriteResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListResponse
import retrofit2.http.*

interface ApiService {

    // USER
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("nama_profil") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): GeneralResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @PUT("user/{user_id}")
    suspend fun updateName(
        @Path("user_id") userId: Int,
        @Field("user_name") userName: String,
    ): GeneralResponse

    // RECIPE
    @GET("recipe")
    suspend fun getAllRecipes(): ListRecipeResponse

    @GET("recipes/{userId}")
    suspend fun getRecommendedRecipes(
        @Path("userId") userId: Int,
    ): ListRecipeResponse

    @GET("recipe/{recipeId}")
    suspend fun getDetailRecipeById(
        @Path("recipeId") recipeId: Int,
    ): DetailRecipeResponse

    // FAVORITE
    @GET("favorite/{userId}")
    suspend fun getFavoriteRecipes(
        @Path("userId") userId: Int,
    ): ListFavoriteResponse

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
    @GET("ingredients")
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

    @FormUrlEncoded
    @POST("shopping-list/{userId}/{ingId}")
    suspend fun addShoppingListItem(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
        @Field("qty") qty: Float,
        @Field("unit") unit: String,
    ): GeneralResponse

    @DELETE("shopping-list/{userId}/{ingId}")
    suspend fun deleteShoppingListItem(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
    ): GeneralResponse
}
