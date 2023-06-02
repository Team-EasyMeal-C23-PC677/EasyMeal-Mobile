package com.doanda.easymeal.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.ListRecipeItem
import com.doanda.easymeal.data.response.ListRecipeResponse
import com.doanda.easymeal.databinding.FragmentFavoriteBinding
import com.doanda.easymeal.ui.recipe.RecipeViewModel
import com.doanda.easymeal.utils.getJsonStringFromResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FavoriteFragment : Fragment() {


    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        setupView()
        setupData()

        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupData() {
        val data = loadDataFromJson()
        // TODO implement retrofit
        // in Result.Success
        val listRecipe: List<ListRecipeItem> = data.listRecipe as List<ListRecipeItem>
        binding.rvFavorite.apply {
            adapter = FavoriteAdapter(listRecipe)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false,
            )
        }
    }

    private fun setupView() {
//        TODO("Not yet implemented")
    }



    private fun loadDataFromJson(): ListRecipeResponse {
        val jsonFileString = getJsonStringFromResource(requireContext(), R.raw.all_recipe_response)
        if (jsonFileString != null) {
            Log.i("JSON", jsonFileString)
        } else {
            Log.e("JSON", "FAILED TO LOAD JSON")
        }

        val gson = Gson()
        val listRecipe = object : TypeToken<ListRecipeResponse>() {}.type
        val recipes: ListRecipeResponse = gson.fromJson(jsonFileString, listRecipe)
//        recipes.forEachIndexed { idx, recipe -> Log.i("data", "> Item $idx:\n$recipe") }

        return recipes
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

}