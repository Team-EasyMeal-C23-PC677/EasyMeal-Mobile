package com.doanda.easymeal.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.source.model.RecipeEntity
import com.doanda.easymeal.databinding.FragmentFavoriteBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.Result
import observeOnce

class FavoriteFragment : Fragment() {

    private val binding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FavoriteViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupData()
        getUser()
    }

    private fun setupView() {
        adapter = FavoriteAdapter()
        binding.rvFavorite.apply {
            adapter = this@FavoriteFragment.adapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false,
            )
        }
    }

    private fun getUser() {
        viewModel.getUser().observeOnce(viewLifecycleOwner) { user ->
            if (user.userId != -1) {
                setupAction(user.userId)
            }
        }
    }

    private fun setupData() {
        viewModel.getFavoriteRecipesLocal().observe(viewLifecycleOwner) { listRecipe ->
            if (listRecipe != null) {
                handleEmptyData(listRecipe.isNotEmpty())
                updateList(listRecipe)
            }
        }
    }

    private fun handleEmptyData(notEmpty: Boolean) {
        with(binding) {
            rvFavorite.visibility = if (notEmpty) View.VISIBLE else View.GONE

            ivIllustEmptyFavorite.visibility = if (notEmpty) View.GONE else View.VISIBLE
            tvEmptyFavorite.visibility = if (notEmpty) View.GONE else View.VISIBLE
            tvEmptyFavoriteDesc.visibility = if (notEmpty) View.GONE else View.VISIBLE
        }
    }

    private fun updateList(listRecipe: List<RecipeEntity>) {
        adapter.submitList(listRecipe.toMutableList())
    }

    private fun setupAction(userId: Int) {
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
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
            viewModel.deleteFavoriteRecipe(userId, recipeId).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
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
            viewModel.addFavoriteRecipe(userId, recipeId).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "FavoriteFragment"
    }
}