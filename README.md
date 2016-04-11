# avaje-ignite-server
Apache Ignite server as runnable jar with external configuration (via properties)


## 1. Download (or create) the  runnable jar

Download the runnable jar from maven central.  Alternatively clone and build it via `mvn clean package`

## 2. configure ignite.properties

Create an `ignite.properties` file in the local directory and configure it.  It can be empty and then all defaults are used.

## 3. configure logback.xml

Create a `logback.xml` file in the local directory and configure it. If not supplied a default one is used.

## 4. run it

```sh
java -jar avaje-ignite-server-1.1.1.jar
```
