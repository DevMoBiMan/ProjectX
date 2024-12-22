package com.example.projectx.util

import android.util.Patterns
import org.intellij.lang.annotations.Pattern

fun CheckVauleEmail(email:String) : RegisterCheck {
    if ( email.isEmpty())
        return RegisterCheck.Failed("Email chưa được điền")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterCheck.Failed("Email không đúng định dạng")
    return RegisterCheck.Succes
}

fun CheckVaulePassword(password:String) : RegisterCheck {
    if ( password.isEmpty())
        return RegisterCheck.Failed("Mật khẩu chưa được điền")
    if (password.length < 6 || password.length > 12)
        return RegisterCheck.Failed("Mật khẩu phải từ 6 - 12 kí tự")
    return RegisterCheck.Succes
}