# tinycinema

This app is being created as part of the Udacity Android NanoDegree.
It now uses 4 moviedb api's to pull the highest rated, most popular movies and the corresponding trailers and reviews.
You can save your favourites to your library to view when not online.

I originally used the Android architecture sample [todo‑mvp‑clean](https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/),
as a way to learn and experiment with creating custom callbacks using interfaces. 
I refactored majority of the project to use architecture components, Room, Livedata and Viewmodels.

I still make use of a method of extracting the view by creating a viewFactory, learnt by Vasiliy Zukanov in his Udemy [course](https://www.udemy.com/dependency-injection-in-android-with-dagger/)
on implementing dependency injection with Dagger2. 

To insert your own Api key from the moviedb go to the RemoteRepositoy class in the Data package.

### Architecture

Clean,
MVP,
Architecture components,
Repository pattern,
Dagger2

## Built With

* Android

## Versioning

1

## Authors

* **Shannon Ferguson**  


## Acknowledgments

* Android Architecture BluePrints [todo‑mvp‑clean](https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/)
* Vasiliy Zukanov: [Udemy course](https://www.udemy.com/dependency-injection-in-android-with-dagger/)
* Udacity
* https://www.themoviedb.org/
* [Codelab for architecture components](https://codelabs.developers.google.com/codelabs/build-app-with-arch-components/index.html#15)
