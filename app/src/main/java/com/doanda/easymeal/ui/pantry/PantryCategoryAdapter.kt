package com.doanda.easymeal.ui.pantry

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.pantry.ListIngredientItem
import com.doanda.easymeal.databinding.ItemCategoryBinding
import com.doanda.easymeal.model.CategoryEntity

class PantryCategoryAdapter(private val listCategory: List<CategoryEntity>)
    : RecyclerView.Adapter<PantryCategoryAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onChildItemCheckedChange(ingredient: CategoryEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryEntity) {
            with(binding) {
                let {
                    tvCategoryName.text = category.categoryName

                    // TODO get user pantry info, count in listIngredient
                    tvCategoryCounter.text = category.listIngredient.size.toString()

                    icCategoryDropdown.setImageResource(R.drawable.ic_round_arrow_right)

                    rvPantryIngredient.setHasFixedSize(true)
                    rvPantryIngredient.layoutManager =
                        StaggeredGridLayoutManager(
                            2,
                            StaggeredGridLayoutManager.HORIZONTAL
                        )

                    val adapter =
                        PantryIngredientAdapter(category.listIngredient)
                    rvPantryIngredient.adapter = adapter
                    adapter.setOnItemClickCallback(object : PantryIngredientAdapter.OnItemClickCallback {
                        override fun onItemCheckedChanged(ingredient: ListIngredientItem) {
//                            TODO("Not yet implemented")

                        }

                    })

                    val isExpandable = category.isExpandable
                    rvPantryIngredient.visibility =
                        if (isExpandable) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listCategory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = listCategory[position]
        holder.bind(category)

        holder.binding.layoutCategory.setOnClickListener {
            category.isExpandable = !category.isExpandable
            holder.binding.icCategoryDropdown.setImageResource(
                if (category.isExpandable) R.drawable.ic_round_arrow_drop_down
                else R.drawable.ic_round_arrow_right
            )
            notifyItemChanged(position)
        }
    }

}
