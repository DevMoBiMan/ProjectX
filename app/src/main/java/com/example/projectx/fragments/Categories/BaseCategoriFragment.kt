package com.example.projectx.fragments.Categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.Api.RetrofitClient
import com.example.projectx.R
import com.example.projectx.adapters.BestProductsAdapter
import com.example.projectx.adapters.SpecialProductsAdapter
import com.example.projectx.data.Product
import com.example.projectx.databinding.FragmentBaseCategoriBinding
import com.example.projectx.databinding.FragmentLoginBinding
import com.example.projectx.databinding.FragmentMainHomeCategoriBinding
import com.example.projectx.util.showBottomNav
import com.example.projectx.viewModels.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseCategoriFragment : Fragment(R.layout.fragment_base_categori) {
    private lateinit var binding : FragmentBaseCategoriBinding
    private lateinit var bestProductsAdapter: BestProductsAdapter

    companion object {
        private const val ARG_CATEGORY = "category"
        fun newInstance(category: String) = BaseCategoriFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, category)
            }
        }
    }

    protected var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoriBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOfferProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
        }

        setupSpecialProductsRv()
    }

    private fun setupSpecialProductsRv() {
        val apiService = RetrofitClient.apiService
        var categoryFinal = ""
        when (category){
            "Đồ điện tử" -> categoryFinal = "electronics"
            "Đồ trang sức" -> categoryFinal = "jewelery"
            "Quần áo nam" -> categoryFinal = "men's clothing"
            "Quần áo nữ" -> categoryFinal = "women's clothing"
        }

        val call = apiService.getProductsByCategory(categoryFinal,10)
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    productList?.let {
                        bestProductsAdapter = BestProductsAdapter(requireContext(), it)
                        binding.rvOfferProducts.adapter = bestProductsAdapter
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