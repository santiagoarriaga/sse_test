# sse_test
Spring Boot Server-Sent-Events ( SSE ) service with reactive Kafka producer and consumers.

## Install

- Install Java 5
- Clone and cd into this repository
- Run:

```
  mvn install
```

## Test locally

- Download and install kafka
- cd into kafka directory
- In a terminal, run zookeeper:

```
  bin/zookeeper-server-start.sh config/zookeeper.properties
```

- In another terminal, run kafka:

```
  bin/kafka-server-start.sh config/server.properties
```

- In another terminal, create a test topic:

```
  bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic thl-events
  bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

- Cd into the sse_test repository, and run the server:

```
  mvn exec:java -Dexec.mainClass="test.Application"
```

- Open one or several browser tabs pointing to http://localhost:8080/

- Use the HTML UI to send and see how messages are received in all of them,
as messages are multicasted.
