package com.doanda.easymeal.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.ListRecipeItem
import com.doanda.easymeal.data.response.ListRecipeResponse
import com.doanda.easymeal.databinding.FragmentRecipeBinding
import com.doanda.easymeal.utils.getJsonDataFromResource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

        val data = loadDataFromJson()
        // TODO implement retrofit
        // in Result.Success
        val listRecipe: List<ListRecipeItem> = data.listRecipe as List<ListRecipeItem>
        binding.rvRecipe.apply {
            adapter = ListRecipeAdapter(listRecipe)
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false,
            )
        }
    }

    private fun loadDataFromJson(): ListRecipeResponse {
        val jsonFileString = getJsonDataFromResource(requireContext(), R.raw.all_recipe_response)
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

    private fun setupView() {
    }

    companion object {
        fun newInstance() = RecipeFragment()
    }
}