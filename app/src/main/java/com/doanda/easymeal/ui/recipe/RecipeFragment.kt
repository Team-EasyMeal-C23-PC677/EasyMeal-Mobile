package com.doanda.easymeal.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.doanda.easymeal.data.source.model.RecipeEntity
import com.doanda.easymeal.databinding.FragmentRecipeBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.Result

class RecipeFragment : Fragment() {

    private val binding by lazy { FragmentRecipeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup views
        getUser()
    }

    private fun getUser() {
        viewModel.getUser().observe(requireActivity()) { user ->
            if (user.isLogin) {
                setupData(user.userId)
            } else {
                goToLogin()
            }
        }
    }

    private fun setupData(userId: Int) {
        loadFavoriteData(userId)

        // TODO if pantry data enough for recommendation (Recommend button clicked)
        val isRecommendable = true
        if (isRecommendable) {
            viewModel.getRecommendedRecipes(userId).observe(requireActivity()) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        setupView(userId, result.data)
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Error loading recommended recipe"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            binding.tvEmptyPantry.visibility = View.VISIBLE
        }
    }

    private fun loadFavoriteData(userId: Int) {
        viewModel.getFavoriteRecipes(userId).observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Error loading favorite data"
                    Log.e(TAG, message)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(userId: Int, listRecipe: List<RecipeEntity>) {

        adapter = RecipeAdapter()
        binding.rvRecipe.apply {
            adapter = this.adapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false,
            )
        }

        adapter.submitList(listRecipe.toMutableList())
        adapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(recipe: RecipeEntity) {
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                startActivity(intent)
            }
            override fun onFavoriteClicked(recipe: RecipeEntity) {
                updateFavorite(userId, recipe.id, recipe.isFavorite)
            }
        })
    }

    private fun updateFavorite(userId: Int, recipeId: Int, oldIsFavorite: Boolean) {
        if (oldIsFavorite) {
            viewModel.deleteFavoriteRecipe(userId, recipeId).observe(requireActivity()) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        updateList()
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Favorite failed"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            viewModel.addFavoriteRecipe(userId, recipeId).observe(requireActivity()) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        updateList()
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Delete favorite failed"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateList() {
        viewModel.getRecommendedRecipesLocal().observe(requireActivity()) { listRecipe ->
            adapter.submitList(listRecipe.toMutableList())
        }
    }

    private fun goToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance() = RecipeFragment()
        private const val TAG = "RecipeFragment"
    }

    @Suppress("UNCHECKED_CAST")
    private fun dummySetupData() {
//        val dataRecipe = loadFromJsonListRecipeResponse(requireContext())
//        val dataFavorite = loadFromJsonListFavoriteResponse(requireContext())
//        // TODO implement retrofit
//        // in Result.Success
//        val listRecipe: List<RecipeEntity> = dataRecipe.listRecipe as List<RecipeEntity>
//        val listFavorite: List<RecipeEntity> = dataFavorite.listRecipe as List<RecipeEntity>
//        binding.rvRecipe.apply {
//            layoutManager = GridLayoutManager(
//                requireContext(),
//                2,
//                GridLayoutManager.VERTICAL,
//                false,
//            )
//            val listAdapter = RecipeAdapter(listRecipe, listFavorite)
//            listAdapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
//                override fun onItemClicked(recipe: RecipeEntity) {
//                    val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
//                    intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
//                    startActivity(intent)
//                }
//                override fun onFavoriteClicked(recipe: RecipeEntity) {
//                    // TODO HANDLE FAVORITE
//                }
//            })
//            adapter = listAdapter
//        }
    }
}