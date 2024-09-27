# Gym-application

## A few words about the application

My application is used to manage the operation of the gym. It allows clients to register for the gym's website. Users for example can check out the gym's offer, sign up for group training, claculate their Body Mass Index or calculate daily calorie requirement. The application has an administrator panel that allows you to manage gym's users and trainings. The application backend is written in Java using spring boot. Hibernate framework is using to communicate with the database. Application consist of two microservices: for customer management (h2 database), and second for training management (MongoDB database). Eureka server is used for communication between microservices in the application. Application's frontend is written in typescript using  React.

## Launching the application

To run the application you should download ZIP with project. Unpack the zip file and open the folder in Development Environment for example Intellij IDEA. You should run the applications in the following order:

 1. server
 2. clients
 3. training

To run the frontend application you should download ZIP with project which you can find here: 

 - https://github.com/panszym/gym-app-frontend.git

Unpack the zip file and open the folder in Development Environment for example Visual Studio Code. To run the application you should have Node.js installed. Then if you don't have typescript installed, you should call the method in the command console: 
 ```bash
npm install -g typescript
```
Then make sure that in Visual Studio Code you have installed react-router-dom. If not install it using command in Visual Studio Code terminal: 
 ```bash
npm install react-router-dom@6
```
The last step is to launch the application
  ```bash
npm start
```
Application started at [http://localhost:3000](http://localhost:3000/)
