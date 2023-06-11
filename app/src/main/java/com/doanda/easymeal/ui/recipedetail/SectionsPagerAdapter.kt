package com.doanda.easymeal.ui.recipedetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.doanda.easymeal.data.response.detailrecipe.Recipe

class SectionsPagerAdapter(activity: AppCompatActivity)
    : FragmentStateAdapter(activity) {

    lateinit var recipe: Recipe

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DetailIngredientFragment.newInstance(recipe)
            1 -> fragment = DetailStepFragment.newInstance(recipe)
        }
        return fragment as Fragment
    }


}
