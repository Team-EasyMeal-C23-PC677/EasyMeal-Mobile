package com.doanda.easymeal.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.databinding.FragmentFavoriteBinding
import com.doanda.easymeal.ui.recipe.RecipeViewModel
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.loadFromJsonLisFavoriteResponse
import com.doanda.easymeal.utils.loadFromJsonListRecipeResponse

class FavoriteFragment : Fragment() {


    private val binding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeViewModel>()

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

    @Suppress("UNCHECKED_CAST")
    private fun setupData() {
        val dataRecipe = loadFromJsonListRecipeResponse(requireContext())
        val dataFavorite = loadFromJsonLisFavoriteResponse(requireContext())

        // TODO implement retrofit
        // in Result.Success
        val listRecipe: List<ListRecipeItem> = dataRecipe.listRecipe as List<ListRecipeItem>
        val listFavorite: List<ListRecipeItem> = dataFavorite.listRecipe as List<ListRecipeItem>

        if (listRecipe.isNotEmpty()) {
            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                val listAdapter = FavoriteAdapter(listFavorite, listRecipe)
                adapter = listAdapter
                listAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                    override fun onItemClicked(recipe: ListRecipeItem) {
                        val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                        startActivity(intent)
                    }
                    override fun onFavoriteClicked(recipe: ListRecipeItem) {
                        // TODO handle favorite, send to room if ok change
                    }
                })
            }
        } else {
            // TODO handle empty data
        }
    }

    private fun setupView() {
//        TODO("Not yet implemented")

    }


    companion object {
        fun newInstance() = FavoriteFragment()
    }

}