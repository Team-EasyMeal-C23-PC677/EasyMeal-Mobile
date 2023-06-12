package com.doanda.easymeal.ui.recipedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.detailrecipe.ListDetailIngredientItem
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.databinding.ItemDetailIngredientBinding
import convertDecimalToFraction

// TODO replace ListDetailIngredientItem with IngredientEntity
class DetailIngredientAdapter(private val listDetailIngredient: List<ListDetailIngredientItem?>)
    : RecyclerView.Adapter<DetailIngredientAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onPantryCheckboxClicked(detailIngredient: ListDetailIngredientItem)
        fun onShoppingCheckboxClicked(detailIngredient: ListDetailIngredientItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemDetailIngredientBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(detailIngredient: ListDetailIngredientItem?) {
            with(binding) {
                detailIngredient?.let {
                    tvDetailIngredientQty.text =
                        itemView.context.getString(R.string.detail_ingredient_qty)
                            .format(
                                convertDecimalToFraction(detailIngredient.qty),
                                detailIngredient.unit
                            )
                    tvDetailIngredientName.text = detailIngredient.name
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listDetailIngredient.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailIngredient = listDetailIngredient[position]
        holder.bind(detailIngredient)

        holder.binding.chDetailPantry.setOnCheckedChangeListener(null)
        holder.binding.chDetailShopping.setOnCheckedChangeListener(null)

        // TODO use IngredientEntity isHave
        holder.binding.chDetailPantry.isChecked = false
        holder.binding.chDetailShopping.isChecked = false

        holder.binding.chDetailPantry.setOnCheckedChangeListener { _, isChecked ->
            // TODO implement chip checked change
        }

        holder.binding.chDetailShopping.setOnCheckedChangeListener { _, isChecked ->
            // TODO implement chip checked change
        }


    }
}