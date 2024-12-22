package com.example.projectx.fragments.Shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectx.R
import com.example.projectx.adapters.HomeViewPageAdapter
import com.example.projectx.databinding.FragmentHomeBinding
import com.example.projectx.fragments.Categories.BaseCategoriFragment
import com.example.projectx.fragments.Categories.ElectronicsCategori
import com.example.projectx.fragments.Categories.JeweleryCategori
import com.example.projectx.fragments.Categories.MainHomeCategoriFragment
import com.example.projectx.fragments.Categories.MenClothingCategori
import com.example.projectx.fragments.Categories.WomenClothingCategori
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf(
            MainHomeCategoriFragment(),
            BaseCategoriFragment.newInstance("Đồ điện tử"),
            BaseCategoriFragment.newInstance("Đồ trang sức"),
            BaseCategoriFragment.newInstance("Quần áo nữ"),
            BaseCategoriFragment.newInstance("Quần áo nam")
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter =
            HomeViewPageAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Trang chủ"
                1 -> tab.text = "Đồ điện tử"
                2 -> tab.text = "Đồ trang sức"
                3 -> tab.text = "Quần áo nữ"
                4 -> tab.text = "Quần áo nam"
            }
        }.attach()
    }
}