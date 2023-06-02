package com.doanda.easymeal.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.ListRecipeItem
import com.doanda.easymeal.databinding.ItemFavoriteBinding
import com.doanda.easymeal.ui.recipedetail.RecipeDetailActivity
import formatRecipeTime
import formatRecipeWarning

class FavoriteAdapter(private val listRecipe: List<ListRecipeItem>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>()
{
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ListRecipeItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: ListRecipeItem?) {
            with(binding) {
                let {
                    tvFavoriteTitle.text = recipe?.title

                    tvFavoriteTime.text =
                        with(itemView.context) {
                            val (hours, minutes) = formatRecipeTime(recipe?.totalTime)
                            var timeText = ""
                            if (hours > 0)
                                timeText += getString(R.string.hour_format).format(hours)
                            if (minutes > 0)
                                timeText += getString(R.string.minute_format).format(minutes)
                            timeText
                        }

                    tvFavoriteServing.text =
                        itemView.context.getString(R.string.serving_format).format(recipe?.serving)

                    if (recipe?.missing?.isEmpty() == true) {
                        tvFavoriteWarning.visibility = View.GONE
                    } else {
                        tvFavoriteWarning.text = formatRecipeWarning(recipe?.missing)
                    }

                    Glide.with(itemView.context)
                        .load(recipe?.imgUrl)
                        .into(binding.ivFavoriteImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRecipe.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = listRecipe[position]
        holder.bind(recipe)

        holder.binding.cardFavorite.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id)
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.btnFavoriteFavorite.setOnClickListener {

        }

    }

}