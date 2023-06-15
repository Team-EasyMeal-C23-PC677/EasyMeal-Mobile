package com.doanda.easymeal.ui.recipedetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.response.detailrecipe.Recipe
import com.doanda.easymeal.data.source.model.DetailIngredientEntity
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.databinding.FragmentDetailIngredientBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.utils.Result

private const val RECIPE = "recipe"
private const val USER_ID = "user_id"

class DetailIngredientFragment : Fragment() {

    private val binding by lazy { FragmentDetailIngredientBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetailIngredientViewModel> { ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter: DetailIngredientAdapter
    private lateinit var listIng: List<IngredientEntity>
    private lateinit var listShop: List<ShoppingItemEntity>
    private var listIngLoaded: Boolean = false
    private var listShopLoaded: Boolean = false

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
    }

    private fun setupData() {
        arguments?.let {
            val recipe : Recipe? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(RECIPE, Recipe::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.getParcelable(RECIPE)
                }
            val userId : Int = it.getInt(USER_ID)
            if (recipe != null) {
                setupObservers(recipe)
                setupAction(userId)
            }
        }
    }

    private fun setupObservers(recipe: Recipe) {
        val listIngId = recipe.listIngredient.map { it.id }
        viewModel.getIngredientsByIds(listIngId).observe(viewLifecycleOwner) { listIng ->
            if (listIng.isNotEmpty()) {
                this.listIng = listIng
                listIngLoaded = true
                updateList(recipe)
            }
        }
        viewModel.getShoppingListByIds(listIngId).observe(viewLifecycleOwner) { listShop ->
            if (listShop.isNotEmpty()) {
                this.listShop = listShop
                listShopLoaded = true
                updateList(recipe)
            }
        }
    }

    private fun updateList(recipe: Recipe) {
//        && listIng.size == listShop.size && listIng.isNotEmpty()
        if (listIngLoaded && listShopLoaded && listIng.size == listShop.size) {
            val listCombined = mutableListOf<DetailIngredientEntity>()
            for (i in listIng.indices) {
                val index = recipe.listIngredient.indexOfFirst { it.id == listIng[i].ingId }
                listCombined.add(
                    DetailIngredientEntity(
                        id = listIng[i].ingId,
                        ingName = listIng[i].ingName,
                        qty = recipe.listIngredient[index].qty,
                        unit = recipe.listIngredient[index].unit,
                        isInPantry = listIng[i].isHave,
                        isInShoppingList = listShop[i].isHave,
                    )
                )
            }
            adapter.submitList(listCombined)
        }
    }

    private fun setupView() {
        adapter = DetailIngredientAdapter()
        binding.rvDetailIngredient.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false,
            )
            val listAdapter = this@DetailIngredientFragment.adapter
            adapter = listAdapter
        }
    }

    private fun setupAction(userId: Int) {
        adapter.setOnItemClickCallback(object : DetailIngredientAdapter.OnItemClickCallback {
            override fun onPantryCheckboxClicked(detailIng: DetailIngredientEntity) {
                updatePantry(userId, detailIng.id, detailIng.isInPantry)
            }
            override fun onShoppingCheckboxClicked(detailIng: DetailIngredientEntity) {
                updateShoppingList(userId, detailIng)
            }
        })
    }

    private fun updatePantry(userId: Int, ingId: Int, inPantry: Boolean) {
        if (inPantry) {
            viewModel.deletePantryIngredient(userId, ingId).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
//                        Toast.makeText(requireContext(), "Ingredient removed!", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Failed to remove ingredient"
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
//                        Toast.makeText(requireContext(), "Ingredient added!", Toast.LENGTH_SHORT).show()
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

    private fun updateShoppingList(userId: Int, detailIng: DetailIngredientEntity) {
        if (detailIng.isInShoppingList) {
            viewModel.deleteShoppingListItem(userId, detailIng.id).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
//                        Toast.makeText(requireContext(), "Shopping item removed!", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Failed remove shopping item"
                        Log.e(TAG, result.error + message)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            viewModel.addShoppingListItem(userId, detailIng.id, detailIng.qty, detailIng.unit).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
//                        Toast.makeText(requireContext(), "Shopping item added!", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        showLoading(false)
                        val message = "Failed adding shopping item"
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
        private const val TAG = "DetailIngredientFragment"
        @JvmStatic
        fun newInstance(recipe: Recipe, userId: Int) =
            DetailIngredientFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECIPE, recipe)
                    putInt(USER_ID, userId)
                }
            }
    }
}