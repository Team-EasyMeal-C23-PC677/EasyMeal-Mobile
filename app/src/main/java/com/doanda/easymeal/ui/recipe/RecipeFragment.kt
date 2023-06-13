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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.data.source.model.RecipeEntity
import com.doanda.easymeal.databinding.FragmentRecipeBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.pantry.PantryFragment
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.Result
import observeOnce

class RecipeFragment : Fragment() {

    private val binding by lazy { FragmentRecipeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter: RecipeAdapter


    private var initPantryObserver: Observer<List<IngredientEntity>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        initPantryObserver?.let {
            viewModel.getPantryIngredientsLocal().removeObservers(viewLifecycleOwner)
        }
//        viewModel.getRecommendedRecipes(-1).removeObservers(viewLifecycleOwner)
    }

    private fun getUser() {
        viewModel.getUser().observeOnce(viewLifecycleOwner) { user ->
            if (user.userId != -1) {
                setupData(user.userId)
                setupAction(user.userId)
            }
        }
    }

    private fun setupAction(userId: Int) {
        adapter = RecipeAdapter()
        binding.rvRecipe.apply {
            adapter = this@RecipeFragment.adapter
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false,
            )
        }
        adapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(recipe: RecipeEntity) {
                Toast.makeText(requireContext(), "Recipe -> RecipeDetail", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                startActivity(intent)
            }
            override fun onFavoriteClicked(recipe: RecipeEntity) {
                updateFavorite(userId, recipe.id, recipe.isFavorite)
            }
        })
    }

    private fun setupData(userId: Int) {
        if (initPantryObserver == null) {
            initPantryObserver = Observer { listIng ->
//                val isPantryUpdated = arguments?.getBoolean(PantryFragment.EXTRA_IS_PANTRY_UPDATED) ?: false
//                val notEmpty = listIng.isNotEmpty()
//                handlePantryStatus(isPantryUpdated)
//                if (notEmpty && isPantryUpdated)
//                    getRecommendedRecipes(userId)
                val isPantryUpdated = arguments?.getBoolean(PantryFragment.EXTRA_IS_PANTRY_UPDATED) ?: false
                val notEmpty = listIng.isNotEmpty()
                handlePantryStatus(notEmpty)
                if (notEmpty)
                    getRecommendedRecipes(userId)
            }
            viewModel.getPantryIngredientsLocal().observeOnce(viewLifecycleOwner, initPantryObserver!!)
        }
//        val isPantryUpdated = arguments?.getBoolean(PantryFragment.EXTRA_IS_PANTRY_UPDATED) ?: false
//        if (isPantryUpdated) {
//            getRecommendedRecipes(userId)
//        } else {
//            getRecommendedRecipesLocal()
//        }
        getRecommendedRecipesLocal()
    }

    private fun getRecommendedRecipesLocal() {
        viewModel.getRecommendedRecipesLocal().observeOnce(viewLifecycleOwner) { listRecipe ->
            if (listRecipe != null) {
                updateList(listRecipe)
            }
        }
    }

    private fun getRecommendedRecipes(userId: Int) {
        viewModel.getRecommendedRecipes(userId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Recommended Success", Toast.LENGTH_SHORT).show()
                    updateList(result.data)
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
    }

    private fun updateList(listRecipe: List<RecipeEntity>) {
        adapter.submitList(listRecipe.toMutableList())
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

    private fun handlePantryStatus(pantryNotEmpty: Boolean) {
        with (binding) {
            rvRecipe.visibility = if (pantryNotEmpty) View.VISIBLE else View.GONE
            tvRecipes.visibility = if (pantryNotEmpty) View.VISIBLE else View.GONE

            ivIllustEmptyPantry.visibility = if (pantryNotEmpty) View.GONE else View.VISIBLE
            tvEmptyPantry.visibility = if (pantryNotEmpty) View.GONE else View.VISIBLE
            tvEmptyPantryDesc.visibility = if (pantryNotEmpty) View.GONE else View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
//        fun newInstance() = RecipeFragment()
        private const val TAG = "RecipeFragment"
    }
}