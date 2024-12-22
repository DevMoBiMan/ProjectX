package com.example.projectx.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectx.data.Product
import com.example.projectx.databinding.SpecialRvItemBinding
import com.example.projectx.fragments.Shopping.HomeFragmentDirections

class SpecialProductsAdapter (private val context: Context, private val products: List<Product>) : RecyclerView.Adapter<SpecialProductsAdapter.SpecialProductsHolder>(){

    class SpecialProductsHolder(val binding: SpecialRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductsHolder {
        val binding = SpecialRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SpecialProductsHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: SpecialProductsHolder, position: Int) {
        val product = products[position]
        holder.binding.tvSpecialProductName.text = product.title
        holder.binding.tvSpecialPrdouctPrice.text = "Giá: ${product.price} VNĐ"
        Glide.with(context).load(product.image).into(holder.binding.imageSpecialRvItem)

        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections
                .actionHomeFragmentToProductDetailFragment(product.id)
            holder.itemView.findNavController().navigate(action)
        }
    }
}