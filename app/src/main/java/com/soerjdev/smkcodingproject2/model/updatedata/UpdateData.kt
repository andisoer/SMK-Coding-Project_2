package com.soerjdev.smkcodingproject2.model.updatedata


import com.google.gson.annotations.SerializedName
import com.soerjdev.smkcodingproject2.model.updatedata.update.Update

data class UpdateData(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("update")
    val update: Update
)