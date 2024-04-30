## Library Project: Backend

### Overview
This backend segment of the Library Project employs a microservices architecture to manage books, orders, and inventory. It is built using various robust technologies and patterns within the Spring ecosystem and related tools.

### Technologies Used
- **Maven**: Dependency management and project lifecycle management.
- **Spring Boot**: Simplifies the development of new Spring applications.
- **Spring Cloud Netflix**: Integration with Netflix OSS components.
- **Spring Cloud Gateway**: API gateway for routing and filtering requests to microservices.
- **Spring Cloud Circuit Breaker (Resilience4j)**: Implements circuit breaker patterns.
- **Spring Cloud Sleuth**: Implements distributed tracing.
- **Zipkin**: Visualization tool for distributed tracing data.
- **MySQL**: Database for persistent storage.

### Architecture
- **Microservices Structure**: Multiple services running on different ports to simulate a microservices architecture.
- **Service Components**:
  - **Book Service**: Add and retrieve books from the store.
  - **Order Service**: Place orders for books and check inventory via REST calls to the Inventory service.
  - **Inventory Service**: Manages inventory checked by the Order service for successful order placement.
  - **Discovery Service**: Acts as a discovery server, where clients retrieve registry information containing IP addresses and ports for services.
  - **Gateway Service**: Functions as an API gateway, directing requests to appropriate microservices.

### Communication and Patterns
- **Synchronous Communication**: Between the Order and Inventory services through REST calls.
- **Circuit Breaker**: Implemented using Resilience4j in the Order service for handling timeouts and retries during calls to the Inventory service.
- **Load Balancing**: Web client used between services is load-balanced to handle multiple instances.
- **Service Discovery Pattern**: Utilizes Spring Cloud Netflix to register services with the Discovery server. Services use the registry data for connecting to other services, even if the Discovery server is down temporarily.
- **API Gateway Pattern**: Uses Spring Cloud Gateway to centralize user requests, acting as a load balancer and routing requests based on defined rules.
- **Distributed Tracing**: Implemented with Spring Cloud Sleuth and visualized using Zipkin to track requests across all involved microservices and measure service response times.

### Flow of Requests
Users can make requests on port 8080 which are handled by the Gateway Service. The Gateway routes these requests to either:
- **Product Service**
- **Order Service** → **Inventory Service** (to check if the order is in stock)

### GitHub Repository
For more details and source code, visit the [GitHub repository](https://github.com/utkarshseth/Library-Microservice-s).
