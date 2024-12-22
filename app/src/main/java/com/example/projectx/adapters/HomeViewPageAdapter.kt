package com.example.projectx.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.projectx.fragments.Categories.BaseCategoriFragment
import com.example.projectx.fragments.Categories.MainHomeCategoriFragment

class HomeViewPageAdapter (
    private val fragments: List<Fragment>,
        fm: FragmentManager,
        lifecycle: Lifecycle
    ):FragmentStateAdapter(fm,lifecycle) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return when (position){
                0 -> MainHomeCategoriFragment()
                1 -> BaseCategoriFragment.newInstance("Đồ điện tử")
                2 -> BaseCategoriFragment.newInstance("Đồ trang sức")
                3 -> BaseCategoriFragment.newInstance("Quần áo nữ")
                4 -> BaseCategoriFragment.newInstance("Quần áo nam")
                else -> MainHomeCategoriFragment()
            }
        }
}