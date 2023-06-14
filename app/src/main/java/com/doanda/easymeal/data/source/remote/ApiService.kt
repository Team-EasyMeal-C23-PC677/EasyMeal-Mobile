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
    suspend fun register( // OK
        @Field("nama_profil") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): GeneralResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(// OK
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @PUT("user/{user_id}")
    suspend fun updateName(
        @Path("user_id") userId: Int,
        @Field("user_name") userName: String,
    ): GeneralResponse// OK

    // RECIPE
    @GET("recipe")
    suspend fun getAllRecipes(): ListRecipeResponse// OK

    @GET("recipes/{userId}")
    suspend fun getRecommendedRecipes(
        @Path("userId") userId: Int,
    ): ListRecipeResponse// OK

    @GET("recipe/{recipeId}")
    suspend fun getDetailRecipeById(
        @Path("recipeId") recipeId: Int,
    ): DetailRecipeResponse

    // FAVORITE
    @GET("favorite/{userId}")
    suspend fun getFavoriteRecipes(
        @Path("userId") userId: Int,
    ): ListFavoriteResponse// OK

    @POST("favorite/{userId}/{recipeId}")
    suspend fun addFavoriteRecipe(
        @Path("userId") userId: Int,
        @Path("recipeId") recipeId: Int,
    ): GeneralResponse// OK

    @DELETE("favorite/{userId}/{recipeId}")
    suspend fun deleteFavoriteRecipe(
        @Path("userId") userId: Int,
        @Path("recipeId") recipeId: Int,
    ): GeneralResponse

    // INGREDIENT & PANTRY
    @GET("ingredients") // OK
    suspend fun getAllIngredients(): ListIngredientResponse

    @GET("pantry/{userId}")
    suspend fun getPantryIngredients(
        @Path("userId") userId: Int,
    ): ListIngredientResponse// OK

    @POST("pantry/{userId}/{ingId}")
    suspend fun addPantryIngredient(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
    ): GeneralResponse// OK

    @DELETE("pantry/{userId}/{ingId}")
    suspend fun deletePantryIngredient(
        @Path("userId") userId: Int,
        @Path("ingId") ingId: Int,
    ): GeneralResponse// OK

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
