package com.example.profile.domain

import com.example.core.Result
import javax.inject.Inject

class GetProfileDetailsUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(): Result<ProfileModel>{
        return profileRepository.getProfileDetails()
    }
}