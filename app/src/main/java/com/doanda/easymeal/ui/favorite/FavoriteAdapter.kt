package com.doanda.easymeal.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doanda.easymeal.R
import com.doanda.easymeal.data.source.model.RecipeEntity
import com.doanda.easymeal.databinding.ItemFavoriteBinding
import convertMinuteToHourMinute

class FavoriteAdapter : ListAdapter<RecipeEntity, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK)
{
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(recipe: RecipeEntity)
        fun onFavoriteClicked(recipe: RecipeEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(val binding: ItemFavoriteBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeEntity) {
            with(binding) {
                tvFavoriteTitle.text = recipe.title

                tvFavoriteDescription.text = recipe.description

                tvFavoriteTime.text =
                    with(itemView.context) {
                        val (hours, minutes) = convertMinuteToHourMinute(recipe.totalTime)
                        val timeText = mutableListOf<String>()
                        if (hours > 0)
                            timeText.add(getString(R.string.hour_format).format(hours))
                        if (minutes > 0)
                            timeText.add(getString(R.string.minute_format).format(minutes))
                        timeText.joinToString(" ")
                    }

                tvFavoriteServing.text =
                    itemView.context.getString(R.string.serving_format).format(recipe.serving)

//                    if (recipe?.missing?.isEmpty() == true) {
//                        tvFavoriteWarning.visibility = View.GONE
//                    } else {
//                        tvFavoriteWarning.text = formatRecipeWarning(recipe?.missing)
//                    }

                Glide.with(itemView.context)
                    .load(recipe.imgUrl)
                    .into(binding.ivFavoriteImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        
        val btnFavorite = holder.binding.btnFavoriteFavorite

        if (recipe.isFavorite) {
            btnFavorite.setImageResource(R.drawable.ic_round_favorite_selected)
        } else {
            btnFavorite.setImageResource(R.drawable.ic_round_favorite)
        }

        holder.binding.cardFavorite.setOnClickListener {
            onItemClickCallback.onItemClicked(recipe)
        }
        holder.binding.btnFavoriteFavorite.setOnClickListener {
            onItemClickCallback.onFavoriteClicked(recipe)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<RecipeEntity> = object : DiffUtil.ItemCallback<RecipeEntity>() {
            override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

}