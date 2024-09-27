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

## Description

### Client

#### Login and Registration 

When the application starts we will see a login form. You need log in to your account to access the website. If you don't have an account, register on the website.

 ![login](https://github.com/user-attachments/assets/e1464c63-1426-4cad-82b4-8fbdc4326534)
 
<p align="center"> Login page.</p>

![registration](https://github.com/user-attachments/assets/069c56b3-09e0-4ef8-ae4d-2f45764ae0b6)
<p align="center"> Registration page</p>


#### Home page 

When you log in to the website you will see the home page. You can use the navigation bar to move around the app.

![home](https://github.com/user-attachments/assets/8707d7d7-3708-4154-9eff-f8d1968aea15)
<p align="center"> Home page</p>
 

 - Training - you see a list of available workouts. You can filter it by category and activity status. When you press the button Details you will see detailed information about that training. If the training status is active and you have the appropriate ticket and active clients's status, you will be able to sign up for it.

![training](https://github.com/user-attachments/assets/18017018-5a01-4314-8491-55819e857e69)
 <p align="center"> Training page</p>
 
 ![training_details](https://github.com/user-attachments/assets/e09ebdae-8a0c-4b59-b55d-c36bbe09763a)
  <p align="center"> Training details page</p>
  
 - BMI calculator - you can calculate your Body Mass Index.
![BMI](https://github.com/user-attachments/assets/739665cd-2541-4099-806c-9c18ece13641)
 <p align="center"> BMI page</p>
 
 - Calorie calculator - you can calculate your daily calorie requirement based on the data provided by you.
 ![calories](https://github.com/user-attachments/assets/9f45c429-be7b-4a97-8c1c-35d078fe7d3d)
 <p align="center">Calories calculator page</p>
 
 - Pricing - you can check out the gym's offer.
 
 - Client details - you can see and edit your data.
![client_details](https://github.com/user-attachments/assets/bab51433-5a72-44b2-9ba6-1df00bddd965)
 <p align="center">Client's details page</p>
 
 - Client's training - list of training session in which the client is participant.

### Admin

#### Admin panel
The application has an administrator panel where it is possible to manage clients and trainings databases. To log in as admin you should use the following details:
**-email: `admin@poczta.com`
-password: `admin`**

 - Clients - admin sees a list of all gym's clients. Admin can filter results by customer activity status. After pressing a given client, the admin sees detailed customer data. Admin has the right to change customer data, for example the type of season ticket and to delete the client from gym's system.
  ![admin_clients](https://github.com/user-attachments/assets/27f7c271-0a73-4454-8f95-f83734d3604a)
 <p align="center">Admin clients page</p>
 
![admin_client](https://github.com/user-attachments/assets/b059f3d3-9a8f-4ec0-af98-05a228c54da5)
 
 <p align="center">Admin client's details page</p>
 
- Training - admin sees a list of all available gym's training. Admin is able to edit, delete and create training. 
![adminn_training](https://github.com/user-attachments/assets/4fcbdb6c-2bbb-4a42-9f68-3ab8fc7093ce)
<p align="center">Admin training details page</p>

 
