package com.example.imagesearchapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashResponse(
    val results: List<UnsplashResults>
) : Parcelable {

    @Parcelize
    data class UnsplashResults(
        val id: String,
        val description: String?,
        val user: UserDetail,
        val urls: PhotoUrl,
    ): Parcelable

    @Parcelize
    data class UserDetail(
        val username: String,
        val name: String,
        val first_name: String,
        val last_name: String,
        val profile_image: ProfileImage
    ): Parcelable{
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }

    @Parcelize
    data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
    ): Parcelable

    @Parcelize
    data class PhotoUrl(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ): Parcelable
}