package com.doanda.easymeal.ui.recipedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doanda.easymeal.data.response.detailrecipe.ListStepItem
import com.doanda.easymeal.databinding.ItemDetailStepBinding

class DetailStepAdapter(private val listStep: List<ListStepItem?>)
    : RecyclerView.Adapter<DetailStepAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDetailStepBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(step: ListStepItem?) {
                with(binding) {
                    step?.let {
                        tvStepNumber.text = step.no.toString()
                        tvStepDescription.text = step.description
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listStep.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = listStep[position]
        holder.bind(step)
    }
}