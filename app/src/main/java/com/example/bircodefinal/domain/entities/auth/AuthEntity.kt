package com.example.bircodefinal.domain.entities.auth

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Auth")
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val email:String ,
    val name:String,
    val password:String
)