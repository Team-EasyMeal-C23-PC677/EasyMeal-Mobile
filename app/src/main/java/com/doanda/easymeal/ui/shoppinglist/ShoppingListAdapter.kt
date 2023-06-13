package com.doanda.easymeal.ui.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.R
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.databinding.ItemShoppingListBinding
import convertDecimalToFraction

class ShoppingListAdapter : ListAdapter<ShoppingItemEntity, ShoppingListAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onDeleteClicked(shoppingListItem: ShoppingItemEntity)
//        fun onCheckboxClicked(shoppingListItem: ShoppingItemEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemShoppingListBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingListItem: ShoppingItemEntity) {
            with(binding) {
                tvShoppingIngredientName.text = shoppingListItem.ingName
                tvShoppingIngredientQty.text =
                    itemView.context.getString(R.string.detail_ingredient_qty)
                        .format(
                            convertDecimalToFraction(shoppingListItem.qty),
                            shoppingListItem.unit
                        )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingListItem = getItem(position)
        holder.bind(shoppingListItem)

        holder.binding.btnDeleteItem.setOnClickListener {
            onItemClickCallback.onDeleteClicked(shoppingListItem)
        }

//        holder.binding.cbShoppingCheckbox.setOnCheckedChangeListener(null)
//        holder.binding.cbShoppingCheckbox.isChecked = false
//        holder.binding.cbShoppingCheckbox.setOnCheckedChangeListener { _, isChecked ->
//            onItemClickCallback.onCheckboxClicked(shoppingListItem)
//        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ShoppingItemEntity> = object : DiffUtil.ItemCallback<ShoppingItemEntity>() {
            override fun areItemsTheSame(oldItem: ShoppingItemEntity, newItem: ShoppingItemEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShoppingItemEntity, newItem: ShoppingItemEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
