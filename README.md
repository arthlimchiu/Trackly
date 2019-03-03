# Trackly

A master-detail Android app that fetches a list of tracks from Itunes Search API.

<img src="https://user-images.githubusercontent.com/11973681/53696972-294b3a80-3e07-11e9-99f6-676c8a0c2cfb.jpg" height="512" width="280"><img src="https://user-images.githubusercontent.com/11973681/53696967-f86b0580-3e06-11e9-8c8e-d76db9560a8f.png" height="320" width="512">

## Persistence Features
* A representation of when the user last visited
<img src="https://user-images.githubusercontent.com/11973681/53697054-1127eb00-3e08-11e9-93fa-105af78ad95f.jpg" height="128" width="480">

* Restore the last screen the user opened
* Offline support for when there is no internet connection

## Clean Architecture + MVVM as Architecture
* **Scalability** - easy to add, remove and change features with minimal to no effects to other parts of the app. Less prone to regression bugs. For example, adding a feature is as simple as adding a Use Case and re-using or adding a new class and plug in that feature right away.
* **Maintainability** - ability to work on each layer with minimal to no effects to other layers of the app. For example, if you're working on things related to data/storage you'll just spend most of your time in the data layer/module with less concern for other layers.
* **Separation of concerns** - ability to change something with less code. For example, we have a new source for getting the tracks. We will just focus our time in the data module without even touching the app module because all the app module wants is a list of trick it doesn't matter how we get it in the data module.

## UI and Design:
* Portrait mode
<img src="https://user-images.githubusercontent.com/11973681/53696972-294b3a80-3e07-11e9-99f6-676c8a0c2cfb.jpg" height="512" width="280">

* Landscape mode to demonstrate master-detail approach for Android tablets
<img src="https://user-images.githubusercontent.com/11973681/53696967-f86b0580-3e06-11e9-8c8e-d76db9560a8f.png" height="320" width="512">

## Libraries/Dependencies
* Retrofit2
* RxJava2
* Room
* ThreeTenABP
* Glide
* Dagger2
* Gson
* ViewModel
* LiveData
