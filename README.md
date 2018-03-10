# Announcement API
Simple API interface to collect LIKE/DISLIKE interactions as REST.

### Requirements
This project requires following software versions or higher in order to compile, package and execute the JAR.

```
JDK8 or later version
maven2 or later version
```

### Running JAR
Running JAR file can be done by executing following command. please make sure to set appropiate `spring.profiles.active` property depend on the environment you trying to run this JAR.

```
java -D"spring.profiles.active=staging" -jar target/announcementapi.jar co.tide.announcementapi.App
```

### REST endpoints
The REST endpoints are declared as follows to interact with this API.

##### POST rate for an announcement

Syntax to rate an announcement

**HTTP Method :** `POST`

**Endpoint    :** `http://localhost:8080/announcements/v1/:announcementId/rate`

***Required Parameters***
```
rating=[like|dislike]
```

Any other values otherthan `like or dislike` will result in `HTTP 400` as it would fail allowed validations

```
curl -X POST -d 'rating=dislike' http://localhost:8080//announcements/v1/1001/rate
```

***Response***
```
{"success":true}
```

##### Display announcement rate counters

Syntax to display counters

**HTTP Method :** `GET`

**Endpoint    :** `http://localhost:8080/announcements/v1/:announcementId/rate`

```
curl -X GET http://localhost:8080//announcements/v1/1001/rate
```

***Response***
```
{"LIKE":"0","DISLIKE":"1"}
```

### Application
This application based on `sparkjava` HTTP Framework to support endpoints and run embedded jetty web server. Currently application uses default port as `8080`, if needs to change port it can be easily configured by changing following config.
```
http.server.port=8080
```

### Tests
Running `UnitTests` and `IntegrationTests` can be easily achieve by executing following maven commands.

##### Unit Test
```
mvn clean test
```

##### Integration Test
```
mvn clean verify
```

#### Packaging
Fat JAR can be easily build by running following `maven` command.

```
mvn clean verify package
```