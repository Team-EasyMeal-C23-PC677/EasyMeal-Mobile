package com.doanda.easymeal.ui.pantry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.MainViewModel
import com.doanda.easymeal.data.response.pantry.ListIngredientItem
import com.doanda.easymeal.data.source.model.CategoryEntity
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.databinding.FragmentPantryBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.recipe.RecipeAdapter
import com.doanda.easymeal.utils.Result

class PantryFragment : Fragment() {

    private val binding by lazy { FragmentPantryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<PantryViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter: PantryCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(this::class.java.simpleName, "Pantry Fragment Called")

        getUser()
    }

    private fun getUser() {
//        viewModel.getUser().observe(requireActivity()) { user ->
//            if (user.isLogin) {
//                setupData(user.userId)
//            }
//        }
        viewModel.getLoginStatus().observe(requireActivity()) { isLogin ->
            if (isLogin) {
                viewModel.getUser().observe(requireActivity()) { user ->
                    setupData(user.userId)
                }
            }
        }
    }

    private fun setupData(userId: Int) {
        viewModel.getAllIngredients().observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    val data = result.data
                    setupView(userId, data)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "message"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(userId: Int, listIng: List<IngredientEntity>) {

        adapter = PantryCategoryAdapter()

        binding.rvPantryCategory.apply {
            adapter = this@PantryFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val listCategory = groupByCategory(listIng)

        adapter.submitList(listCategory.toMutableList())
        adapter.setOnItemClickCallback(object : PantryCategoryAdapter.OnItemClickCallback {
            override fun onChildItemCheckedChange(
                ingredient: IngredientEntity,
            ) {
                updatePantry(userId, ingredient.ingId, ingredient.isHave)
            }
        })
    }

    private fun updatePantry(userId: Int, ingId: Int, oldIsHave: Boolean) {
        if (oldIsHave) {
            viewModel.deletePantryIngredient(userId, ingId).observe(requireActivity()) { result ->
                when (result) {
                    is Result.Success -> {
                        showLoading(false)
                        updateList()
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
        }
    }

    private fun updateList() {
        viewModel.getAllIngredientsLocal().observe(requireActivity()) { listIng ->
            adapter.submitList(groupByCategory(listIng).toMutableList())
        }
    }

    private fun groupByCategory(listIng: List<IngredientEntity>): List<CategoryEntity> {
        val listCategoryMap: Map<String, List<IngredientEntity>> = listIng.groupBy { it.categoryName }
        val listCategory: List<CategoryEntity> = listCategoryMap.map {
            CategoryEntity(
                categoryName = it.key,
                listIngredient = it.value
            )
        }
        return listCategory
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance() = PantryFragment()
        private const val TAG = "PantryFragment"
    }

    private fun getLoginStatus(viewModel: PantryViewModel) {
////        viewModel.getLoginStatus().observe(requireActivity()) {
////            // TODO observe login status
////            Log.d(this::class.java.simpleName, "Checking login status")
////        }
//        val loginStatus = true // TODO implement userpreference
//        Log.d(this::class.java.simpleName, "Checking login status")
//        if (loginStatus == false) {
//            val intent = Intent(requireContext(), LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        } else {
//            dummySetupView()
//            dummySetupData(viewModel)
//        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun dummySetupData(viewModel: PantryViewModel) {
//        val dataIngredients = loadFromJsonListIngredientResponse(requireContext())
//        val dataUserIng = loadFromJsonListUserIngredientResponse(requireContext())
//
//
//        // TODO implement retrofit
//        // in result success
//
//        val listIngredient: List<ListIngredientItem> =
//            dataIngredients.listIngredient as List<ListIngredientItem>
//        val listUserIng: List<ListIngredientItem> =
//            dataUserIng.listIngredient as List<ListIngredientItem>
//
//        val listCategoryMap: Map<String?, List<ListIngredientItem>> = listIngredient.groupBy { it.categoryName }
//
//        val listCategory: List<CategoryEntity> = listCategoryMap.map {
//            CategoryEntity(
//                categoryName = it.key,
//                listIngredient = it.value
//            )
//        }
//
//        if (listIngredient.isNotEmpty()) {
//            binding.rvPantryCategory.apply {
//                layoutManager = LinearLayoutManager(
//                    requireContext(),
//                    LinearLayoutManager.VERTICAL,
//                    false,
//                )
//                val listAdapter = PantryCategoryAdapter(listCategory)
//                adapter = listAdapter
//                listAdapter.setOnItemClickCallback(object : PantryCategoryAdapter.OnItemClickCallback {
//                    override fun onChildItemCheckedChange(ingredient: ListIngredientItem) {
////                        TODO("Not yet implemented")
//                    }
//                })
//            }
//        }
    }

    private fun dummySetupView() {
////        binding.recipeSearchbar.ivUserPhoto.setOnClickListener {
////            // TODO intent to ProfileActivity
////        }
//
//        // TODO implement searching logic
////        binding.recipeSearchbar.searchBar.seton
//
//        binding.btnPantryHave.setOnClickListener {
//            // TODO filter data
//        }
//
//        binding.btnPantryToRecipe.setOnClickListener {
//            // TODO open dialog confirmation
//            // TODO if ok send user pantry to api
//            // TODO if ok call get recommended recipes api
//            view?.findNavController()?.navigate(R.id.action_navigation_pantry_to_navigation_recipe)
//        }

    }
}