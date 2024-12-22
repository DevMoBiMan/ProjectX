package com.example.projectx.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectx.data.Product
import com.example.projectx.databinding.ProductRvItemBinding
import com.example.projectx.fragments.Shopping.HomeFragmentDirections

class BestProductsAdapter (private val context: Context, private val products: List<Product>) : RecyclerView.Adapter<BestProductsAdapter.BestProductsHolder>(){

    class BestProductsHolder(val binding: ProductRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsHolder {
        val binding = ProductRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BestProductsHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: BestProductsHolder, position: Int) {
        val product = products[position]
        holder.binding.tvName.text = product.title
        holder.binding.tvPrice.text = "Giá: ${product.price} VNĐ"
        Glide.with(context).load(product.image).into(holder.binding.imgProduct)

        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections
                .actionHomeFragmentToProductDetailFragment(product.id)
            holder.itemView.findNavController().navigate(action)
        }
    }
}
