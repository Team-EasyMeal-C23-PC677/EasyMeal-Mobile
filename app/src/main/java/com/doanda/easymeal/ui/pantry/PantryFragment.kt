package com.doanda.easymeal.ui.pantry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.response.pantry.ListIngredientItem
import com.doanda.easymeal.databinding.FragmentPantryBinding
import com.doanda.easymeal.model.CategoryEntity
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.utils.loadFromJsonListIngredientResponse

class PantryFragment : Fragment() {


    private val binding by lazy { FragmentPantryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<PantryViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(this::class.java.simpleName, "Pantry Fragment Called")
        getLoginStatus(viewModel)
    }

    private fun getLoginStatus(viewModel: PantryViewModel) {
//        viewModel.getLoginStatus().observe(requireActivity()) {
//            // TODO observe login status
//            Log.d(this::class.java.simpleName, "Checking login status")
//        }
        val loginStatus = true // TODO implement userpreference
        Log.d(this::class.java.simpleName, "Checking login status")
        if (loginStatus == false) {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            setupView()
            setupData(viewModel)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupData(viewModel: PantryViewModel) {
        val data = loadFromJsonListIngredientResponse(requireContext())



        // TODO implement retrofit
        // in result success

        val listIngredient: List<ListIngredientItem> =
            data.listIngredient as List<ListIngredientItem>

        val listCategoryMap: Map<String?, List<ListIngredientItem>> = listIngredient.groupBy { it.categoryName }

        val listCategory: List<CategoryEntity> = listCategoryMap.map {
            CategoryEntity(
                categoryName = it.key,
                listIngredient = it.value
            )
        }

        if (listIngredient.isNotEmpty()) {
            binding.rvPantryCategory.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                val listAdapter = PantryCategoryAdapter(listCategory)
                adapter = listAdapter
            }
        }
    }

    private fun setupView() {
//        binding.recipeSearchbar.ivUserPhoto.setOnClickListener {
//            // TODO intent to ProfileActivity
//        }
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


    companion object {
        fun newInstance() = PantryFragment()
    }
}