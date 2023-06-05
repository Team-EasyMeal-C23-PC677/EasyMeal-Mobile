package com.doanda.easymeal.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.databinding.FragmentRecipeBinding
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.loadFromJsonListRecipeResponse


class RecipeFragment : Fragment() {

    private var _binding : FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)

        setupView()
        setupData()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup views
        setupView()
        setupData()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupData() {

        val data = loadFromJsonListRecipeResponse(requireContext())
        // TODO implement retrofit
        // in Result.Success
        val listRecipe: List<ListRecipeItem> = data.listRecipe as List<ListRecipeItem>
        binding.rvRecipe.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false,
            )
            val listAdapter = RecipeAdapter(listRecipe)
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

    private fun setupView() {
    }

    companion object {
        fun newInstance() = RecipeFragment()
    }
}