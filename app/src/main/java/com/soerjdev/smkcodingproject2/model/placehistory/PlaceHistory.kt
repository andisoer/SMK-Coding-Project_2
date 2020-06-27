package com.soerjdev.smkcodingproject2.model.placehistory

data class PlaceHistory (
    var placeName :String,
    var placeAddres: String,
    var placeCategory: String,
    var placeDate: String,
    var uid: String
){
    constructor(): this("","","","", "")
}