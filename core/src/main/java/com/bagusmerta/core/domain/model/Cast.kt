package com.bagusmerta.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    var character: String?,
    var name: String?,
    var profilePicPath: String?

): Parcelable
