package com.doanda.easymeal.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.databinding.ItemFavoriteBinding
import convertMinuteToHourMinute

class FavoriteAdapter(private val listFavorite: List<ListRecipeItem>, private val listRecipe: List<ListRecipeItem>)
    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>()
{
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(recipe: ListRecipeItem)
        fun onFavoriteClicked(recipe: ListRecipeItem)
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
                            val (hours, minutes) = convertMinuteToHourMinute(recipe?.totalTime)
                            val timeText = mutableListOf<String>()
                            if (hours > 0)
                                timeText.add(getString(R.string.hour_format).format(hours))
                            if (minutes > 0)
                                timeText.add(getString(R.string.minute_format).format(minutes))
                            timeText.joinToString(" ")
                        }

                    tvFavoriteServing.text =
                        itemView.context.getString(R.string.serving_format).format(recipe?.serving)

//                    if (recipe?.missing?.isEmpty() == true) {
//                        tvFavoriteWarning.visibility = View.GONE
//                    } else {
//                        tvFavoriteWarning.text = formatRecipeWarning(recipe?.missing)
//                    }

                    Glide.with(itemView.context)
                        .load(recipe?.imgUrl)
                        .into(binding.ivFavoriteImage)

                    // TODO if in favorite then red else black
                    if (recipe in listFavorite)
                        btnFavoriteFavorite.setColorFilter(R.color.red_theme)
                    else
                        btnFavoriteFavorite.setColorFilter(R.color.black)
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
            onItemClickCallback.onItemClicked(listRecipe[holder.bindingAdapterPosition])
        }

        holder.binding.btnFavoriteFavorite.setOnClickListener {
            onItemClickCallback.onFavoriteClicked(listRecipe[holder.bindingAdapterPosition])
        }

    }

}