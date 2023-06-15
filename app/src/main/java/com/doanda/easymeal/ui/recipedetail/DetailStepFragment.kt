package com.doanda.easymeal.ui.recipedetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.response.detailrecipe.Recipe
import com.doanda.easymeal.databinding.FragmentDetailStepBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val RECIPE = "recipe"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailStepFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailStepFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var recipe: String? = null
    private val binding by lazy { FragmentDetailStepBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RecipeDetailViewModel>()

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

    private fun setupData() {
        arguments?.let {
            val recipe : Recipe? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(RECIPE, Recipe::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.getParcelable(RECIPE)
                }

            if (recipe != null) {
                setupView(recipe)
            }
        }
    }

    private fun setupView(recipe: Recipe) {
        if (recipe.listStep != null) {
            binding.rvDetailStep.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                val listAdapter = DetailStepAdapter(recipe.listStep)
                adapter = listAdapter
                // TODO setCallback
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(recipe: Recipe) =
            DetailStepFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECIPE, recipe)
                }
            }
    }
}