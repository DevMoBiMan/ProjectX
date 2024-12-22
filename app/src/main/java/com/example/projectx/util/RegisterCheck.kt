package com.example.projectx.util

sealed class RegisterCheck() {
    object Succes: RegisterCheck()
    data class Failed(val mess: String): RegisterCheck()
}

data class RegisterCheckStates(
    val email : RegisterCheck,
    val password : RegisterCheck
)