package com.doanda.easymeal.data.source.remote

import com.doanda.easymeal.data.response.auth.AuthResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import retrofit2.http.*

interface ApiService {

    // AUTHORIZATION
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @GET("recipe")
    suspend fun getAllRecipes(): ListRecipeResponse

    @GET("recipe")
    suspend fun getRecommendedRecipes(
        @Query("user_id") userId: String,
    ): ListRecipeResponse

    @GET("favorite_recipe")
    suspend fun getFavoriteRecipes(
        @Query("user_id") userId: String,
    ): ListRecipeResponse

    @GET("ingredient")
    suspend fun getAllIngredients(): ListIngredientResponse

    @GET("pantry_item")
    suspend fun getUserIngredients(
        @Query("userId") userId: String,
    ): ListIngredientResponse

    @GET("recipe/{id}")
    suspend fun getDetailRecipeById(
        @Path("recipeId") recipeId: String,
    ): DetailRecipeResponse

//    @POST("")
}
