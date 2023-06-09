package com.doanda.easymeal.ui.pantry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.data.response.pantry.ListIngredientItem
import com.doanda.easymeal.databinding.ItemPantryIngredientBinding
import com.doanda.easymeal.model.CategoryEntity

class PantryIngredientAdapter(private val listIngredient: List<ListIngredientItem>)
    : RecyclerView.Adapter<PantryIngredientAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemCheckedChanged(ingredient: ListIngredientItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemPantryIngredientBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ingredient: ListIngredientItem) {
            with(binding) {
                let {
                    chPantryIngredient.text = ingredient.ingName
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPantryIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listIngredient.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = listIngredient[position]
        holder.bind(ingredient)

        holder.binding.chPantryIngredient.setOnCheckedChangeListener { _, _ ->
            // TODO if checked add to room, if not delete from room
            onItemClickCallback.onItemCheckedChanged(listIngredient[holder.bindingAdapterPosition])
            notifyItemChanged(position)
        }
    }


}
