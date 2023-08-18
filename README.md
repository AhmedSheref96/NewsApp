# News App
News Application For OLE

News App using https://newsapi.org/ Api.
I'm using  [postman collection](https://elements.getpostman.com/redirect?entityId=19417510-d67c72fb-5224-47bc-9aea-ca775aee486a&entityType=collection) to preview that easily.

## features
**Embedded Features :**
 - Search in News.
 - sorting by date.
 - Open in browser.


### News List Screen

| Dark Mode                                                    | Light Mode                                                   |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/screen1_dark.jpg" width="50%"> | <img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/screen1_light.jpg" width="50%"> |


### News Details Screen

| Dark Mode                                                    | Light Mode                                                   |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/screen2_dark.jpg" width="50%"> | <img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/screen2_light.jpg" width=50%"> |
| <img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/screen3_dark.jpg" width="50%"> | <img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/screen3_light.jpg" width="50%"> |


### caching data. 
[Caching Experment Video](https://github.com/AhmedSheref96/NewsApp/blob/master/screen_recording2.mp4)

**Coming Features**

- [ ] Sort news list according to user selection from date dialog picker.
- [ ] Get headlines news list according to user country selection.
- [ ] Handle empty status with [lottie](https://lottiefiles.com/) json files.
- [ ] Open in web view instead of open in browser.
- [ ] Using splash screen api.
- [ ] User registeration.
- [ ] User saved news with locale database.



## implementation
with modularization, I have build four  modules
1- Entities : that represents responses models.
2- Data : implement data collecting instructions.
3- Domain : implement usecases with repositories implementations and paging data sources.
4- UI : implement and display features ui with intents and view states.


## Build With
- Kotlin.
- Modularization.
- Clean Architecture.
- MVI.
- Use Cases.
- Navigation Component.
- And third-party libraries.
- Retrofit.
- Okhttp3.
- Glide.
  ....



<a href="https://play.google.com/store/apps/details?id=com.el3sas.newsapp" target="blank"><img align="center" src="https://github.com/AhmedSheref96/NewsApp/blob/master/store_img.png" alt="Get It On Store" height="60"/></a>

