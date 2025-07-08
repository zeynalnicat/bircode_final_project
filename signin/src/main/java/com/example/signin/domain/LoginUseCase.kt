package com.example.signin.domain

import com.example.core.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(email:String,password:String): Result<Unit>{
        return loginRepository.login(email,password)
    }
}