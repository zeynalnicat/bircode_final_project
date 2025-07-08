package com.example.signin.domain

import com.example.core.Result

interface LoginRepository {

    suspend fun login(email:String,password:String): Result<Unit>
}