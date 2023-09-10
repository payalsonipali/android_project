# android_project

In this project i integrated Nasa image of the day api with caching. Following steps used-

**1.API Integration:**
- Generated api key for api calls
- Used retrofit for api call

**2.UI:**
- Displaying image and related details in UI.
- Used data binding to bind data on UI.
- If there will be any video then it will have some button on it ad on button click it will open the video
For image -
![photo_2023-09-10_14-37-24](https://github.com/payalsonipali/android_project/assets/45533629/b3b7b3c2-9de5-433b-8016-512f074be6fc)

For video -
![photo_2023-09-10_14-38-23](https://github.com/payalsonipali/android_project/assets/45533629/24ed05a5-765c-4f87-addc-4f72acdb70a2)

**3.Functionality:**
- Loading daily image on app launch or user refresh.
- Handled video content with thumbnail image and play button.

**4.Caching:**
- Implemented local caching using shared preference because there is no much data to store.

**5.Error Handling:**
- Created a sealed class to check success or error and pass error on request failure and toast error to ui

**6.Code Quality:**
- Used MVVM architecure for this project.
