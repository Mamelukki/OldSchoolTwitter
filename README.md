# Web server programming Java 2021
### Project manual

Technical implementation: Java, Spring Boot, JPA, SQL database (h2 on local, PostgreSQL on Heroku)

### Heroku
[The app is available in Heroku](https://nameless-river-37493.herokuapp.com/).

The app opens the login page by default so I recommend that you start using the app by navigating to the register page (just add /register at the end of the address or [click here](https://nameless-river-37493.herokuapp.com/register)).

### Worth mentioning
So far there are no automatic tests. There are also no notifications or error messages. Everything should still work even if you do not get a notification, for example when making a follow request. There are also some other flaws, such as the app crashing with too long messages or too big photos. The way the messages are limited to the last 25 and comments to the last 10 are clumsy and inefficient and there is probably a better way to use the database to get the relevant data. Due to time limitation, I had to make some bad choices to get all the desired features into the app in time.

### How to use the app

Start by creating a user. You can also test the app with by logging in with either of the following users:

EITHER

Username: Joulupukki

Password: secret

OR

Username: Punakuono

Password: salainen

- You can write messages on your own profile page and you can comment and likeyour own and other people's messages. The profile page also shows who you follow, who follow you, the messages of the people you follow (if there are any). You can also access either your or other users' photo album through the profile page.

- You can upload photos through the photo album.You can also add a description for the pictures. You can comment, like and delete your own pictures and others can also like and comment your pictures. 

- There is a user search on the navigation bar. The users can be found using a name (not username). The search shows a table of the matching users and they have a send follow request botton next to them. Clicking that will send a follow request to the chosen user. Unfortunately, the app shows you the send follow request button even if you already follow the person. The controller however makes sure that no one gets a request from a person who already follow them or has sent a request. You can view the requests you have made under the Follow requests tab where you can cancel the request if the other person has not yet reacted to it. 

- A user who has received a follow request can view the requests under the Follow requests tab. The user can either reject the request which leads to the deletion of the request or accept it after which the person will show up in the following of the other user and the followers of the opposite user. 

- You can log out using the logout button on the navigation bar. 
