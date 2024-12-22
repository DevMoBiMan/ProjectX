package com.example.projectx.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectx.data.Product
import com.example.projectx.databinding.BestDealsRvItemBinding
import com.example.projectx.fragments.Shopping.HomeFragmentDirections
import com.example.projectx.fragments.Shopping.ProductDetailFragmentArgs

class BestDealProductsAdapter (private val context: Context, private val products: List<Product>) : RecyclerView.Adapter<BestDealProductsAdapter.BestDealHolder>(){

    class BestDealHolder(val binding: BestDealsRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealHolder {
        val binding = BestDealsRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BestDealHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: BestDealHolder, position: Int) {
        val product = products[position]
        holder.binding.tvDealProductName.text = product.title
        holder.binding.tvNewPrice.text = "Giá: ${product.price} VNĐ"
        Glide.with(context).load(product.image).into(holder.binding.imgBestDeal)

        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections
                .actionHomeFragmentToProductDetailFragment(product.id)
            holder.itemView.findNavController().navigate(action)
        }
    }
}
