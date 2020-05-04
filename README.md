# TDSExercise
Coding assignment

Introduction
------------

Display the alarm is triggered every time there is an
emergency inside the building.

Getting Started
---------------

This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

Screenshots
-----------
![City list screen](https://github.com/aniketmane/TDSExercise/blob/master/TDSExercise/screenshots/screenshot1.png "No Emergency")

![Weather details screen](https://github.com/aniketmane/TDSExercise/blob/master/TDSExercise/screenshots/screenshot2.png "Emergency")


Libraries Used
--------------

* [Architecture][0] - A collection of libraries that help you design robust, testable, and
  maintainable apps.
  * [LiveData][1] - Build data objects that notify views when the underlying database changes.
  * [ViewModel][2] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
* [UI][3] - Details on why and how to use UI Components in your apps - together or separate
* Third party
  * [Retrofit][4] for network operation
  * [RxKotlin][5] for managing background threads with simplified code and reducing needs for callbacks

[0]: https://developer.android.com/jetpack/arch/
[1]: https://developer.android.com/topic/libraries/architecture/livedata
[2]: https://developer.android.com/topic/libraries/architecture/viewmodel
[3]: https://developer.android.com/guide/topics/ui
[4]: https://github.com/square/retrofit
[5]: https://github.com/ReactiveX/RxKotlin
[6]: https://developer.android.com/guide/topics/ui

Android Studio IDE setup
------------------------
For development, the latest version of Android Studio is required. The latest version can be
downloaded from [here](https://developer.android.com/studio/).

Improvements 
-----------------
* Add Dagger injection for dependencies 
* Add Test Cases

Author
------------------------
* **Aniket Mane**
