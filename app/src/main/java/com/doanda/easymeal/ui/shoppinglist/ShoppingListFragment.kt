package com.doanda.easymeal.ui.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListItem
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.databinding.FragmentShoppingListBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.utils.Result

class ShoppingListFragment : Fragment() {

    private val binding by lazy { FragmentShoppingListBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ShoppingListViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        viewModel.getShoppingList(userId).observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
                    val data = result.data
                    setupView(data)
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    Log.e(TAG, result.error)
                    Toast.makeText(requireContext(), "Error loading shopping list", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView(listShopping: List<ShoppingItemEntity>) {
//        if (listShopping.isNotEmpty()) {
//            binding.rvShoppingListNeed.apply {
//                layoutManager = LinearLayoutManager(
//                    requireContext(),
//                    LinearLayoutManager.VERTICAL,
//                    false,
//                )
//                val listAdapter = ShoppingListAdapter(listShopping)
//                adapter = listAdapter
//                listAdapter.setOnItemClickCallback(object: ShoppingListAdapter.OnItemClickCallback {
//                    override fun onDeleteClicked(shoppingListItem: ShoppingListItem) {
//                        // TODO handle delete shopping list item
//                    }
//
//                    override fun onCheckboxClicked(shoppingListItem: ShoppingListItem) {
//                        // TODO handle checkbox clicked
//                    }
//
//                })
//            }
//        } else {
//            // TODO handle empty shopping list
//        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        fun newInstance() = ShoppingListFragment()
        private const val TAG = "ShoppingListFragment"
    }
}