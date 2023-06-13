package com.doanda.easymeal.ui.recipedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.R
import com.doanda.easymeal.data.source.model.DetailIngredientEntity
import com.doanda.easymeal.databinding.ItemDetailIngredientBinding
import convertDecimalToFraction

class DetailIngredientAdapter : ListAdapter<DetailIngredientEntity, DetailIngredientAdapter.ViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onPantryCheckboxClicked(detailIng: DetailIngredientEntity)
        fun onShoppingCheckboxClicked(detailIng: DetailIngredientEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemDetailIngredientBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(detailIng: DetailIngredientEntity) {
            with (binding) {
                tvDetailIngredientQty.text = itemView.context.getString(R.string.detail_ingredient_qty)
                    .format(
                        convertDecimalToFraction(detailIng.qty),
                        detailIng.unit
                    )
                tvDetailIngredientName.text = detailIng.ingName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailIng = getItem(position)
        holder.bind(detailIng)

        holder.binding.chDetailPantry.setOnCheckedChangeListener(null)
        holder.binding.chDetailShopping.setOnCheckedChangeListener(null)

        holder.binding.chDetailPantry.isChecked = detailIng.isInPantry
        holder.binding.chDetailShopping.isChecked = detailIng.isInShoppingList

        holder.binding.chDetailPantry.isSelected = detailIng.isInPantry
        holder.binding.chDetailShopping.isSelected = detailIng.isInShoppingList

        holder.binding.chDetailPantry.setOnCheckedChangeListener { _, _ ->
            onItemClickCallback.onPantryCheckboxClicked(detailIng)
        }

        holder.binding.chDetailShopping.setOnCheckedChangeListener { _, _ ->
            onItemClickCallback.onShoppingCheckboxClicked(detailIng)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<DetailIngredientEntity> = object : DiffUtil.ItemCallback<DetailIngredientEntity>() {
            override fun areItemsTheSame(oldItem: DetailIngredientEntity, newItem: DetailIngredientEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DetailIngredientEntity, newItem: DetailIngredientEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}