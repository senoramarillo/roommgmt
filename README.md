# Project "Room Management"

<img src="https://i.imgur.com/qFK95sZ.png" width="250"  alt=""/>

[![Build Status](https://app.travis-ci.com/senoramarillo/roommgmt-react.svg?branch=main)](https://app.travis-ci.com/senoramarillo/roommgmt-react)
[![codecov](https://codecov.io/gh/senoramarillo/roommgmt-react/branch/main/graph/badge.svg?token=WG1PB5HL92)](https://codecov.io/gh/senoramarillo/roommgmt-react)

# Demo
[Room-Management](https://bit.ly/35Dz8Oy)

[Swagger UI](https://bit.ly/3cWyU9g)

[Docker Hub](https://hub.docker.com/r/senoramarillo/roommgmt-react)

# Architecture
<img src="https://i.imgur.com/h6e6k95.png" width="350px"  alt=""/>

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=senoramarillo_roommgmt-react)

<img src="https://i.imgur.com/OAwZXKy.png" width="800px"  alt=""/>

<img src="https://i.imgur.com/p60XyxC.jpg" width="850px"  alt=""/>

[Spring Boot](https://spring.io/projects/spring-boot) version 3.0.0

# Git structure
* [./src/](src) - Source code

# Project description

This is the basic project for the "Room Management". The goal of this exercise is to apply the content taught in the previous
courses on the topics of architecture and development with the Spring framework. In particular
the following technologies in particular:

* Spring Framework
    * Spring Boot
    * Spring REST
    * Spring Data JPA
* JPA (Hibernate)
* Unit-Tests with JUnit, Mockito und AssertJ

## Functional requirements

The final application should offer classic master data processing (CRUD) including some search operations via a
REST API.

The domain model consists of three entities:
* `Building`: Represents a building.
* `Room`: Represents a room. This is associated with a building.
* `Meeting` (optional): Represents a meeting/lecture/event and is associated with a room.
The required attributes of the entities are listed below.

### Entity Building

A building has the following natural attributes:
* Building number
* Optional description
* A flag whether public access is possible.

| Attribute       | Properties               |
|-----------------| --------------------------- |
| Building Number | `String`, required, unique  |
| Description     | `String`, optional          |
| Public Access   | `Boolean`, required         |

### Entity Room

A room has the following natural attributes:
* Building
* Room number
* Number of seats
* A flag whether a beamer is available

| Attribute         | Properties                            |
|-------------------| ---------------------------------------- |
| Building          | `Building`, required                     |
| Room number       | `String`, required                       |
| Seats             | `Integer`, required, zwischen 1 und 9999 |
| Projector present | `Boolean`, required                      |

In addition, the application must ensure that the combination of *building* and *room number* is unique.

### Entity Meeting

A meeting has the following natural attributes:
* Room
* Subject
* Start and end time

| Attribute | Properties       |
|-----------| ------------------- |
| Room      | `Room`, required    |
| Topic     | `String`, required  |
| Start     | `Instant`, required |
| End       | `Instant`, required |

### Required functions

The application shall provide the following functions via a REST API:

* Buildings
  * List all buildings
  * Create, modify and delete a building
  * Search a building by building number
  * Search all buildings with public access
* Room
  * List all rooms
  * Create, modify and delete a room
  * Search a room by building number and room number
  * Search all rooms in a building
  * Search all rooms with public access
* Meeting
  * List all meetings
  * Create, modify and delete a meeting
  * Search all meetings in a specific period (from, to)
  * Search all meetings in a building in a specific time period (from, to)
  * Search all meetings in a room in a specific time period (from, to)

## Technical requirements

In addition to the functional requirements, the technical requirements listed below must be met.
must be met.

### REST API

The application must provide a REST API that follows common REST standards.
Error handling is done through the use of HTTP status codes.

### Layer architecture

The application must implement a three-tier architecture:

```
  A[REST Controller] --> B[Service];
  B --> C[Repository];
```

The individual layers are to be developed on the basis of the following technologies:

* REST Controller
    * Spring REST Controller (Spring MVC)
* Service
    * Standard Spring Framework
* Repository
    * Spring Data JPA
    * Hibernate
* Database
    * H2

It is permitted to use complementary technologies as long as they do not replace the technologies listed above or bypass the
or circumvent the three-layer architecture.

### Transaction handling

Clean transaction handling is required. In particular, it is important to set the transaction bracket in the correct
correct layer (*Unit of Work*).

### Testing

Unit tests are ideally implemented in each layer.

However, for the purposes of this exercise, the implementation of unit tests is only mandatory for the *Controllers* and the
*Services* are mandatory. Full test coverage of all methods is not required for this exercise.

Optionally, unit tests can of course also be built for the *Repositories*.

## Application testing

Testing of the application is possible at any time locally in the IDE. To do this, simply start the Spring boot application.
IntelliJ automatically generates a run configuration for this purpose. Alternatively, you can also click on the *Play* icon in
the main class `com.spring.roommgmt.RoomManagementApplication`.

Testing the REST API is best done using *HTTP scratch files*. These can be created directly in the IntelliJ
application.

## Available Scripts for React Frontend

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.
