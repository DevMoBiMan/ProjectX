package com.example.projectx.fragments.Categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.R
import com.example.projectx.Api.RetrofitClient
import com.example.projectx.adapters.BestDealProductsAdapter
import com.example.projectx.adapters.BestProductsAdapter
import com.example.projectx.adapters.SpecialProductsAdapter
import com.example.projectx.data.Product
import com.example.projectx.databinding.FragmentMainHomeCategoriBinding
import com.example.projectx.util.showBottomNav
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeCategoriFragment : Fragment(R.layout.fragment_main_home_categori) {

    private lateinit var binding: FragmentMainHomeCategoriBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealProductsAdapter: BestDealProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainHomeCategoriBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSpecialProducts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        setupSpecialProductsRv()

        binding.rvBestDealsProducts.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        setupBestDealProductsRv()

        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
        }

        setupBestProductsRv()
    }

    private fun setupSpecialProductsRv() {
        val apiService = RetrofitClient.apiService
        val call = apiService.getProducts(5)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    productList?.let {
                        specialProductsAdapter = SpecialProductsAdapter(requireContext(), it)
                        binding.rvSpecialProducts.adapter = specialProductsAdapter
                    }
                }
                else {
                    // Xử lý lỗi
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // Xử lý lỗi
            }
        })
    }

    private fun setupBestDealProductsRv() {
        val apiService = RetrofitClient.apiService
        val call = apiService.getProducts(3)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    productList?.let {
                        bestDealProductsAdapter = BestDealProductsAdapter(requireContext(), it)
                        binding.rvBestDealsProducts.adapter = bestDealProductsAdapter
                    }
                }
                else {
                    // Xử lý lỗi
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // Xử lý lỗi
            }
        })
    }

    private fun setupBestProductsRv() {
        val apiService = RetrofitClient.apiService
        val call = apiService.getProducts(10)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    productList?.let {
                        bestProductsAdapter = BestProductsAdapter(requireContext(), it)
                        binding.rvBestProducts.adapter = bestProductsAdapter
                    }
                }
                else {
                    // Xử lý lỗi
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // Xử lý lỗi
            }
        })
    }

    override fun onResume() {
        super.onResume()

        showBottomNav()
    }
}