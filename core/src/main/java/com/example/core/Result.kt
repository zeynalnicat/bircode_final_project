package com.example.core

sealed class Result<out T:Any> {
    data class Success<out T: Any>(val data:T):Result<T>()
    data class Error(val message:String):Result<Nothing>()
}