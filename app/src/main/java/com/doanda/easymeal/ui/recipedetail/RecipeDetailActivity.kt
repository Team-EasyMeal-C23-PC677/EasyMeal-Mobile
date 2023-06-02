package com.doanda.easymeal.ui.recipedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
    }
}