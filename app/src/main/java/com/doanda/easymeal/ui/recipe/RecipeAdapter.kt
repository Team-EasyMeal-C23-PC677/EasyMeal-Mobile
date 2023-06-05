package com.doanda.easymeal.ui.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.databinding.ItemRecipeBinding
import convertMinuteToHourMinute

class RecipeAdapter(private val listRecipe: List<ListRecipeItem>)
    : RecyclerView.Adapter<RecipeAdapter.ViewHolder>()
{
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onFavoriteClicked(recipe: ListRecipeItem)
        fun onItemClicked(recipe: ListRecipeItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: ListRecipeItem?) {
            with(binding) {
                let {
                    tvRecipeTitle.text = recipe?.title

                    tvRecipeTime.text =
                        with(itemView.context) {
                            val (hours, minutes) = convertMinuteToHourMinute(recipe?.totalTime)
                            val timeText = mutableListOf<String>()
                            if (hours > 0)
                                timeText.add(getString(R.string.hour_format).format(hours))
                            if (minutes > 0)
                                timeText.add(getString(R.string.minute_format).format(minutes))
                            timeText.joinToString(" ")
                        }

                    tvRecipeServing.text =
                        itemView.context.getString(R.string.serving_format).format(recipe?.serving)

//                    if (recipe?.missing?.isEmpty() == true) {
//                        tvRecipeWarning.visibility = View.GONE
//                    } else {
//                        tvRecipeWarning.text = formatRecipeWarning(recipe?.missing)
//                    }

                    Glide.with(itemView.context)
                        .load(recipe?.imgUrl)
                        .into(binding.ivRecipeImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRecipe.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = listRecipe[position]
        holder.bind(recipe)

        holder.binding.cardRecipe.setOnClickListener {
            onItemClickCallback.onItemClicked(listRecipe[holder.bindingAdapterPosition])
        }

        holder.binding.btnRecipeFavorite.setOnClickListener {
            onItemClickCallback.onFavoriteClicked(listRecipe[holder.bindingAdapterPosition])
        }
    }

}