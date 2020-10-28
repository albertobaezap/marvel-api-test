Marvel API Test Project
======

Simple Marvel Character viewer using [Marvel public API](https://developer.marvel.com/) written in Kotlin.

The project's architecture uses MVVM pattern with Architecture Components from Android.

Support libraries:
* [Retrofit2](https://square.github.io/retrofit/) with coroutine support for API communication.
* [Kodein](https://docs.kodein.org/kodein-di/7.1.0/index.html) for dependency injection.
* [Picasso](https://square.github.io/picasso/) for image loading.
* [Timber](https://github.com/JakeWharton/timber) for logging purposes.

Also I'm using [Kotlin coroutines](https://developer.android.com/kotlin/coroutines), [Navigation components](https://developer.android.com/guide/navigation) and [Data binding](https://developer.android.com/topic/libraries/data-binding)

App's package skeleton is divided in data (repository/state function), di (for dependency injection), model (data models), network (all network/Retrofit stuff) and ui (activities/adapters).

**Data layer**
Since we have to download a big number of characters in a "paged" way (maximum 100 per request) I decided it was better to have a single instance of the character list in order to survive the whole lifecycle of the App. 
I created a pseudo-Store class with the single purpose of populating and storing the list and provide it to the ViewModels instead of retrieving the list from there.

**DI layer**
All the dependency injection here using Kodein. I divided the modules between Activity (anything UI-related), Network (mainly retrofit) and ViewModels (and their factory methods).

**Model layer**
All the data classes for the API, which I preferred to create manually instead of using a POJO-generation plugin since I'm only going to use a small portion of the API.

**Network layer**
We only have the API service here. It was created the coroutines version of Retrofit since I'm more comfortable using them than having to import RX or use callbacks.
Even though I implemented both GET methods, I'll only be using one of them.

**UI layer*
Divided between adapters and main (fragments and viewmodels).
For adapters, I'll be using a single list adapter to display all the character list with the name and a thumbnail of the character, which is generated dynamically.
Also I used a generic ExpandableListAdapter which is a pretty way of displaying all the comic, events, etc... 
For the main/detail fragments, I used databinding and Picasso to paint all the images/data in the layout.
ViewModels are pretty simple this time since we have the Store in the data layer to handle the repository function but will be there for architecture purposes.

___

**Install**

Since I decided to leave my credentials properly hidden in my local environment, in order to install and execute the APK from the source code, you'll need to add your [API credentials](https://developer.marvel.com/account) to the `local.properties` file.
```
PUBLIC_KEY="XXXXXXXXXXXXXXXXXXXXXXXXXXX"
PRIVATE_KEY="XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
```
The project will use these fields to generate the hash that sould go in all the network requests. You can see the configuration in the buildTypes category from the app's build.gradle file. 