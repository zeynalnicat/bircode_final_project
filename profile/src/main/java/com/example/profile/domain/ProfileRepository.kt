package com.example.profile.domain

import com.example.core.Result

interface ProfileRepository {

    suspend fun getProfileDetails(): Result<ProfileModel>
}