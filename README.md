# YT-Downloader
Website that you can download any video from YouTube. It is .mp4 and .mp3 format.

You can only download one video at a time.

Every request is checked for spamming and if something went wrong in the checks then it will block it automaticly.

You can use your own website if you have one, but the config will work only for those files:
* ```web/index.html```
* ```web/app.js```
* ```web/progress.js```
* ```web/style.css```

Backend endpoints are only custom for frontend. Here are the backend default endpoints:
* ```/downloader```  ->  GET  when the client wants the download stats of a video
* ```/download```    ->  POST  when the client send a video to be downloaded on the server-side
* ```/ask```         ->  GET  twhen the client wants to download the video
* ```/```          ->  GET  returns the web/index.html content as string
* ```/app.js```    -> GET  returns the web/app.js  content as a string
* ```/progress.js```    -> GET  returns the web/progress.js  content as a string
* ```/style.css```    -> GET  returns the web/style.css  content as a string

### Frameworks and Tools
* ```Spring Boot 3```
* ```JavaTube```
* ```Docker```

### Build and Run (Java 17 required)
# First Build then Run

# Note: If you have changed anything in the build.gradle then it is better to change te RUN command to the and of the JAR file thet gradle builds. Go to app/build/libs  and search for NAME-Version.jar . Thats the name of it.

Build:
```
YT-Downloader> chmod +x *
YT-Downloader> gradlew clean build
```

Run:
```
YT-Downloader> gradlew bootRun
```

OR

```
YT-Downloader> java -jar /app/build/libs/app-0.1.jar
```

