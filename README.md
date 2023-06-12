# Moviee
This app is a movie browsing platform that follows MVVM and Clean Architecture principles. It features different movie categories, search function, and the ability to save favorite movies. Detailed movie pages provide comprehensive information about each film, including plot, cast, reviews, release date, and trailer.

| <img src="https://github.com/gusentanan/moviee/assets/68723002/7ba6fa80-d2db-4801-96b2-0d6232c1870d" alt="Onboarding 1" width="200"/> | <img src="https://github.com/gusentanan/moviee/assets/68723002/382ea392-7df6-40c8-a019-75971fde9f73" alt="Onboarding 2" width="200"/> | <img src="https://github.com/gusentanan/moviee/assets/68723002/f84ac455-a614-4238-9bca-76a189e61c8e" alt="Onboarding 3" width="200"/> | <img src="https://github.com/gusentanan/moviee/assets/68723002/f39451d3-580b-4bac-bddf-f20400a256d7" alt="Main Page" width="200"/>  |
| :--: | :--:| :--:| :--:|
| Onboarding 1 | Onboarding 2 | Onboarding 3 | Main page | 

| <img src="https://github.com/gusentanan/moviee/assets/68723002/1eda38a6-230f-489b-a530-198c5295f6be" alt="All Movie Page" width="200"/> | <img src="https://github.com/gusentanan/moviee/assets/68723002/fea28e99-8296-4282-a2c2-739e88b29a83" alt="Detail Page" width="200"/> | <img src="https://github.com/gusentanan/moviee/assets/68723002/3f7aca41-bb6e-44b9-9d0d-65f7a94485d5" alt="Favorite Page" width="200"/> | <img src="https://github.com/gusentanan/moviee/assets/68723002/ce1c469e-e311-4993-b9ad-a8dc81068444" alt="Search Page" width="200"/> |
| :--: | :--:| :--:| :--:|
| See all page| Detail page | Favorite page | Search page |

## Architecture
The architecture of this app is complies with each of 3 following points:
- [Model-View-ViewModel (MVVM)](https://proandroiddev.com/understanding-mvvm-pattern-for-android-in-2021-98b155b37b54) by utilizing the ViewModel in this architecture, you can achieve a more modular, testable, and maintainable codebase, with improved separation of concerns and a clear distinction between UI and business logic/data operations.
- [Modular app architecture](https://developer.android.com/topic/modularization) enables the development of features in isolation, independent of other features. This approach offers benefits such as reusability, scalability, maintainability, reduced app size, and easier modification or replacement of specific features. 
- [Clean Architecture](https://proandroiddev.com/kotlin-clean-architecture-1ad42fcd97fa) strictly emphasizes a clear separation of concerns through distinct architectural layers: Presentation/UI layer, Domain layer, and Data layer. This separation facilitates writing tests without dependencies on external frameworks or UI components, enhancing testability.

## Modules
![moviee drawio](https://github.com/gusentanan/moviee/assets/68723002/c195e347-59cc-4109-9735-a5a76b69e056)
<br></br>
Above graph shows the app modular structure.
- The `:app` module has a direct dependency on the `:core` module. Additionally, `:app` indirectly depends on the `:feature` module through library
- `:feature` modules depends on `:core`, and `utility`.
- Both `:core` and `:app` modules may have dependencies on the `:utility` module, but only if there is a need for specific utilities.
- The `:utility` module does not have any external dependencies.
<br></br>
This architecture promotes modularity and encapsulation by separating functionality into distinct modules. It allows for easier maintenance, reuse of modules across projects, and better management of dependencies between modules.

## Tech Stack:
- `Androidx`: provides a set of libraries and components that offer backward compatibility, enhanced functionality, and ease of development for Android apps.
- `Material`:  simplifies the process of creating visually appealing and consistent user interfaces.
- `LiveData`: allows you to store and observe data in a way that automatically updates the UI when the data changes.
- `LifeCycle`: offers lifecycle-aware components and callbacks that allow developers to handle common lifecycle events.
- `ViewModel`: allows you to store and manage data that is independent of the UI lifecycle, ensuring that data is retained even during screen rotations or other configuration changes.
- `RxJava`:  for doing asynchronous and executing event-based programs by using observable sequences.
- `Retrofit2`: construct the REST APIs and paging network data.
- `Room`: construct a DB by creating an abstract layer on top of SQLite, which enables smooth and effortless access to the database.
- `Glide`:  library for Android that simplifies the process of loading and displaying images from network.
- `Koin`: a dependency injection framework that facilitates the management and resolution of dependencies.
- `Timber`: a logger with a small, extensible API.
- `Shimmer`: simplifies the implementation of shimmering effects, enhancing the user experience and providing visual feedback during content loading.
- `Mockito`: mocking framework that allows for the creation of mock objects in unit testing.
- `JUnit` : simplifies the process of writing and running tests, allowing developers to verify the correctness of their code and catch potential issues early in the development cycle.
- and a lot more ...

## How to use it ?
Clone this repository and make sure you're using the latest version of Android Studio
### Running
- Run the project using `./gradlew build`
- Run unit tests using `./gradlew clean test`

-----------------------------------
Thanks for stopping by 😄
