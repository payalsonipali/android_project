# movie_browsing_project

An Android Movie browsing application coded in Kotlin
- UI using Jetpack Compose. 
- Room db.
- MVVM or Clean Architecture
- Dependency injection using Hilt android.
- Retrofit
- Android navigation component.
- ViewModel
- Stateflow

**Screens :**

**Authentication Screen**: Supports social sign in. (Google) and email/password using Firebase Auth.

**Movie List Screen**: 
- Shows 4 filter chips on top. Now playing, Popular, Top rated, Upcoming
- Default shows Now playing movies & upon selection of other chips, the list changes.
- Movies need to be paginated & fetched 20 at a time.

**Movie Detail Screen**:
- Shows information about a selected movie, including its title, synopsis, release date, cast.
- Shows a horizontal list of cast members with image & name.
- Shows a backdrop image on top of the screen & a movie poster. On click of backdrop image, 
  The Image screen needs to be displayed.
- Option to mark as favorite/unfavorite. 
- Favorite movies need to be stored inside a local db.

**Movie Images Screen**: Shows movie images in a grid having 4 columns.

**Trailer Screen**: Plays a trailer inside an in-app youtube player.

**Favorites Screen**: 
- Displays a list of favorite movies from the local db.
- UI needs to be exactly the same as Movie List Screen. In other words, reuse the UI components.
- Upon clicking an item, the Movie Detail Screen opens.
- Also, when a movie is marked as favorite, this list needs to be auto-updated.

**Profile Screen**:
- Displays name, avatar, email from firebase auth.
- Option to logout.

![image](https://github.com/payalsonipali/android_project/assets/45533629/c8bcd2b8-40e8-4672-9025-1e69b3a38f0c)

![image](https://github.com/payalsonipali/android_project/assets/45533629/e63a1725-161a-4210-8f40-f6dcb90eb23c)

![image](https://github.com/payalsonipali/android_project/assets/45533629/ad46423c-b614-4116-b1cc-65d98d8c3b11)





