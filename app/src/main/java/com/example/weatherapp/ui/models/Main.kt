package com.example.example

import com.google.gson.annotations.SerializedName


data class Main (

  @SerializedName("temp"       ) var temp      : Double? = 0.0,
  @SerializedName("feels_like" ) var feelsLike : Double? = 0.0,
  @SerializedName("temp_min"   ) var tempMin   : Double? = null,
  @SerializedName("temp_max"   ) var tempMax   : Double? = null,
  @SerializedName("pressure"   ) var pressure  : Int?    = null,
  @SerializedName("humidity"   ) var humidity  : Int?    = null

)