package com.example.movies.model

data class MoviesResult(var dates:Any, var page:Int, var results:List<MovieDetail>, var total_pages:Int, var total_results:Int )
