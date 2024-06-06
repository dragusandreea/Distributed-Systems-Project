# DS2023_30643_Dragus_Andreea_Assignment_3

## Description

This project was developed at the Distributed System Laboratory at UTCN. It is composed of three microservices on the back-end side and a sensor simulator app, all of them being Java&Spring-Boot applications. The application simulates continous readings from various sensors attached to devices. On the front-end side, the application was developed using Angular. In the following sections I will provide a step-by-step tutorial for running this project.

## Prerequisites
Before you begin, ensure you have the following software installed on your system:

- Java (JDK17)
- PostgreSQL
- Node.js
- Angular CLI
- Docker Desktop
- RabbitMQ Server


## Getting started
- Make sure you have a running RabbitMQ server; you can achieve that by using the following command:
```
docker run -it --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12.8-management
```

- Clone this repository on your local machine
```
 git clone  https://gitlab.com/ds2023_30643_dragus_andreea/ds2023_30643_dragus_andreea_assignment_3
```
- Go to the project directory
```
cd ds2023_30643_dragus_andreea_assignment_3
```
- Go to the backend directory
```
cd backend
```
- Let's build the Docker container for the UserManagementService first
```
cd UserManagementService
docker compose up -d
```
- Then build the Docker container for the DeviceManagementService
```
cd DeviceManagementService
docker compose up -d
```
- Build the Docker container for the MonitoringService
```
cd MonitoringService
docker compose up -d
```
- Build the Docker container for the ChatService
```
cd ChatService
docker compose up -d
```

- Go to the frontend directory
```
cd frontend
cd EnergyManagementSystemFrontend
```
- Build the Docker container for the frontend
```
docker compose up -d
```
You can do this step at any moment:  run the Sensor Simulator application
## Accessing the application
Once the containers are up and running, you can access the application in your browser:
-  Angular Frontend: http://localhost:4200/welcome/home-page
-  Spring Boot Users API:  http://localhost:8082
-  Spring Boot Devices API: http://localhost:8081
-  Spring Boot Monitoring API: http://localhost:8084
-  Spring Boot Chat API: http://localhost:8085
-  Spring Boot Sensor Simulator API: http://localhost:8083
-  RabbitMQ API: http://localhost:15672