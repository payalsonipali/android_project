package com.example.movies

object Constants {
     val BASE_URL = "https://api.themoviedb.org/3/"

     val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDg5OWM5ZmMyNDVhYTJiOTY0MTExYjEyN2Y3NDljNSIsInN1YiI6IjY0ZWI2ZmU5MWZlYWMxMDExYjJlNWU0YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EJxvTefdqA75fzV-LMYfzTw2ZwzZ11okZlzsf_a6Ics"

     val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"

     val mapOfMovieCategory = mapOf(
          "Now Playing" to "now_playing",
          "Popular" to "popular",
          "Top Rated" to "top_rated",
          "Upcoming" to "upcoming"
     )

     val items = listOf("Now Playing", "Popular", "Top Rated", "Upcoming")

     val SERVER_CLIENT_ID = "991389426279-ve4dbj3ve4j6hp3tahn6eo39rbchrbq4.apps.googleusercontent.com"
}

