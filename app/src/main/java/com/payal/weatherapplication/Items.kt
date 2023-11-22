package com.payal.weatherapplication

import com.fasterxml.jackson.annotation.JsonProperty

data class Root(
    val location: Location,
    val forecast: Forecast,
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @JsonProperty("tz_id")
    val tzId: String,
    @JsonProperty("localtime_epoch")
    val localtimeEpoch: Long,
    val localtime: String,
)

data class Forecast(
    val forecastday: List<Forecastday>,
)

data class Forecastday(
    val date: String,
    @JsonProperty("date_epoch")
    val dateEpoch: Long,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>,
)

data class Day(
    @JsonProperty("maxtemp_c")
    val maxtempC: Double,
    @JsonProperty("maxtemp_f")
    val maxtempF: Double,
    @JsonProperty("mintemp_c")
    val mintempC: Double,
    @JsonProperty("mintemp_f")
    val mintempF: Double,
    @JsonProperty("avgtemp_c")
    val avgtempC: Double,
    @JsonProperty("avgtemp_f")
    val avgtempF: Double,
    @JsonProperty("maxwind_mph")
    val maxwindMph: Double,
    @JsonProperty("maxwind_kph")
    val maxwindKph: Double,
    @JsonProperty("totalprecip_mm")
    val totalprecipMm: Double,
    @JsonProperty("totalprecip_in")
    val totalprecipIn: Double,
    @JsonProperty("avgvis_km")
    val avgvisKm: Double,
    @JsonProperty("avgvis_miles")
    val avgvisMiles: Double,
    val avghumidity: Double,
    val condition: Condition,
    val uv: Double,
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Long,
)

data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    @JsonProperty("moon_phase")
    val moonPhase: String,
    @JsonProperty("moon_illumination")
    val moonIllumination: Long,
)

data class Hour(
    @JsonProperty("time_epoch")
    val timeEpoch: Long,
    val time: String,
    @JsonProperty("temp_c")
    val tempC: Double,
    @JsonProperty("temp_f")
    val tempF: Double,
    @JsonProperty("is_day")
    val isDay: Long,
    val condition: Condition2,
    @JsonProperty("wind_mph")
    val windMph: Double,
    @JsonProperty("wind_kph")
    val windKph: Double,
    @JsonProperty("wind_degree")
    val windDegree: Long,
    @JsonProperty("wind_dir")
    val windDir: String,
    @JsonProperty("pressure_mb")
    val pressureMb: Double,
    @JsonProperty("pressure_in")
    val pressureIn: Double,
    @JsonProperty("precip_mm")
    val precipMm: Double,
    @JsonProperty("precip_in")
    val precipIn: Double,
    val humidity: Long,
    val cloud: Long,
    @JsonProperty("feelslike_c")
    val feelslikeC: Double,
    @JsonProperty("feelslike_f")
    val feelslikeF: Double,
    @JsonProperty("windchill_c")
    val windchillC: Double,
    @JsonProperty("windchill_f")
    val windchillF: Double,
    @JsonProperty("heatindex_c")
    val heatindexC: Double,
    @JsonProperty("heatindex_f")
    val heatindexF: Double,
    @JsonProperty("dewpoint_c")
    val dewpointC: Double,
    @JsonProperty("dewpoint_f")
    val dewpointF: Double,
    @JsonProperty("will_it_rain")
    val willItRain: Long,
    @JsonProperty("chance_of_rain")
    val chanceOfRain: Long,
    @JsonProperty("will_it_snow")
    val willItSnow: Long,
    @JsonProperty("chance_of_snow")
    val chanceOfSnow: Long,
    @JsonProperty("vis_km")
    val visKm: Double,
    @JsonProperty("vis_miles")
    val visMiles: Double,
    @JsonProperty("gust_mph")
    val gustMph: Double,
    @JsonProperty("gust_kph")
    val gustKph: Double,
    val uv: Double,
)

data class Condition2(
    val text: String,
    val icon: String,
    val code: Long,
)

