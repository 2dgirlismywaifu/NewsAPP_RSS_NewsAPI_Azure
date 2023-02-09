package com.notmiyouji.newsapp.kotlin.UpdateModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserName {
    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}