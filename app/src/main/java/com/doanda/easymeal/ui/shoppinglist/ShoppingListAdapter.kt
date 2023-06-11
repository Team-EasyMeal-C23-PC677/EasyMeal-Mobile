package com.doanda.easymeal.ui.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListItem
import com.doanda.easymeal.databinding.ItemShoppingListBinding
import convertDecimalToFraction

// TODO change ShoppingListItem to ShoppingEntity
class ShoppingListAdapter(private val listShopping: List<ShoppingListItem>)
    : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onDeleteClicked(shoppingListItem: ShoppingListItem)
        fun onCheckboxClicked(shoppingListItem: ShoppingListItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemShoppingListBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingListItem: ShoppingListItem?) {
            with(binding) {
                shoppingListItem?.let {
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listShopping.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingListItem = listShopping[position]
        holder.bind(shoppingListItem)

        // TODO change list into room entity with isHave attribute

        holder.binding.btnDeleteItem.setOnClickListener {
            onItemClickCallback.onDeleteClicked(listShopping[holder.bindingAdapterPosition])
        }

        holder.binding.cbShoppingCheckbox.setOnCheckedChangeListener(null)

        // TODO use ShoppingEntity isHave
        holder.binding.cbShoppingCheckbox.isChecked = false

        holder.binding.cbShoppingCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // TODO implement checkbox checked change
            onItemClickCallback.onCheckboxClicked(listShopping[holder.bindingAdapterPosition])
        }
    }

}
