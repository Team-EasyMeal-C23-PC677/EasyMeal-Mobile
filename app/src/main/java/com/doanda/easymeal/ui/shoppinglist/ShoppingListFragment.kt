package com.doanda.easymeal.ui.shoppinglist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.databinding.FragmentShoppingListBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.utils.Result
import observeOnce

class ShoppingListFragment : Fragment() {

    private val binding by lazy { FragmentShoppingListBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ShoppingListViewModel>{ ViewModelFactory.getInstance(requireContext()) }

    private lateinit var adapter : ShoppingListAdapter
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
        getUser()
    }

    private fun setupData() {
        viewModel.getShoppingListLocal().observe(viewLifecycleOwner) { listShopping ->
            if (listShopping != null) {
                handleEmptyData(listShopping.isNotEmpty())
                updateList(listShopping)
            }
        }
    }

    private fun handleEmptyData(notEmpty: Boolean) {
        with(binding) {
            tvShoppingListNeed.visibility = if (notEmpty) View.VISIBLE else View.GONE
            rvShoppingListNeed.visibility = if (notEmpty) View.VISIBLE else View.GONE

            ivIllustEmptyShopping.visibility = if (notEmpty) View.GONE else View.VISIBLE
            tvEmptyShopping.visibility = if (notEmpty) View.GONE else View.VISIBLE
            tvEmptyShoppingDesc.visibility = if (notEmpty) View.GONE else View.VISIBLE
        }
    }

    private fun getUser() {
        viewModel.getUser().observeOnce(viewLifecycleOwner) { user ->
            if (user.userId != -1) {
                setupAction(user.userId)
            }
        }
    }

    private fun setupView() {
        adapter = ShoppingListAdapter()
        binding.rvShoppingListNeed.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false,
            )
            adapter = this@ShoppingListFragment.adapter
        }
    }

    private fun setupAction(userId: Int) {
        adapter.setOnItemClickCallback(object : ShoppingListAdapter.OnItemClickCallback {
            override fun onDeleteClicked(shoppingListItem: ShoppingItemEntity) {
                deleteItem(userId, shoppingListItem.id)
            }
        })
    }

    private fun deleteItem(userId: Int, ingId: Int) {
        viewModel.deleteShoppingListItem(userId, ingId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    showLoading(false)
//                    Toast.makeText(requireContext(), "Item deleted!", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    val message = "Failed to delete item"
                    Log.e(TAG, result.error + message)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateList(listShopping: List<ShoppingItemEntity>) {
        adapter.submitList(listShopping)
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "ShoppingListFragment"
    }
}