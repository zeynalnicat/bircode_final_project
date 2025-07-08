package com.example.register.domain

import com.example.core.Result
import javax.inject.Inject


class SignUpUseCase @Inject constructor(val repository: SignUpRepository) {

    suspend operator fun invoke(email:String,name:String,password:String): Result<Unit>{
         return  repository.signUp(email,name,password)
    }
}