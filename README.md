# Leboncoin Technical Test
The project follows Clean Architecture with the MVVM presentation pattern, utilizing Kotlin Coroutines and Flow for asynchronous operations. Hilt is used for dependency injection, ensuring that all dependencies are correctly provided at runtime.
UI tests are implemented to validate user interactions and visual elements, while unit tests cover the domain and presentation layers to ensure business logic correctness.

## 📄 Description
A Simple Android application that fetchs and display a list of albums from an given endpoint via the provided API in JSON format. Must be implement a data persistence system so that the data can be available offline, even after restarting the application.

#### Requirements
- The minimum SDK should be 24.
- API endpoint: https://static.leboncoin.fr/img/shared/technical-test.json

## ⭐ Features
- **Albums Screen**: Displays a list of album items with a title and image from the Leboncoin API.
- **Offline Support**: Albums are persisted in a local database to ensure availability offline.

## 📸 Screenshots

<img src="https://github.com/user-attachments/assets/630dfca0-3199-4698-99e6-9b83ce347914" alt="Description" width="900">

## 📦 APK
Download the APK directly [here](https://github.com/tiagodiasduarte/) to test the application immediately.

## 📚 Libraries
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)
- [Jetpack Compose](https://developer.android.com/compose)
- [Retrofit](https://square.github.io/retrofit/)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Coil](https://coil-kt.github.io/coil/)

## 🧪 Testing Libraries
- [Junit4](https://junit.org/junit4/)
- [Truth](https://truth.dev/)
- [Mockk](https://mockk.io/)
- [Turbine](https://github.com/cashapp/turbine)

## ✅ Tasks
The tasks for this project are detailed in the associated Pull Request.

- [[LAC-1] Add dager-hilt for dependency injection](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/1)
- [[LAC-2] Albums data layer](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/2)
- [[LAC-3] Error Handling Support](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/3)
- [[LAC-4] Add Fetch Albums Worker](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/4)
- [[LAC-5] Albums screen](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/5)
- [[LAC-6] Paging 3 integration](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/6)
- [[LAC-7] Fetch albums from ViewModel](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/7)
- [[LAC-8] Add Error Dialog to AlbumsScreen](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/8)
- [[LAC-9] Rename FetchAlbumsUseCase ](https://github.com/tiagodiasduarte/leboncoin-android-challenge/pull/9)

## 📝 Choices made

#### Clean Architecture
Clean Architecture was chosen to ensure that the app is scalable, maintainable, and easy to test. By following the separation of concerns principle, Clean Architecture allows us to isolate the business logic from the presentation layer, making the app more modular and testable.
The app follows the Clean Architecture layers:
- **Data Layer:** Handles data operations, including network communication and local storage (Room database).
- **Domain Layer:** Contains business logic and use cases, which can be easily reused or extended.
- **Presentation Layer (UI):** Built with the MVVM (Model-View-ViewModel) pattern, the ViewModel interacts with the domain layer to retrieve data and update the UI.


#### MVVM (Model-View-ViewModel)
MVVM was chosen for the presentation layer to separate the UI logic from the business logic and make the UI more declarative and testable with Jetpack Compose. MVVM helps in handling the UI state efficiently, especially when dealing with data changes and user interactions.

#### Room Database
Room was selected as the local database solution because it provides an abstraction layer over SQLite, making database operations more efficient and easier to manage with a clean API. It also integrates seamlessly with Kotlin coroutines and Flow for asynchronous operations. The Room database is used to persist albums locally, allowing the app to function offline.

#### Paging Library
Paging 3 was implemented to handle large datasets (albums) efficiently. It supports pagination and helps to load data directly from the Room database, preventing excessive memory usage and improving the app’s performance.

#### Testing
To ensure the application is reliable, maintainable, and performs as expected, the following decisions were made regarding testing:
- **Unit Tests:** Focused on testing individual components in isolation, such as: ViewModel, UseCases and Repository.
- **UI Tests:** Implemented UI tests with Jetpack Compose Test APIs to validate the behavior and appearance of the screens.

**Mocking Dependencies:** Mocked API responses to simulate network scenarios such as success and error.

Testing Libraries:
- Adopted **JUnit4** as the primary testing framework for unit tests.
- Used **Turbine** for testing Kotlin Flow to ensure reactive streams behave as expected.
- Used **MockK** for mocking dependencies to simulate various scenarios.
- Incorporated **Truth** for more expressive and readable assertions.


## ⚠️ Known issues
1. **Placeholder Image Not Available:** The placeholder images used in the app (from via.placeholder.com) are currently unavailable.

## 🔮 Future Improvements
1. **Modularization**: Introduce modularization to improve scalability, maintainability, and team collaboration. By separating the app into feature-specific and shared modules (e.g., Core, Feature, Data), we can enhance build times, enable code reuse, and ensure a cleaner architecture. This approach will simplify scaling and streamline development.

2. **Observe Internet Connection**: Implement a robust mechanism to monitor internet connectivity changes using APIs like **ConnectivityManager**. This would allow the app to dynamically handle online/offline states, show appropriate UI feedback, and synchronize data when the connection is restored.

3. **Adaptive Layouts**: Further enhance the app’s responsiveness by refining the adaptive layouts to better handle a wider range of screen sizes, orientations, and resolutions, ensuring a seamless experience across all devices, including foldable phones and tablets

4. **Build system**: Enhance the build system by automating tasks with custom Gradle scripts, streamline the CI/CD pipeline for faster builds and more efficient deployment, and optimize build performance by managing dependencies and build configurations more effectively.
