package com.doanda.easymeal.ui.recipedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContentProviderCompat.requireContext
import com.doanda.easymeal.R
import com.doanda.easymeal.databinding.ActivityRecipeDetailBinding
import com.doanda.easymeal.utils.loadFromJsonDetailRecipeResponse

class RecipeDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecipeDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeDetailViewModel>()
//    private val adapter by lazy { StoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupData()
    }

    private fun setupData() {

        val data = loadFromJsonDetailRecipeResponse(this@RecipeDetailActivity )



        // TODO get extra
    }

    private fun setupView() {

        // TODO wrap in observe viewmodel
        val sectionsPagerAdapter = SectionsPagerAdapter(this@RecipeDetailActivity)

    }

    companion object {
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
        const val TAG = "RecipeDetailActivity"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_ingredients,
            R.string.tab_steps,
        )
    }
}