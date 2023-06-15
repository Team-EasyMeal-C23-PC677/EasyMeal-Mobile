package com.doanda.easymeal.ui.pantry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.databinding.ItemPantryIngredientBinding

class PantryIngredientAdapter : ListAdapter<IngredientEntity, PantryIngredientAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemCheckedChanged(ingredient: IngredientEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemPantryIngredientBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ingredient: IngredientEntity) {
            with(binding) {
                chPantryIngredient.text = ingredient.ingName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPantryIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)

        holder.binding.chPantryIngredient.setOnCheckedChangeListener(null)

        holder.binding.chPantryIngredient.isChecked = ingredient.isHave

        holder.binding.chPantryIngredient.setOnCheckedChangeListener { _, _ ->
            onItemClickCallback.onItemCheckedChanged(ingredient)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<IngredientEntity> = object : DiffUtil.ItemCallback<IngredientEntity>() {
            override fun areItemsTheSame(oldItem: IngredientEntity, newItem: IngredientEntity): Boolean {
                return oldItem.ingId == newItem.ingId
            }

            override fun areContentsTheSame(oldItem: IngredientEntity, newItem: IngredientEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
