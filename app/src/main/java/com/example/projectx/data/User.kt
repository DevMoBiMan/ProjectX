package com.example.projectx.data

data class User(
    val firstname : String,
    val lastname : String,
    val email : String,
    val imagepath : String = ""
){
    constructor() : this ("", "", "", "")
}