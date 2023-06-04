package com.doanda.easymeal.ui.recipedetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity)
    : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    // TODO
    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }


}
