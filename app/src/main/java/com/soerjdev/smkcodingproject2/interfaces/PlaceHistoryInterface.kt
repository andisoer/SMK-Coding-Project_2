package com.soerjdev.smkcodingproject2.interfaces

import com.soerjdev.smkcodingproject2.model.placehistory.PlaceHistory

interface PlaceHistoryInterface {

    fun deletePlaceHistory(uid: String)
    fun updatePlaceHistory(placeHistory : PlaceHistory)

}