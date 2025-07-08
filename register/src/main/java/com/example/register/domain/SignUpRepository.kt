package com.example.register.domain

import com.example.core.Result

interface SignUpRepository {

    suspend fun signUp(email:String,name:String,password:String): Result<Unit>
}