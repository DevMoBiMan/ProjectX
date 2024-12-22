package com.example.projectx.Api
import com.example.projectx.data.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(@Query("limit") limit: Int): Call<List<Product>>

    @GET("products/category/{category}")
    fun getProductsByCategory(
        @Path("category")
        category: String,
        @Query("limit")
        limit: Int
    ): Call<List<Product>>

    @GET("products/{id}")
    fun getProductById(@Path("id") productId: Int): Call<Product>
}