package com.doanda.easymeal.ui.recipedetail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.detailrecipe.Recipe
import com.doanda.easymeal.databinding.ActivityRecipeDetailBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.utils.Result
import com.google.android.material.tabs.TabLayoutMediator
import convertMinuteToHourMinute
import observeOnce

class RecipeDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecipeDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeDetailViewModel> {ViewModelFactory.getInstance(this)}

    private lateinit var adapter: SectionsPagerAdapter
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getUser()
    }

    private fun getUser() {
        val recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, -1)
        viewModel.getUser().observeOnce(this) { user ->
            if (user.userId != -1 && recipeId != -1) {
                setupData(user.userId, recipeId)
            }
        }
    }
    private fun setupData(userId: Int, recipeId: Int) {

        viewModel.getDetailRecipeById(recipeId).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    val data = result.data.recipe
                    setupView(userId, data)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Failed loading recipe detail"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(userId: Int, recipe: Recipe) {
        viewModel.getIngredientsByIds(recipe.listIngredient.map { it.id }).observe(this) { listIng ->
            if (listIng != null) {
                val listMissingIngs = listIng.filter { !it.isHave }.map { it.ingName }
                if (listMissingIngs.isEmpty()) {
                    binding.tvDetailStatus.text = getString(R.string.have_all_ingredients)
                    binding.tvDetailStatus.setTextColor(getColor(R.color.primary))
                } else {
                    binding.tvDetailStatus.text = getString(R.string.ingredients_missing).format(listMissingIngs.joinToString(", "))
                    binding.tvDetailStatus.setTextColor(getColor(R.color.red_theme))
                }
            }
        }

        viewModel.isRecipeFavoriteLocal(recipe.id).observe(this) { isFavorite ->
            if (isFavorite != null) {
                this.isFavorite = isFavorite
                val iconColor = if (isFavorite) ContextCompat.getColor(this, R.color.red_theme) else ContextCompat.getColor(this, R.color.grey)
                val iconColorStateList = ColorStateList.valueOf(iconColor)
                binding.fabDetailFavorite.imageTintList = iconColorStateList
            }
        }

        adapter = SectionsPagerAdapter(this)
        adapter.recipe = recipe
        adapter.userId = userId

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tlDetailTabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        binding.tvDetailTitle.text = recipe.title
        binding.tvDetailDescription.text = recipe.description

        val (hours, minutes) = convertMinuteToHourMinute(recipe.totalTime)
        val timeText = mutableListOf<String>()
        if (hours > 0)
            timeText.add(getString(R.string.hour_format).format(hours))
        if (minutes > 0)
            timeText.add(getString(R.string.minute_format).format(minutes))
        binding.tvDetailTime.text = timeText.joinToString(" ")

        binding.tvDetailServing.text = getString(R.string.serving_format).format(recipe.serving)

        Glide.with(this)
            .load(recipe.imgUrl)
            .into(binding.ivDetailImage)

        binding.fabDetailFavorite.setOnClickListener {
            updateFavorite(userId, recipe.id, this.isFavorite)
        }
        binding.fabDetailBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateFavorite(userId: Int, recipeId: Int, oldIsFavorite: Boolean) {
        if (oldIsFavorite) {
            viewModel.deleteFavoriteRecipe(userId, recipeId).observe(this) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Delete favorite failed"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            viewModel.addFavoriteRecipe(userId, recipeId).observe(this) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Add favorite failed"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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