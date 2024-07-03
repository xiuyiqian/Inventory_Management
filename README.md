# Inventory Management System

This project is an Inventory Management System designed to handle products and orders within an e-commerce platform. The design is based on the architecture depicted in the diagram below.

<img src="./Inventory_Mangement.png" alt="Solution Architecture" style="width:100%;">

## Overview

### Solution Architecture

The solution consists of several components interacting with each other to manage the inventory, orders, and delivery processes. The main components are:

- **Merchant**: Manages product provision from suppliers and handles inventory.
- **Supplier**: Provides products to merchants.
- **Delivery Person**: Handles the delivery of products to customers.
- **Customer**: Adds items to the cart and places orders.
- **Home Page**: The interface where customers interact with the system.

### Spring Framework

The backend is developed using the Spring framework, which handles HTTP requests through controllers, processes business logic in services, interacts with repositories for data persistence, and communicates with a database.

## API Gateway Implementation

The API Gateway in this project is implemented using Spring Cloud Gateway and Eureka for service discovery. It handles routing and load balancing between different services.

## Eureka Server

This repository contains the configuration for an Eureka Server, which is used as a service registry for microservices. Eureka Server enables microservices to discover each other and manage their instances dynamically.

### **Multi-instance Support**

- **Load Balancing**
  - Distributes incoming requests across all available instances to optimize performance and resource utilization.
- **Fault Tolerance**
  - If one instance fails, other instances can take over, ensuring the service remains available.
- **Health Monitoring**
  - Regularly checks the health of each instance and removes any that are not responding, ensuring that only healthy instances are used.

## Example Configuration for a Microservice Registering with Eureka


### Structure and Architecture

- **API Gateway (Spring Cloud Gateway)**
  - **Service Discovery (Eureka)**
    - Registers the API Gateway and backend services
    - Enables dynamic discovery and routing to microservices
  - **Routes**
    - **Product Service Route**
      - ID: `Product-Management`
      - URI: Load-balanced URI for Product-Management service
      - Predicate: Path matching `/api/v2/product`
    - **Order Service Route**
      - ID: `Order-Management`
      - URI: Load-balanced URI for Order-Management service
      - Predicate: Path matching `/api/v2/order`
    - **IP Dictionary Service Route**
      - ID: `server-management`
      - URI: Direct URI to local Eureka server
      - Predicate: Path matching `/eureka/web`
      - Filter: SetPath to `/`
  - **Logging**
    - INFO level for general logs
    - TRACE level for detailed gateway operations logs

This architecture ensures that the API Gateway efficiently manages traffic to various microservices, enhancing the scalability and reliability of the overall system.


## Data Storage

### Product Data

- **Database**: MongoDB
- **Fields**:
  - `merchant name`
  - `price`
  - `description`

### Order Data

- **Database**: JPA (Java Persistence API)
- **Fields**:
  - `orderList` (contains one item)
- **Item Fields**:
  - `quantity`
  - `price`

### Storage Data

- **Database**: JPA (Java Persistence API)
- **Fields**:
  - `skuCode` (the name of the product)
  - `quantity`

# Interservice Communication

To ensure seamless integration between the Order and Storage microservices, the system performs a synchronous HTTP call to the Storage service when placing an order. This call checks if there is enough inventory to support the order and validates the order.

## Order Placement Flow

1. **Customer places an order**: The customer adds items to the cart and submits the order.
2. **Order service validation**: The Order service validates the order details by generating an order number and mapping the order request items to real order items.
3. **Inventory check**:
   - The Order service extracts the SKU codes from the order items.
   - It then makes a synchronous HTTP request to the Storage service to check the inventory levels for the requested SKU codes.
4. **Order processing**:
   - If the inventory is sufficient (i.e., all items are in stock), the order is processed and saved to the database.
   - If the inventory is insufficient (i.e., one or more items are out of stock), the order is rejected with an appropriate message.



## Using MockMvc for Testing in Inventory Management System

In this project, we use `MockMvc` to perform integration tests on our Spring Boot application's web layer. This allows us to test the behavior of our controllers and ensure they are handling HTTP requests correctly. Below is an example of how we use `MockMvc` to test the product creation functionality.

### Setting Up the Test

We use the following dependencies and annotations for setting up our test environment:

- **Testcontainers**: To run a MongoDB container for testing purposes.
- **Spring Boot Test**: To load the application context and enable web layer testing.
- **MockMvc**: To perform HTTP requests and verify the responses.
- **ObjectMapper**: To serialize and deserialize JSON objects.

### MongoDB Container for testing
To ensure our tests run in an isolated and consistent environment, we use a MongoDB container provided by Testcontainers. This approach allows us to spin up a fresh MongoDB instance for each test run, ensuring that tests do not interfere with each other and have a predictable state.

### Test Example

Here is an example test case that demonstrates how to use `MockMvc` to test the creation of a product:

```java
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {

		ProductRequest productRequest = getProductRequest();
		String jsonPostRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonPostRequestString))
				.andExpect(status().isCreated());

		Assertions.assertEquals(1, productRepository.findAll().size());
	}
