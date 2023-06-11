package com.doanda.easymeal.ui.pantry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.doanda.easymeal.R
import com.doanda.easymeal.databinding.ItemCategoryBinding
import com.doanda.easymeal.data.source.model.CategoryEntity
import com.doanda.easymeal.data.source.model.IngredientEntity

class PantryCategoryAdapter : ListAdapter<CategoryEntity, PantryCategoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onChildItemCheckedChange(ingredient: IngredientEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryEntity) {
            with(binding) {
                tvCategoryName.text = category.categoryName
                binding.icCategoryDropdown.setImageResource(R.drawable.ic_round_arrow_drop_down_circle)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)

        val isExpanded = category.isExpanded
        holder.binding.rvPantryIngredient.visibility =
            if (isExpanded) View.VISIBLE else View.GONE

//        holder.binding.layoutCategory.setOnClickListener {
//            category.isExpanded = !category.isExpanded
//            notifyItemChanged(position) // TODO find alternative
//        }

        holder.binding.rvPantryIngredient.setHasFixedSize(true)
        holder.binding.rvPantryIngredient.layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.HORIZONTAL
            )

        val adapter =
            PantryIngredientAdapter()
        adapter.submitList(category.listIngredient.toMutableList())
        holder.binding.rvPantryIngredient.adapter = adapter
        adapter.setOnItemClickCallback(object : PantryIngredientAdapter.OnItemClickCallback {
            override fun onItemCheckedChanged(ingredient: IngredientEntity) {
                onItemClickCallback.onChildItemCheckedChange(ingredient) // TODO bener
            }
        })
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CategoryEntity> = object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
                return oldItem.categoryName == newItem.categoryName
            }

            override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}
