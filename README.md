# RightMesh GPS Superpeer

This minimalist RightMesh "GPS Superpeer" implementation acts as node in the mesh that records GPS coordinates of it's peers, and provides a web interface that visualizes these coordinates as markers on a map.

## Running the Superpeer

Superpeer is a Gradle application - it can be built with `gradlew build`, and a binary can be generated with `gradlew installDist`. The binary can be found in `build/install/Superpeer/bin`.

## Connecting to the Superpeer

Currently apps that are running `https://code.leftofthedot.com/conorb/GPSTracker` hard code this SuperPeer's MeshID and send data to it through the RightMesh library.

## Environment

This was all tested with the following:
* macOSX High Sierra 10.13.6
* Java 8 (1.8.0_181)
* RightMesh library 0.9.0
* Mongo 4.0.2
* Node 10.12.0
    
#### Other Dependencies 
 - Dagger 2.17
 - Mongo Async Driver 3.8.2
 - JUnit 5.10
 - For JS dependencies see `src/main/web/package.json`
 
*See `build.gradle` for the latest*

## Running the Superpeer

Generate the binary with
`./gradlew installDist`

Start the mongo daemon with `mongod --dbpath <path/to/datastore>`

Run the Super with `./build/install/Superpeer/bin/Superpeer`

Run the web that marks the currently recorded GPS coords with `node app.js <here-app-id> <here-app-code>`
- You can create a HERE account [here](https://developer.here.com/plans?create=Freemium-Basic&keepState=true&step=account), or ask me for existing credentials


