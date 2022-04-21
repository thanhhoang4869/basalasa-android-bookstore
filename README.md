<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/thanhhoang4869/basalasa">
    <img src="https://i.imgur.com/W3hAQxg.png" alt="Logo" width="100" height="100">
  </a>

  <h3 align="center">Mobile Application Development - BookStore</h3>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#basalasa">basalasa</a>
    </li>
    <li>
      <a href="#how-to-run-this-project">How to run this project</a>
    </li>
    <li>
      <a href="#contributors">Contributors</a>
    </li>
  </ol>
</details>

<br/>

# basalasa

- This project is named Basalasa and is owned by Dau Sua team while studying the course Mobile Application Development in HCMUS. 
- In this project, we build an e-commerce Android mobile application for books, and use NodeJS as the back-end server.

## Android application
The android application is built in Kotlin language following the MVC model.

## Server side
The server resides on Heroku, which works as a NodeJS server, connects to MongoDB.

# How to run this project

**1. Install Nodejs and Android Studio**

This project requires install [Node.js](https://nodejs.org/), npm and [Android Studio](https://developer.android.com/) first. If you already have Nodejs and npm installed, skip this step.


**2. Start the server**

Firstly, start the server
- Open project, then change dir to ```Server```
- Use these commands to start the server:
```sh
npm i
```
```sh
npm install
```
```sh
node server.js
```

**3. Run the application**
- Open the project with Android Studio
- Inside the folder ```Application```, find folder ```utils``` then go to the file ```MyAPI.kt```
- Change the ```BASE_URL``` to your localhost IP address
```sh
  private const val BASE_URL = //config here
```

**4. Test login**

Test login by using the account below
```sh
  username: thanhhoang4869@gmail.com
  password: 1234
```
This is the admin account, if you need to login as seller/customer, kindly find ```user.json``` in folder ```Server``` > ```database``` > ```collections``` to see more accounts.

# Contributors

Dau Sua
- [Hoang Nhu Thanh](https://github.com/thanhhoang4869)
- [Vu Duc Quang Huy](https://github.com/19US-dominic)
- [Le Thanh Khoi](https://github.com/lethanhkhoi)
- [Hoang Thien Nhan](https://github.com/jasonhoang0621)

You are free to refer from our project, but ***do not copy without permission***.


