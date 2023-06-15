package com.doanda.easymeal.ui.pantry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.R
import com.doanda.easymeal.data.source.model.CategoryEntity
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.databinding.FragmentPantryBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.utils.Result
import observeOnce

class PantryFragment : Fragment() {

    private val binding by lazy { FragmentPantryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<PantryViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter: PantryCategoryAdapter
    private var isPantryUpdated: Boolean = false
    private var oldListIng: List<IngredientEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()
        setupView()
        setupData()
    }

    private fun getUser() {
        viewModel.getUser().observeOnce(viewLifecycleOwner) { user ->
            if (user.userId != -1) {
                setupAction(user.userId)
            }
        }
    }

    private fun setupView() {
        // SETUP RECYCLER VIEW
        adapter = PantryCategoryAdapter()
        binding.rvPantryCategory.apply {
            adapter = this@PantryFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupData() {
        viewModel.getAllIngredientsLocal().observeOnce(viewLifecycleOwner) { listIng ->
            oldListIng = listIng
        }
        viewModel.getAllIngredientsLocal().observe(viewLifecycleOwner) { listIng ->
            if (listIng.isNotEmpty()) {
                updateList(listIng)
            } else {
                loadAllIngredients()
            }
        }
    }

    private fun loadAllIngredients() {
        viewModel.getAllIngredients().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Failed to load ingredients"
                    Log.e(TAG, result.error + message)
//                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAction(userId: Int) {
        // SETUP ACTION
        adapter.setOnItemClickCallback(object : PantryCategoryAdapter.OnItemClickCallback {
            override fun onChildItemCheckedChange(
                ingredient: IngredientEntity,
            ) {
                updatePantry(userId, ingredient.ingId, ingredient.isHave)
            }
        })
    }

    private fun updateList(listIng: List<IngredientEntity>) {
        // UPDATE LIST
        binding.btnPantryToRecipe.setOnClickListener { view ->
            val mBundle = Bundle()
            val isPantryUpdated = listIng.map { it.isHave } == oldListIng.map { it.isHave } && listIng.isNotEmpty()
            mBundle.putBoolean(EXTRA_IS_PANTRY_UPDATED, isPantryUpdated)
            view.findNavController().navigate(R.id.action_navigation_pantry_to_navigation_recipe, mBundle)
        }
        val listCategory = groupByCategory(listIng)
        adapter.submitList(listCategory.toMutableList())
    }

    private fun updatePantry(userId: Int, ingId: Int, oldIsHave: Boolean) {
        if (oldIsHave) {
            viewModel.deletePantryIngredient(userId, ingId).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Failed to delete ingredient"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            viewModel.addPantryIngredient(userId, ingId).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Failed to add ingredient"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun groupByCategory(listIng: List<IngredientEntity>): List<CategoryEntity> {
        val listCategoryMap: Map<String, List<IngredientEntity>> = listIng.groupBy { it.categoryName }
        val listCategory: List<CategoryEntity> = listCategoryMap.map {
            CategoryEntity(
                categoryName = it.key,
                listIngredient = it.value,
                isExpanded = true
            )
        }
        return listCategory
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
//        fun newInstance() = PantryFragment()
        private const val TAG = "PantryFragment"
        const val EXTRA_IS_PANTRY_UPDATED = "extra_is_pantry_updated"
    }
}