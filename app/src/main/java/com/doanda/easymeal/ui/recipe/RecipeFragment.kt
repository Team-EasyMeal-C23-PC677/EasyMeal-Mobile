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
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.databinding.FragmentRecipeBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.Result
import com.doanda.easymeal.utils.loadFromJsonListFavoriteResponse
import com.doanda.easymeal.utils.loadFromJsonListRecipeResponse


class RecipeFragment : Fragment() {

    private val binding by lazy { FragmentRecipeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeViewModel>{ ViewModelFactory.getInstance(requireContext()) }
//    private val activityViewModel by activityViewModels<MainViewModel>()

    private lateinit var listRecipe: List<ListRecipeItem>
    private lateinit var listFavorite: List<ListRecipeItem>

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
//        dummySetupData()
        viewModel.getAllRecipes().observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    val data = result.data.listRecipe
                    if (data != null) {
                        val dataSafe = data.filterNotNull()
                        listRecipe = dataSafe
                        setupView()
                    }
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Error loading all recipe"
                    Log.e(TAG, message)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getFavoriteRecipes(userId).observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    val data = result.data.listRecipe
                    if (data != null) {
                        val dataSafe = data.filterNotNull()
                        listFavorite = dataSafe
                    }
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Error loading all recipe"
                    Log.e(TAG, message)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView() {
        binding.rvRecipe.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false,
            )
            val listAdapter = RecipeAdapter(listRecipe, listFavorite)
            listAdapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
                override fun onItemClicked(recipe: ListRecipeItem) {
                    val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                    intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                    startActivity(intent)
                }
                override fun onFavoriteClicked(recipe: ListRecipeItem) {
                    // TODO HANDLE FAVORITE
                }
            })
            adapter = listAdapter
        }
    }

    private fun goToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    @Suppress("UNCHECKED_CAST")
    private fun dummySetupData() {
        val dataRecipe = loadFromJsonListRecipeResponse(requireContext())
        val dataFavorite = loadFromJsonListFavoriteResponse(requireContext())
        // TODO implement retrofit
        // in Result.Success
        val listRecipe: List<ListRecipeItem> = dataRecipe.listRecipe as List<ListRecipeItem>
        val listFavorite: List<ListRecipeItem> = dataFavorite.listRecipe as List<ListRecipeItem>
        binding.rvRecipe.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false,
            )
            val listAdapter = RecipeAdapter(listRecipe, listFavorite)
            listAdapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
                override fun onItemClicked(recipe: ListRecipeItem) {
                    val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                    intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                    startActivity(intent)
                }
                override fun onFavoriteClicked(recipe: ListRecipeItem) {
                    // TODO HANDLE FAVORITE
                }
            })
            adapter = listAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance() = RecipeFragment()
        private const val TAG = "RecipeFragment"
    }
}