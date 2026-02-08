# Gatling-Based Performance Testing Framework

A comprehensive performance testing framework built using **Gatling (Java DSL)** to evaluate scalability, reliability, and throughput of real-world HTTP APIs under different traffic models.

This project demonstrates how modern asynchronous, non-blocking load testing can be used to simulate **thousands of concurrent users** and identify system bottlenecks before production failures occur.

---

## Table of Contents

- [Problem Statement](#problem-statement)  
- [Why Gatling](#why-gatling)  
- [What This Framework Measures](#what-this-framework-measures)  
- [Project Structure](#project-structure)  
- [Simulation Coverage](#simulation-coverage)  
- [Load Models Implemented](#load-models-implemented)  
- [How to Run](#how-to-run)  
- [Reports & Results](#reports--results)  
- [Key Engineering Concepts](#key-engineering-concepts)  
- [Academic & Professional Value](#academic--professional-value)  
- [Limitations](#limitations)  
- [Future Enhancements](#future-enhancements)  
- [Contributing](#contributing)  
- [License](#license)  
- [Contact](#contact)

---

## Problem Statement

Modern backend systems often perform well under small loads but fail unpredictably at scale:

- 1 user → system works  
- 10 users → still stable  
- 10,000 users → unknown  
- 50,000 users → production incident  

Traditional load testing tools rely on **one thread per user**, which causes the load generator itself to collapse before realistic traffic is achieved.

This project addresses that problem using **Gatling’s event-driven architecture**, allowing a small number of threads to simulate massive concurrent traffic.

---

## Why Gatling

Gatling uses:

- Asynchronous execution  
- Non-blocking I/O  
- Event-driven scheduling  

This enables:

- Few threads → thousands of virtual users  
- High accuracy performance metrics  
- No artificial bottlenecks from the load generator  

In short: **the system under test fails first, not the test tool.**

---

## What This Framework Measures

Each simulation captures:

- Response time distribution  
- Requests per second (throughput)  
- Error rate thresholds  
- Bottleneck endpoints  
- System behavior under sustained and spike loads  

All results are automatically generated as **HTML performance reports**.

---

## Project Structure

```
Gatling_Tests/
│
├── pom.xml
├── mvnw / mvnw.cmd
│
├── src/test/java/example/
│   ├── BasicSimulation.java
│   ├── BasicSimulation10.java
│   ├── BasicSimulation100.java
│
│   ├── Test_2_ProductsList.java
│   ├── Test_3_ProductsList_100.java
│   ├── Test_4_ProductsList_Response_100.java
│   ├── Test_5_Products_Auth_100.java
│
│   ├── Test_6_Session_Products.java
│   ├── Test_7_PauseRequest.java
│   ├── Test_8_TripleRequests.java
│
│   ├── Test_9_CRUD_JSONPlaceholder.java
│
│   ├── Test_A1_CSV_Response.java
│   ├── Test_A2_Feeder_FakeStore.java
│   ├── Test_A3_Feeder_GitHub.java
│   ├── Test_A4_AuthToken.java
│   ├── Test_A5_SpikeTest.java
│   └── Test_A6_RandomSwitch.java
│
└── target/gatling/
    └── HTML performance reports
```

---

## Simulation Coverage

### Core Load Tests

| Simulation | Purpose |
|-----------|--------|
| BasicSimulation | Single-user baseline |
| BasicSimulation10 | Low concurrency validation |
| BasicSimulation100 | High concurrency stability |

### API Behavior & Pagination

| Simulation | Focus |
|-----------|------|
| Test_2_ProductsList | Product listing |
| Test_3_ProductsList_100 | Same endpoint under load |
| Test_4_ProductsList_Response_100 | Response body extraction |

### Authentication & Headers

| Simulation | Focus |
|-----------|------|
| Test_5_Products_Auth_100 | Bearer token authorization |
| Test_A4_AuthToken | Login → token → authenticated calls |

### Sequential & Realistic User Flow

| Simulation | Focus |
|-----------|------|
| Test_6_Session_Products | Sequential requests |
| Test_7_PauseRequest | Random think-time |
| Test_8_TripleRequests | Multi-step workflows |

### Data-Driven Testing

| Simulation | Focus |
|-----------|------|
| Test_A1_CSV_Response | Save responses to CSV |
| Test_A2_Feeder_FakeStore | Dynamic product IDs |
| Test_A3_Feeder_GitHub | GitHub repo validation |

### Advanced Models

| Simulation | Focus |
|-----------|------|
| Test_A5_SpikeTest | Sudden traffic spike |
| Test_A6_RandomSwitch | Mixed traffic patterns |

---

## Load Models Implemented

- Constant load  
- Ramp-up users  
- Spike testing  
- Randomized traffic  
- Sequential workflows  
- Authenticated sessions  
- Data-driven requests  

---

## How to Run

### Prerequisites

- Java 11 or 17  
- Maven  

Verify:

```
java -version
mvn -version
```

### Run All Simulations

```
mvn gatling:test
```

### Run Specific Simulation

```
mvn gatling:test -Dgatling.simulationClass=example.Test_A5_SpikeTest
```

---

## Reports & Results

All reports are generated in:

```
target/gatling/<run-id>/index.html
```

Each run includes:

- Response time graphs  
- Throughput charts  
- Error distribution  
- Percentiles (50th, 75th, 95th, 99th)  

---

## Key Engineering Concepts

- Asynchronous load generation  
- Event-driven architecture  
- Performance assertions  
- Traffic modeling  
- API contract validation  
- CSV-driven test design  
- Token-based authentication testing  
- Spike and stress analysis  

---

## Limitations

- No UI testing (backend only)  
- Depends on public APIs for demos  
- Not intended for destructive testing on production systems  

---

## Future Enhancements

- CI/CD integration (GitHub Actions)  
- Dockerized Gatling runner  
- Grafana + InfluxDB metrics  
- Custom DSL wrappers  
- Kubernetes load agents  

---

## Contributing

Contributions are welcome.

Steps:

1. Fork the repository  
2. Create a feature branch  
3. Add simulations or improvements  
4. Submit a pull request  

All contributions should follow clean coding standards and include documentation.

---

## License

This project is licensed under the **MIT License**.

You are free to:

- Use  
- Modify  
- Distribute  
- Include in commercial projects  

Attribution is appreciated but not required.

---

## Contact

**Mridul M Kumar**  
Performance & Backend Engineering  
[GitHub](https://github.com/mridul0703), [Portfolio](https://mridul0703.vercel.app/)

---
