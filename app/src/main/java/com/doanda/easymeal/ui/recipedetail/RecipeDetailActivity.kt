package com.doanda.easymeal.ui.recipedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.detailrecipe.Recipe
import com.doanda.easymeal.databinding.ActivityRecipeDetailBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.utils.loadFromJsonDetailRecipeResponse
import com.google.android.material.tabs.TabLayoutMediator
import convertMinuteToHourMinute

class RecipeDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRecipeDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeDetailViewModel> {ViewModelFactory.getInstance(this)}
    private var recipeId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        getUser()
    }

    private fun getUser() {
//        viewModel.getUser().observe(this) { user ->
//            if (user.isLogin) {
//                setupData(user.userId)
//            }
//        }
        viewModel.getLoginStatus().observe(this) { isLogin ->
            if (isLogin) {
                viewModel.getUser().observe(this) { user ->
                    setupData(user.userId)
                }
            }
        }
    }

    private fun setupData(userId: Int) {
        // TODO observe getRecipeDetail, if Success then
        val dataRecipeDetail = loadFromJsonDetailRecipeResponse(this@RecipeDetailActivity )


        recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 1)
        if (recipeId != null) {
            showLoading(false)
            val recipe = dataRecipeDetail.recipe
            if (recipe != null) {
                setupView(recipe)
            }
        }
    }

    private fun checkIfRecipeBookmarked(recipeId: Int?) {
//        TODO("Not yet implemented")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView(recipe: Recipe) {
        val adapter = SectionsPagerAdapter(this)
        adapter.recipe = recipe
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tlDetailTabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }

        checkIfRecipeBookmarked(recipe.id)
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

        binding.fabDetailBack.setOnClickListener {
            // TODO handle back
        }

        binding.fabDetailFavorite.setOnClickListener {
            // TODO handle favorite button
        }
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