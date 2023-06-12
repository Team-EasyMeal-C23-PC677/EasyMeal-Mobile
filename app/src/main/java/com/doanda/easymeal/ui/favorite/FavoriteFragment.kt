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
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.loadFromJsonListFavoriteResponse

class FavoriteFragment : Fragment() {


    private val binding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FavoriteViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupData() {

        // TODO implement observe
        val dataFavorite = loadFromJsonListFavoriteResponse(requireContext())

        // in Result.Success

        val listFavorite: List<ListRecipeItem> = dataFavorite.listRecipe as List<ListRecipeItem>

        if (listFavorite != null) {
            setupView(listFavorite)
        }

    }

    private fun setupView(listFavorite: List<ListRecipeItem>) {
        if (listFavorite.isNotEmpty()) {
            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                val listAdapter = FavoriteAdapter(listFavorite)
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


    companion object {
        fun newInstance() = FavoriteFragment()
    }

}