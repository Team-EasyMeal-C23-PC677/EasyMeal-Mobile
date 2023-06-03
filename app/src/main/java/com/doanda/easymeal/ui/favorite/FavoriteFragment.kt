package com.doanda.easymeal.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.response.ListRecipeItem
import com.doanda.easymeal.databinding.FragmentFavoriteBinding
import com.doanda.easymeal.ui.recipe.RecipeViewModel
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import com.doanda.easymeal.utils.loadFromJsonListRecipeResponse

class FavoriteFragment : Fragment() {


    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)


        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupData() {
        val data = loadFromJsonListRecipeResponse(requireContext())

        // TODO IMPLEMENT RETROFIT
        // in Result.Success
        val listRecipe: List<ListRecipeItem> = data.listRecipe as List<ListRecipeItem>
        if (listRecipe.isNotEmpty()) {
            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                val listAdapter = FavoriteAdapter(listRecipe)
                adapter = listAdapter
                listAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                    override fun onItemClicked(recipe: ListRecipeItem) {
                        val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
                        startActivity(intent)
                    }
                    override fun onFavoriteClicked(recipe: ListRecipeItem) {
                        // TODO HANDLE FAVORITE
                    }
                })
            }
        } else {
            // TODO HANDLE EMPTY DATA
        }
    }

    private fun setupView() {
//        TODO("Not yet implemented")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupData()
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

}