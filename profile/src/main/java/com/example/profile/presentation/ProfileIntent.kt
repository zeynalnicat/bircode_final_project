package com.example.profile.presentation

import com.example.core.CoreIntent

sealed class ProfileIntent: CoreIntent {

    data object OnNavigateBack: ProfileIntent()
    data object OnGetProfileDetails: ProfileIntent()
}