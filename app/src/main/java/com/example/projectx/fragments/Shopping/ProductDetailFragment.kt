package com.example.projectx.fragments.Shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.projectx.Api.RetrofitClient
import com.example.projectx.R
import com.example.projectx.active.ShoppingActivity
import com.example.projectx.data.CartProduct
import com.example.projectx.data.Product
import com.example.projectx.databinding.FragmentProductDetailBinding
import com.example.projectx.util.Resource
import com.example.projectx.util.hideBottomNav
import com.example.projectx.viewModels.DetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {
    private lateinit var bindding : FragmentProductDetailBinding
    private val args by navArgs<ProductDetailFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNav()
        bindding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return bindding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productId = args.productId
        fetchProductDetail(productId.toInt(), false)

        bindding.imageClose.setOnClickListener{
            findNavController().navigateUp()
        }

        bindding.buttonAddToCart.setOnClickListener{
            fetchProductDetail(productId.toInt(), true)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        bindding.buttonAddToCart.startAnimation()
                    }

                    is Resource.Success -> {
                        bindding.buttonAddToCart.revertAnimation()
                        Toast.makeText(requireContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Error -> {
                        bindding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun fetchProductDetail(productId: Int, check: Boolean) {
        val apiService = RetrofitClient.apiService
        val call = apiService.getProductById(productId)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    product?.let {
                        if (check){
                            viewModel.addUpdateProductInCart(CartProduct(product, 1))
                        }
                        bindding.tvProductName.text = it.title
                        bindding.tvProductDescription.text = it.description
                        bindding.tvProductPrice.text = "Giá: ${it.price} VNĐ"
                        Glide.with(this@ProductDetailFragment)
                            .load(it.image)
                            .into(bindding.imageProduct)
                    }
                }
                else {
                    // Xử lý lỗi }
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                // Xử lý lỗi
            }
        })
    }
}