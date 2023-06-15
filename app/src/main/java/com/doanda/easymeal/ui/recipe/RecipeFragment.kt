package com.doanda.easymeal.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.doanda.easymeal.R
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
    }

    private fun getUser() {
        viewModel.getUser().observeOnce(viewLifecycleOwner) { user ->
            if (user.userId != -1) {
                setupData(user.userId)
                setupAction(user.userId)
            }
        }
    }

    private fun setupData(userId: Int) {
        getRecommendedRecipesLocal()
        if (initPantryObserver == null) {
            initPantryObserver = Observer { listIng ->
                val isPantryUpdated = arguments?.getBoolean(PantryFragment.EXTRA_IS_PANTRY_UPDATED) ?: false
                val notEmpty = listIng.isNotEmpty()
                handlePantryStatus(notEmpty)
                if (notEmpty)
                    getRecommendedRecipes(userId)
            }
            viewModel.getPantryIngredientsLocal().observeOnce(viewLifecycleOwner, initPantryObserver!!)
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
//                Toast.makeText(requireContext(), "Recipe -> RecipeDetail", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                startActivity(intent)
            }
            override fun onFavoriteClicked(recipe: RecipeEntity) {
                updateFavorite(userId, recipe.id, recipe.isFavorite)
            }
        })
        binding.chTime.setOnClickListener {
            val title = getString(R.string.maximum_time)
            val minutes = getString(R.string.minutes)
            val hour  = getString(R.string.hour)
            val hours = getString(R.string.hours)
            val options = arrayOf("15 $minutes", "30 $minutes", "1 $hour", "1 $hour 30 $minutes", "2 $hours")
            val values = arrayOf(15, 30, 60, 90, 120)
            if (binding.chTime.isChecked) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            options
                        )
                    ) { _, which ->
                        val selectedOption = values[which]
                        viewModel.setFilterTime(selectedOption)
                    }
                    .setOnCancelListener {
                        binding.chTime.isChecked = false
                    }
                    .create()
                dialog.show()
            } else {
                viewModel.setFilterTime(null)
            }
        }
        binding.chServing.setOnCheckedChangeListener { _, isChecked ->
            val title = getString(R.string.number_of_servings)
            val servings = getString(R.string.servings)
            val serving = getString(R.string.serving)
            val options = arrayOf("1 $serving", "2-3 $servings", "3-4 $servings", "> 5 $servings")
            val values = arrayOf(0..1, 2..3, 4..5, 5..100)
            if (binding.chServing.isChecked) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            options
                        )
                    ) { _, which ->
                        val selectedOption = values[which]
                        viewModel.setFilterServing(selectedOption)
                    }
                    .setOnCancelListener {
                        binding.chServing.isChecked = false
                    }
                    .create()
                dialog.show()
            } else {
                viewModel.setFilterServing(null)
            }
        }
        binding.chFavorite.setOnCheckedChangeListener { _, isChecked ->
            val title = getString(R.string.recipe_in_favorite)
            val options = arrayOf(getString(R.string.yes), getString(R.string.no))
            val values = arrayOf(true, false)
            if (binding.chFavorite.isChecked) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            options
                        )
                    ) { _, which ->
                        val selectedOption = values[which]
                        viewModel.setFilterFavorite(selectedOption)
                    }
                    .setOnCancelListener {
                        binding.chFavorite.isChecked = false
                    }
                    .create()
                dialog.show()
            } else {
                viewModel.setFilterFavorite(null)
            }
        }
        binding.svSearch.setOnCloseListener {
            viewModel.setFilterSearch(null)
            false
        }
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setFilterSearch(query)
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                viewModel.setFilterSearch(query)
                return true
            }
        })
    }

    data class RecipeFilter(
        var query: String? = null,
        var maxTotalTime: Int? = null,
        var servings: IntRange? = null,
        var isFavorite: Boolean? = null,
    )

    private fun getRecommendedRecipesLocal() {
        viewModel.getRecommendedRecipesLocal().observe(viewLifecycleOwner) { listRecipe ->
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
//                    Toast.makeText(requireContext(), "Recommended Success", Toast.LENGTH_SHORT).show()
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
        viewModel.recipeFilter.observe(viewLifecycleOwner) { filter ->
            var filteredList = listRecipe
            if (filter != null) {
                filteredList = if (filter.maxTotalTime != null || filter.servings != null || filter.isFavorite != null || filter.query != null) {
                    filterRecipes(listRecipe, filter)
                } else {
                    listRecipe
                }
            }
            adapter.submitList(filteredList.toMutableList())
        }
    }

    private fun filterRecipes(listRecipe: List<RecipeEntity>, filter: RecipeFilter): List<RecipeEntity> {
        var filteredRecipes = listRecipe
        if (filter.maxTotalTime != null)
            filteredRecipes = filteredRecipes.filter { it.totalTime <= filter.maxTotalTime!! }
        if (filter.servings != null)
            filteredRecipes = filteredRecipes.filter { filter.servings!!.contains(it.serving) }
        if (filter.isFavorite != null)
            filteredRecipes = filteredRecipes.filter { it.isFavorite == filter.isFavorite }
        if (filter.query != null)
            filteredRecipes = filteredRecipes.filter { (it.title + it.description).contains(filter.query!!) }
        return filteredRecipes
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
            chFavorite.visibility = if (pantryNotEmpty) View.VISIBLE else View.GONE
            chServing.visibility = if (pantryNotEmpty) View.VISIBLE else View.GONE
            chTime.visibility = if (pantryNotEmpty) View.VISIBLE else View.GONE
            svSearch.visibility = if (pantryNotEmpty) View.VISIBLE else View.GONE

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