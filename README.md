# Coding puzzle - Toy Robot

## Problem

**Code problem details:**
* The application is a simulation of a toy robot moving on a square tabletop, of
dimensions 5 x 5 units.
* There are no other obstructions on the table surface.
* The robot is free to roam around the surface of the table, but must be prevented from
falling to destruction. Any movement that would result in the robot falling from the table must
be prevented, however further valid movement commands must still be allowed.


**Create an application that can read in commands of the following form:**
* PLACE X,Y,F
* MOVE
* LEFT
* RIGHT
* REPORT

**PLACE** will put the toy robot on the table in position X,Y and facing NORTH,
SOUTH, EAST or WEST. The origin (0,0) can be considered to be the SOUTH WEST
most corner.
**MOVE** will move the toy robot one unit forward in the direction it is currently facing.
**LEFT** and RIGHT will rotate the robot 90 degrees in the specified direction without
changing the position of the robot.
**REPORT** will announce the X,Y and F of the robot.

**Constraints:**
* The application must be a Spring-Boot-Application
* Input must be realised over the REST-API, take care when designing the REST-API
* The robot that is not on the table can choose the ignore the MOVE, LEFT, RIGHT
and REPORT commands.
* The robot must not fall off the table during movement. This also includes the initial
placement of the toy robot.
* Any move that would cause the robot to fall must be ignored.
* It is not required to provide any graphical output showing the movement of the toy
robot.

**Plain input Examples:**

```
PLACE 0,0,NORTH
MOVE
REPORT
Output: 0,1,NORTH
```

```
PLACE 0,0,NORTH
LEFT
REPORT
Output: 0,0,WEST
```

```
PLACE 1,2,EAST
MOVE
MOVE
LEFT
MOVE
REPORT
Output: 3,3,NORTH
```

```
MOVE
REPORT
Output: ROBOT MISSING
```

**Deliverables:**

* The whole project, example requests to test a robot-application in form of Postman-Collection or in form of a text file.

## Solution

**Idea:**
* The main resource is going to be `Game` - therefore we will have `api/games` endpoint
* According to the problem there is exactly one robot per game which is the subresource of a certain game and as there is no collections we will use the `api/games/{gameId}/robot` endpoint
* We expose actions on the robot according to the `Restful Objects Specification v1.1.0` paragraphs `20.3`, `20.4` 

**Assumptions:**
* Users do not share a single game but have a game per user or user might create several games
* Robot might be placed on the board more than once during the game
* Application explicitly notifies a consumer with a proper HTTP status and message when it cancels a move or a robot placement out of board boundaries
* Application should be stateless to be reliable and ready for horizontal scaling (and to be RESTful)
* Therefore we need a persistence layer and as our logic does not require multi-row transactions we can use NoSQL DB 
* The game will evolve so we need a good separation of the logic on the business logic layer (altough some of the services, e.g. `BoardService` have very basic functionality)
* For the same reason let's decouple services and components of business layer with interfaces

**Constraints:**
* Embedded Mongo is used as a NoSQL DB and games are not persisted in between applicaion starts
* As we have the very restricted set of tests we do not separate unit and integration tests with different profiles or modules
* Synchronization is provided for the operations changing a robot state but for report we provide a weaker guarantee so under concurrent use in might report not the very latest state  

**Installation:**
_Java and Apache Maven need to be installed in order to build the application._

* Build application: `mvn clean package`
* Run application: `java -jar target/puzzle-0.0.1.jar`

**API:**
Navigate using any browser to page [http://localhost:8080/swagger-ui.html#/game-controller](http://localhost:8080/swagger-ui.html#/game-controller) for Swagger-generated API specification.