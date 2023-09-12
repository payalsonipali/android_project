package com.example.movies.model

data class MovieDetail(var id:Long, var original_title:String, var overview:String, var poster_path:String, var release_date:String, var vote_average:Float){
}

fun MovieDetail.toBeer(): MovieDetail {
    return MovieDetail(
        id = id,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        vote_average = vote_average
    )
}